package com.database.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;


public class InfluxUtils {
    private static final Logger LOGGER = LogManager.getLogger("InfluxUtils");

    public static InfluxDB influxDB;
    public static final String DB_NAME = "measurements";

    public static boolean connect() {

        influxDB = InfluxDBFactory.connect("http://77.55.225.4:8086", "root", "Szop1234");
        boolean influxDbStarted = false;
        do {
            Pong response;
            try {
                response = influxDB.ping();
                if (!response.getVersion().equalsIgnoreCase("unknown")) {
                    influxDbStarted = true;
                }
            } catch (Exception e) {
                // NOOP intentional
                LOGGER.error(e);
            }
        } while (!influxDbStarted);
        influxDB.setDatabase(DB_NAME);

        return true;
    }
}
