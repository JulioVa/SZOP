package com.database;

import com.database.model.Sensor;
import com.database.service.SchemaService;
import com.database.service.SensorService;
import com.database.service.SystemService;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SensorTest extends TestBase {

    @Test
    public void findSensorByIdTest() {
        Sensor sensor = SensorService.findSensorById(1);
        Assert.assertNotNull(sensor);
        Assert.assertEquals(new Integer(1), sensor.getId());
        Assert.assertEquals("sensor1", sensor.getName());
        Assert.assertEquals(new Integer(SensorTypeEnum.TEMPERATURE.getId()), sensor.getType());
        Assert.assertTrue(sensor.isActive());
    }

    @Test
    public void createSensorTest() {
        Sensor sensor = new Sensor();
        sensor.setSensorId("3");
        sensor.setName("new sensor");
        sensor.setType(SensorTypeEnum.HUMIDITY.getId());
        sensor.setActive(true);
        sensor.setSystem(SystemService.findSystemById(2));
        sensor.setSchema(SchemaService.findSchemaById(2));
        SensorService.save(sensor);

        Sensor cachedSensor = SensorService.findBySensorIdAndSystemId("3", 2);
        Assert.assertEquals(sensor, cachedSensor);
    }

    @Test
    public void updateSensorTest() {
        Sensor sensor = new Sensor();
        sensor.setSensorId("4");
        sensor.setName("new sensor");
        sensor.setType(SensorTypeEnum.HUMIDITY.getId());
        sensor.setActive(true);
        sensor.setSystem(SystemService.findSystemById(2));
        sensor.setSchema(SchemaService.findSchemaById(2));
        SensorService.save(sensor);

        sensor.setName("updated sensor");
        sensor.setType(2);
        SensorService.update(sensor);
        Sensor updatedSensor = SensorService.findBySensorIdAndSystemId("4", 2);
        Assert.assertEquals(sensor, updatedSensor);
    }

    @Test
    public void deleteSensorTest() {
        Sensor sensor = new Sensor();
        sensor.setSensorId("5");
        sensor.setName("new sensor");
        sensor.setType(SensorTypeEnum.HUMIDITY.getId());
        sensor.setActive(true);
        sensor.setSystem(SystemService.findSystemById(2));
        sensor.setSchema(SchemaService.findSchemaById(2));
        SensorService.save(sensor);
        SensorService.delete(sensor);

        Sensor deletedSensor = SensorService.findBySensorIdAndSystemId("5", 2);
        Assert.assertNull(deletedSensor);
    }

    @Test
    public void findSensorsBySchemaTest() {
        List<Sensor> sensors = SensorService.findAllBySchema(1);
        Assert.assertEquals(3, sensors.size());
    }

    @Test
    public void findSensorsBySystemTest() {
        List<Sensor> sensors = SensorService.findAllBySystem(1);
        Assert.assertEquals(3, sensors.size());
    }

    @Test
    public void findAllSensorsTest() {
        List<Sensor> sensors = SensorService.findAll();
        Assert.assertFalse(sensors.isEmpty());
    }
}
