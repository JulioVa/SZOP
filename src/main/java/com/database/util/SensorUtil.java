package com.database.util;

import com.database.dto.SensorDto;
import com.database.dto.SensorIdLevelDto;
import com.database.model.Schema;
import com.database.model.Sensor;
import com.database.model.System;
import com.database.service.SchemaService;
import com.database.service.SensorService;
import com.database.service.SystemService;

import java.util.ArrayList;
import java.util.List;

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
        if (sensorUpdate.getSchemaId() != null) {
            sensor.setSchema(SchemaService.findSchemaById(sensorUpdate.getSchemaId()));
        }
        return sensor;
    }

    public static SensorDto convertToDto(Sensor sensor) {
        SensorDto sensorDto = new SensorDto();
        sensorDto.setSensorId(sensor.getSensorId());
        sensorDto.setName(sensor.getName());
        sensorDto.setType(sensor.getType());
        sensorDto.setLastUpdate(sensor.getLastUpdate());
        sensorDto.setActive(sensor.isActive());
        sensorDto.setSchemaX(sensor.getSchemaX());
        sensorDto.setSchemaY(sensor.getSchemaY());
        if (sensor.getSchema() != null) {
            sensorDto.setSchemaId(sensor.getSchema().getId());
        }
        sensorDto.setSystemId(sensor.getSystem().getId());
        return sensorDto;
    }

    public static SensorIdLevelDto convertToDtoId(Sensor sensor) {
        SensorIdLevelDto sensorDto = new SensorIdLevelDto();
        sensorDto.setSensorId(sensor.getSensorId());
        sensorDto.setName(sensor.getName());
        sensorDto.setType(sensor.getType());
        sensorDto.setLastUpdate(sensor.getLastUpdate());
        sensorDto.setActive(sensor.isActive());
        sensorDto.setSchemaX(sensor.getSchemaX());
        sensorDto.setSchemaY(sensor.getSchemaY());
        if (sensor.getSchema() != null) {
            sensorDto.setSchemaId(sensor.getSchema().getId());
        }
        sensorDto.setSystemId(sensor.getSystem().getId());
        sensorDto.setId(sensor.getId());
        return sensorDto;
    }

    public static List<SensorDto> convertToDtos(List<Sensor> sensors) {
        List<SensorDto> sensorDtos = new ArrayList<>();
        for (Sensor sensor : sensors) {
            sensorDtos.add(convertToDto(sensor));
        }
        return sensorDtos;
    }

    public static List<SensorIdLevelDto> convertToDtosId(List<Sensor> sensors) {
        List<SensorIdLevelDto> sensorDtos = new ArrayList<>();
        for (Sensor sensor : sensors) {
            sensorDtos.add(convertToDtoId(sensor));
        }
        return sensorDtos;
    }

    public static void unbindFromSchema(List<Sensor> sensors) {
        for (Sensor sensor : sensors) {
            sensor.setSchemaX(null);
            sensor.setSchemaY(null);
            sensor.setSchema(null);
            SensorService.update(sensor);
        }
    }

    public static void unbindSingleFromSchema(Sensor sensor) {
        sensor.setSchemaX(null);
        sensor.setSchemaY(null);
        sensor.setSchema(null);
        SensorService.update(sensor);
    }

}
