package com.database;

import com.database.model.System;
import com.database.model.User;
import com.database.service.SystemService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.NoResultException;
import java.util.List;

public class SystemTest extends TestBase {

    private User user;

    @Before
    public void createUser(){
        user = new User();
        user.setId(1);
        user.setName("name1");
        user.setPassword("dfsgf2376");
        user.setMail("mail@name1.com");
    }

    @Test
    public void findSystemByIdTest(){
        System systemTest = SystemService.findSystemById(1);
        Assert.assertNotNull(systemTest);
        Assert.assertEquals(1, systemTest.getId());
        Assert.assertEquals(user, systemTest.getUserId());
        Assert.assertEquals("system1", systemTest.getName());
    }

    @Test
    public void findAllTest(){
        System systemTest = SystemService.findSystemById(1);

        List<System> systemList = SystemService.findAll();
        Assert.assertNotNull(systemList);
        Assert.assertFalse(systemList.isEmpty());
        Assert.assertTrue(systemList.contains(systemTest));
    }

    @Test
    public void findAllByUserTest(){
        System system1 = SystemService.findSystemById(1);

        List<System> systemList = SystemService.findAllByUser(user.getId());
        Assert.assertNotNull(systemList);
        Assert.assertFalse(systemList.isEmpty());
        Assert.assertTrue(systemList.contains(system1));
    }

    @Test
    public void createSystemTest(){
        System systemTest = new System();
        systemTest.setName("createTestName");
        systemTest.setUserId(user);

        int id = SystemService.save(systemTest);
        System newSystem = SystemService.findSystemById(id);

        Assert.assertNotNull(newSystem);
        Assert.assertEquals(systemTest.getName(), newSystem.getName());
        Assert.assertEquals(user, newSystem.getUserId());

        SystemService.delete(newSystem);
    }

    @Test
    public void updateSystemTest(){
        System systemTest = new System();
        systemTest.setName("createTestName");
        systemTest.setUserId(user);

        int id = SystemService.save(systemTest);
        System newSystem = SystemService.findSystemById(id);

        newSystem.setName("updateTestName");

        Assert.assertNotNull(newSystem);
        Assert.assertNotEquals(systemTest.getName(), newSystem.getName());
        Assert.assertEquals(user, newSystem.getUserId());

        SystemService.update(newSystem);
        System updatedSystem = SystemService.findSystemById(id);

        Assert.assertNotNull(updatedSystem);
        Assert.assertEquals(newSystem.getName(), updatedSystem.getName());
        Assert.assertEquals(user, updatedSystem.getUserId());

        SystemService.delete(updatedSystem);
    }

    @Test(expected = NoResultException.class)
    public void deleteSystemTest(){
        System systemTest = new System();
        systemTest.setName("deleteTestName");
        systemTest.setUserId(user);

        int id = SystemService.save(systemTest);
        System newSystem = SystemService.findSystemById(id);

        Assert.assertNotNull(newSystem);

        SystemService.delete(newSystem);
        newSystem = SystemService.findSystemById(id);

        Assert.assertNull(newSystem);
    }
}
