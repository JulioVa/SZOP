package com.database;

import com.database.model.*;
import com.database.model.System;
import com.database.service.AlertService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class AlertTest extends TestBase {

    private User user;
    private System system;
    private Schema schema;
    private Sensor sensor;
    private Alert alert;

    private int id;
    @Before
    public void setValuesToTests(){
        user = new User();
        user.setId(1);
        user.setName("name1");
        user.setPassword("dfsgf2376");
        user.setMail("mail@name1.com");

        system = new System();
        system.setId(1);
        system.setUserId(user);
        system.setName("system1");

        schema = new Schema();
        schema.setId(1);
        schema.setName("schema1");
        schema.setImg("dgfdsghfdthf".getBytes());

        sensor = new Sensor();
        sensor.setId(1);
        sensor.setSensorId(1);
        sensor.setName("sensor1");
        sensor.setType(1);

        /*String str = "2017-08-01 10:23:54";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());*/
        Date date = new Date(1501575834000L);

        sensor.setLastUpdate(date);
        sensor.setActive(true);
        sensor.setSystem(system);
        sensor.setSchema(schema);
        sensor.setSchemaX(50);
        sensor.setSchemaY(50);

        alert = new Alert();
        alert.setId(1);
        alert.setUser(user);
        alert.setSensor(sensor);
        alert.setGreaterLower("greater");
        alert.setValue(30.5d);
    }

    @Test
    public void findAlertByIdTest() {
        Alert alertTest = AlertService.findAlertById(1);
        Assert.assertNotNull(alertTest);
        Assert.assertEquals(1,alertTest.getId());
        Assert.assertEquals(user, alertTest.getUser());
        Assert.assertEquals(sensor, alertTest.getSensor());
        Assert.assertEquals("greater",alertTest.getGreaterLower());
        Assert.assertEquals(new Double (30.5), alertTest.getValue());
    }

    @Test
    public void findAllTest(){
        List<Alert> alertsTest = AlertService.findAll();
        Assert.assertNotNull(alertsTest);
        Assert.assertFalse(alertsTest.isEmpty());
        Assert.assertTrue(alertsTest.contains(alert));
    }

    @Test
    public void findAllForUser(){
        List<Alert> alertsTest = AlertService.findAllForUser(user.getId());
        Assert.assertNotNull(alertsTest);
        Assert.assertFalse(alertsTest.isEmpty());
        Assert.assertEquals(1, alertsTest.size());
        Assert.assertTrue(alertsTest.contains(alert));
    }

    @Test
    public void createAlertTest(){
        Alert alertTest = new Alert();
        alertTest.setUser(user);
        alertTest.setSensor(sensor);
        alertTest.setGreaterLower("lower");
        alertTest.setValue(15.0d);

        id = AlertService.save(alertTest);
        Alert savedAlert = AlertService.findAlertById(id);

        Assert.assertNotNull(savedAlert);
        Assert.assertEquals(alertTest, savedAlert);

        AlertService.delete(savedAlert);

    }

    @Test
    public void updateAlertTest(){
        Alert alertTest = new Alert();
        alertTest.setUser(user);
        alertTest.setSensor(sensor);
        alertTest.setGreaterLower("lower");
        alertTest.setValue(15.0d);

        id = AlertService.save(alertTest);
        Alert oldAlert = AlertService.findAlertById(id);
        Assert.assertEquals(oldAlert, alertTest);

        alertTest.setValue(24.5d);

        AlertService.update(alertTest);

        Alert newAlert = AlertService.findAlertById(id);

        Assert.assertNotEquals(oldAlert, newAlert);
        Assert.assertEquals(alertTest, newAlert);

        AlertService.delete(alertTest);
    }

    @Test(expected = NoResultException.class)
    public void deleteAlertTest(){
        Alert alertTest = new Alert();
        alertTest.setUser(user);
        alertTest.setSensor(sensor);
        alertTest.setGreaterLower("lower");
        alertTest.setValue(15.0d);

        id = AlertService.save(alertTest);

        Alert newAlert = AlertService.findAlertById(id);
        Assert.assertNotNull(newAlert);

        AlertService.delete(newAlert);

        Alert alertTest2 = AlertService.findAlertById(id);
        Assert.assertNull(alertTest2);
    }

}
