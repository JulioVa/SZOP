package com.database.util;

import com.database.dto.SensorDto;
import com.database.model.Schema;
import com.database.model.Sensor;
import com.database.model.System;
import com.database.service.SchemaService;
import com.database.service.SystemService;

public class SensorUtil {

    public static Sensor addSensor(SensorDto sensorDto) {
        Schema schema = SchemaService.findSchemaById(sensorDto.getSchemaId());
        System system = SystemService.findSystemById(sensorDto.getSystemId());
        if (schema != null && system != null) {
            Sensor sensor = new Sensor();
            sensor.setName(sensorDto.getName());
            sensor.setActive(sensorDto.isActive());
            sensor.setType(sensorDto.getType());
            sensor.setSchemaX(sensorDto.getSchemaX());
            sensor.setSchemaY(sensorDto.getSchemaY());
            sensor.setSchema(schema);
            sensor.setSystem(system);
            return sensor;
        } else {
            return null;
        }
    }

    public static Sensor updateSensor(Sensor sensor, SensorDto sensorUpdate) {
        sensor.setSensorId(NotNullUtil.setNotNull(sensor.getSensorId(), sensorUpdate.getSensorId()));
        sensor.setName(NotNullUtil.setNotNull(sensor.getName(), sensorUpdate.getName()));
        sensor.setType(NotNullUtil.setNotNull(sensor.getType(), sensorUpdate.getType()));
        sensor.setLastUpdate(NotNullUtil.setNotNull(sensor.getLastUpdate(), sensorUpdate.getLastUpdate()));
        sensor.setActive(NotNullUtil.setNotNull(sensor.isActive(), sensorUpdate.isActive()));
        sensor.setSchemaX(NotNullUtil.setNotNull(sensor.getSchemaX(), sensorUpdate.getSchemaX()));
        sensor.setSchemaY(NotNullUtil.setNotNull(sensor.getSchemaY(), sensorUpdate.getSchemaY()));
        return sensor;
    }
}
