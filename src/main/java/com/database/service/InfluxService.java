package com.database.service;

import com.database.model.*;
import com.database.model.System;
import com.database.util.ColorUtil;
import org.apache.log4j.LogManager;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.database.util.InfluxUtils.DB_NAME;
import static com.database.util.InfluxUtils.influxDB;

public class InfluxService {

    private static final org.apache.log4j.Logger LOGGER = LogManager.getLogger(InfluxService.class);

    private static final String[] tableNames = new String[]{"measurements", "measurements_1h", "measurements_1d"};

    public static void writeData(String userId, String sysName, List<TemperatureData> temps) {

        User user = UserService.findUserByEmail(userId);
        if (user == null)
            return;

        System sys = SystemService.findSystemByName(sysName);
        if (sys == null) {
            sys = new System(user, sysName);
            int id = SystemService.save(sys);
            sys.setId(id);
        }
        Set<String> sensors = new HashSet<>();
        BatchPoints batchPoints = BatchPoints
                .database(DB_NAME)
                .retentionPolicy("two_days")
                .tag("async", "true")
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();

        for (TemperatureData temp : temps) {

            Point point = Point.measurement(tableNames[0])
                    .tag("type", temp.getType())
                    .tag("sensor", temp.getSensorId())
                    .tag("system", sys.getName())
                    .tag("user", user.getEmail())
                    .time(Long.parseLong(temp.getDate()), TimeUnit.MILLISECONDS)
                    .addField("value", temp.getValue())
                    .build();

            int type = temp.getType().equals("temp") ? 1 : 2;
            if (sensors.add(temp.getSensorId()) && (SensorService.findBySensorIdAndSystemIdAndType(temp.getSensorId(), sys.getId(), type) == null)) {
                SensorService.save(new Sensor(temp.getSensorId(), "new sensor", type, new Date(Long.parseLong(temp.getDate())), true, null, sys, null, null, ColorUtil.generateColor()));
            } else {
                Sensor sensor = SensorService.findBySensorIdSystemNameAndMail(temp.getSensorId(), sysName, userId);
                sensor.setLastUpdate(new Date(Long.parseLong(temp.getDate())));
                SensorService.update(sensor);
            }
            batchPoints.point(point);
        }

        influxDB.write(batchPoints);
    }

    public static List<Temperature> getDataForSensor(String mail, String sensorId) {
        List<Temperature> results = new ArrayList<>();

        Query  query = new Query("SELECT * FROM " + DB_NAME + ".\"autogen\".\"" + tableNames[2] + "\" WHERE time < now() - 7d AND sensor='" + sensorId + "' AND \"user\"='" + mail + "' GROUP BY * ORDER BY time", DB_NAME);
        addResult(influxDB.query(query, TimeUnit.MILLISECONDS), results);

        query = new Query("SELECT * FROM " + DB_NAME + ".two_weeks.\"" + tableNames[1] + "\" WHERE time < now() - 1d AND time > now() - 7d AND sensor='" + sensorId + "' AND \"user\"='" + mail + "' GROUP BY * ORDER BY time", DB_NAME);
        addResult(influxDB.query(query, TimeUnit.MILLISECONDS), results);

        query = new Query("SELECT * FROM " + DB_NAME + ".two_days.\"" + tableNames[0] +"\" GROUP BY *" ,DB_NAME);//+ "\" WHERE time > now() - 1d AND sensor='" + sensorId + "' AND \"user\"='" + mail + "' GROUP BY * ORDER BY time", DB_NAME);
        addResult(influxDB.query(query, TimeUnit.MILLISECONDS), results);

        return results;
    }

    private static void addResult(QueryResult queryResult, List<Temperature> results) {
        if (queryResult.getError() == null && queryResult.getResults().get(0).getSeries() != null)
            for (List<Object> qResult : queryResult.getResults().get(0).getSeries().get(0).getValues()) {
                results.add(new Temperature((Double) qResult.get(1), ((Double) qResult.get(0)).longValue()));
            }
    }

    public static Double getDataForSensorLastValue(String mail, String sensorId) {
        Double result = null;
        Query query = new Query("SELECT * FROM " + DB_NAME + ".two_days.\"" + tableNames[0] + "\" WHERE sensor='" + sensorId + "' AND \"user\"='" + mail + "' GROUP BY * ORDER BY time DESC LIMIT 1", DB_NAME);
        QueryResult queryResult = influxDB.query(query, TimeUnit.MILLISECONDS);
        if (queryResult.getError() == null && queryResult.getResults().get(0).getSeries() != null) {
            result = (Double) queryResult.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);
        }
        return result;
    }

    public static List<SensorTempDataColorLevel> getDataForUserWithColor(String mail, String type) {
        List<SensorTempDataColorLevel> results = new ArrayList<>();

        Query query = new Query("SELECT * FROM " + DB_NAME + ".\"autogen\".\"" + tableNames[2] + "\" WHERE time < now() - 7d AND type = '" + type + "' AND \"user\"='" + mail + "' GROUP BY * ORDER BY time", DB_NAME);
        addTempAndColorResult(influxDB.query(query, TimeUnit.MILLISECONDS), results, mail);

        query = new Query("SELECT * FROM " + DB_NAME + ".two_weeks.\"" + tableNames[1] + "\" WHERE time < now() - 1d AND time > now() - 7d AND type = '" + type + "' AND \"user\"='" + mail + "' GROUP BY * ORDER BY time", DB_NAME);
        addTempAndColorResult(influxDB.query(query, TimeUnit.MILLISECONDS), results, mail);

        query = new Query("SELECT * FROM " + DB_NAME + ".two_days.\"" + tableNames[0] + "\" WHERE time > now() - 1d AND type = '" + type + "' AND \"user\"='" + mail + "' GROUP BY * ORDER BY time", DB_NAME);
        addTempAndColorResult(influxDB.query(query, TimeUnit.MILLISECONDS), results, mail);

        return results;
    }

    private static void addTempAndColorResult(QueryResult queryResult, List<SensorTempDataColorLevel> results, String mail) {
        if (queryResult.getError() == null && queryResult.getResults().get(0).getSeries() != null) {

            for (QueryResult.Series sensor : queryResult.getResults().get(0).getSeries()) {
                List<Temperature> temperatures = new ArrayList<>();
                for (List<Object> tmps : sensor.getValues()) {
                    temperatures.add(new Temperature((Double) tmps.get(1), ((Double) tmps.get(0)).longValue()));
                }

                boolean contains = false;
                for(SensorTempData sensorTempData: results){
                    if(sensorTempData.getSensorID().equals(sensor.getTags().get("sensor"))){
                        sensorTempData.getTemps().addAll(temperatures);
                        contains = true;
                    }
                }
                if(!contains) {
                    LOGGER.info(sensor.getTags().get("system"));
                    Sensor sens = SensorService.findBySensorIdSystemNameAndMail(sensor.getTags().get("sensor"), sensor.getTags().get("system"), mail);
                    results.add(new SensorTempDataColorLevel(sensor.getTags().get("sensor"), temperatures, sens.getColor(), sens.getName()));
                }
            }
        }
    }
}
