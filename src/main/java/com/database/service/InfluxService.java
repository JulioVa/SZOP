package com.database.service;

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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.database.util.InfluxUtils.DB_NAME;
import static com.database.util.InfluxUtils.influxDB;

public class InfluxService {

    private static final Logger LOGGER = LogManager.getLogger("InfluxService");
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static void writeData(int userId, int systemId, List<TemperatureData> temps){

        BatchPoints batchPoints = BatchPoints
                .database(DB_NAME)
                .tag("async", "true")
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();

        for (TemperatureData temp: temps) {

            Point point = Point.measurement("user_" + userId)
                    .tag("type", temp.getType())
                    .tag("sensor", temp.getSensorId())
                    .tag("system", Integer.valueOf(systemId).toString())
                    .time(Long.parseLong(temp.getDate()), TimeUnit.MILLISECONDS)
                    .addField("value", temp.getValue())
                    .build();

            batchPoints.point(point);
        }

        influxDB.write(batchPoints);
    }

    public static Map<Long, Double> getDataForSensor(int userId, int systemId, String sensorId){
        Map<Long, Double> results = new HashMap<>();
        Query query = new Query("SELECT * FROM user_" + userId + " WHERE system='" + systemId + "' AND sensor='" + sensorId +"' GROUP BY *", DB_NAME);
        QueryResult queryResult = influxDB.query(query);
            if(queryResult.getError() == null && queryResult.getResults().get(0).getSeries() != null)
            for (List<Object> qResult :queryResult.getResults().get(0).getSeries().get(0).getValues()){
                try {
                    results.put(formatter.parse((String) qResult.get(0)).getTime(), (Double) qResult.get(1));
                } catch (ParseException e) {
                    LOGGER.error("Error:",e);
                }
          }
        return results;
    }
}
