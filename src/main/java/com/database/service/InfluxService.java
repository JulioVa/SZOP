package com.database.service;

import com.database.model.Sensor;
import com.database.model.SensorTempData;
import com.database.model.System;
import com.database.model.Temperature;
import com.database.model.TemperatureData;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.database.util.InfluxUtils.DB_NAME;
import static com.database.util.InfluxUtils.influxDB;

public class InfluxService {

    private static final Logger LOGGER = LogManager.getLogger("InfluxService");
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static void writeData(int userId, int systemId, List<TemperatureData> temps) {

        System sys = SystemService.findSystemById(systemId);
        Set<String> sensors = new HashSet<>();
        BatchPoints batchPoints = BatchPoints
                .database(DB_NAME)
                .tag("async", "true")
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();

        for (TemperatureData temp : temps) {

            Point point = Point.measurement("user_" + userId)
                    .tag("type", temp.getType())
                    .tag("sensor", temp.getSensorId())
                    .tag("system", Integer.valueOf(systemId).toString())
                    .time(Long.parseLong(temp.getDate()), TimeUnit.MILLISECONDS)
                    .addField("value", temp.getValue())
                    .build();

            if (sensors.add(temp.getSensorId()) && (SensorService.findBySensorIdAndSystemId(temp.getSensorId(), systemId) == null)) {
                int type = temp.getType().equals("temp") ? 1 : 2;
                SensorService.save(new Sensor(temp.getSensorId(), "new sensor", type, new Date(Long.parseLong(temp.getDate())), true, null, sys, null, null));
            }
            batchPoints.point(point);
        }

        influxDB.write(batchPoints);
    }

    public static List<Temperature> getDataForSensor(int userId, int systemId, String sensorId) {
        List<Temperature> results = new ArrayList<>();

        Query query = new Query("SELECT * FROM user_" + userId + " WHERE system='" + systemId + "' AND sensor='" + sensorId + "' GROUP BY * ORDER BY time", DB_NAME);
        QueryResult queryResult = influxDB.query(query);
        if (queryResult.getError() == null && queryResult.getResults().get(0).getSeries() != null)
            for (List<Object> qResult : queryResult.getResults().get(0).getSeries().get(0).getValues()) {
                try {
                    results.add(new Temperature((Double) qResult.get(1), formatter.parse((String) qResult.get(0)).getTime()));
                } catch (ParseException e) {
                    LOGGER.error("Error:", e);
                }
            }

        return results;
    }

    public static List<SensorTempData> getDataForUser(int userId, String type){
        List<SensorTempData> results = new ArrayList<>();
        Query query = new Query("SELECT * FROM user_" + userId + " WHERE type = '" + type + "' GROUP BY * ORDER BY time", DB_NAME);
        QueryResult queryResult = influxDB.query(query);
        if (queryResult.getError() == null && queryResult.getResults().get(0).getSeries() != null)
            for (QueryResult.Series sensor : queryResult.getResults().get(0).getSeries()) {
                List<Temperature> temperatures = new ArrayList<>();
                for (List<Object> tmps : sensor.getValues()) {
                    try {
                        temperatures.add(new Temperature((Double) tmps.get(1), formatter.parse((String) tmps.get(0)).getTime()));
                    } catch (ParseException e) {
                        LOGGER.error("Error:", e);
                    }
                }
                results.add(new SensorTempData(sensor.getTags().get("sensor"), temperatures));
            }
        LOGGER.error(queryResult);
        return results;
    }
}
