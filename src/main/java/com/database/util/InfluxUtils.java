package com.database.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class InfluxUtils {
    private static final Logger LOGGER = LogManager.getLogger("InfluxUtils");

    public static InfluxDB influxDB;
    public static String DB_NAME = "";
    public boolean connect() {

        try {
            final FileInputStream file = new FileInputStream("./influx.properties");
            final Properties properties = new Properties();
            properties.load(file);
            DB_NAME = properties.getProperty("influx.db.name");
            String userName = properties.getProperty("influx.username");
            String password = properties.getProperty("influx.password");
            String address = properties.getProperty("influx.address");

            influxDB = InfluxDBFactory.connect(address, userName, password);
        boolean influxDBstarted = false;
        do {
            Pong response;
            try {
                response = influxDB.ping();
                if (!response.getVersion().equalsIgnoreCase("unknown")) {
                    influxDBstarted = true;
                }
            } catch (Exception e) {
                LOGGER.error(e  );
            }
        } while (!influxDBstarted);
        influxDB.createDatabase(DB_NAME);
        influxDB.setDatabase(DB_NAME);

        } catch (IOException e) {
            LOGGER.error("Error:",e);
            return false;
        }

        return true;
    }
}
