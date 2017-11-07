package com.database.service;

import com.database.model.TemperatureData;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.database.util.InfluxUtils.DB_NAME;
import static com.database.util.InfluxUtils.influxDB;

public class InfluxService {

    public static void writeData(int userId, int systemId, List<TemperatureData> temps){

        BatchPoints batchPoints = BatchPoints
                .database(DB_NAME)
                .tag("async", "true")
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();

        for (TemperatureData temp: temps) {

            Point point = Point.measurement("user_" + userId)
                    .time(Long.parseLong(temp.getDate()), TimeUnit.MILLISECONDS)
                    .addField("value", temp.getValue())
                    .tag("type", temp.getType())
                    .tag("sensor", temp.getSensorId())
                    .tag("system", Integer.valueOf(systemId).toString())
                    .build();

            batchPoints.point(point);
        }

        influxDB.write(batchPoints);
    }
}
