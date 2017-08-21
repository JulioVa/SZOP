package com.database;

import com.database.model.Sensor;
import com.database.service.SchemaService;
import com.database.service.SensorService;
import com.database.service.SystemService;
import org.junit.Assert;
import org.junit.Test;

public class SensorTest extends TestBase {

    @Test
    public void findSensorByIdTest() {
        Sensor sensor = SensorService.findSensorById(1);
        Assert.assertNotNull(sensor);
        Assert.assertEquals(1, sensor.getId());
        Assert.assertEquals("sensor1", sensor.getName());
        Assert.assertEquals(1, sensor.getType());
        Assert.assertTrue(sensor.isActive());
    }

    @Test
    public void createSensorTest() {
        Sensor sensor = new Sensor();
        sensor.setSensorId(3);
        sensor.setName("new sensor");
        sensor.setType(2);
        sensor.setActive(true);
        sensor.setSystem(SystemService.findSystemById(1));
        sensor.setSchema(SchemaService.findSchemaById(1));
        SensorService.save(sensor);

        Sensor cachedSensor = SensorService.findBySensorIdAndSystemId(3, 1);
        Assert.assertEquals(sensor, cachedSensor);
    }


}
