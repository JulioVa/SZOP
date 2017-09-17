package com.database;

import com.database.model.User;
import com.database.service.SchemaService;
import com.database.service.UserService;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.NoResultException;
import java.util.List;

public class UserTest extends TestBase {

    @Test
    public void findUserByIdTest(){
        User userTest = UserService.findUserById(1);
        Assert.assertNotNull(userTest);
        Assert.assertEquals(1, userTest.getId());
        Assert.assertEquals("name1", userTest.getName());
        Assert.assertEquals("dfsgf2376", userTest.getPassword());
        Assert.assertEquals("mail@name1.com", userTest.getMail());
    }

    @Test
    public void findAllTest(){
        User userTest = UserService.findUserById(1);

        List<User> usersTest = UserService.findAll();
        Assert.assertNotNull(usersTest);
        Assert.assertFalse(usersTest.isEmpty());
        Assert.assertTrue(usersTest.contains(userTest));
    }

    @Test
    public void createUserTest(){
        User userTest = new User();
        userTest.setName("createTestName");
        userTest.setMail("createTest@Test.eu");
        userTest.setPassword("createTestPassword");

        int id = UserService.save(userTest);
        User userTest2 = UserService.findUserById(id);

        Assert.assertNotNull(userTest2);
        Assert.assertEquals(userTest.getName(), userTest2.getName());
        Assert.assertEquals(userTest.getMail(), userTest2.getMail());
        Assert.assertEquals(userTest.getPassword(), userTest2.getPassword());

        UserService.delete(userTest2);
    }

    @Test
    public void updateUsetTest(){
        User userTest = new User();
        userTest.setName("createTestName");
        userTest.setMail("updateTest@Test.eu");
        userTest.setPassword("updateTestPassword");

        int id = UserService.save(userTest);
        User userTest2 = UserService.findUserById(id);
        userTest2.setName("updateTestName");

        Assert.assertNotNull(userTest2);
        Assert.assertNotEquals(userTest.getName(), userTest2.getName());
        Assert.assertEquals(userTest.getMail(), userTest2.getMail());
        Assert.assertEquals(userTest.getPassword(), userTest2.getPassword());

        UserService.update(userTest2);
        User updatedUser = UserService.findUserById(id);

        Assert.assertNotNull(updatedUser);
        Assert.assertEquals(updatedUser.getName(), userTest2.getName());
        Assert.assertEquals(updatedUser.getMail(), userTest2.getMail());
        Assert.assertEquals(updatedUser.getPassword(), userTest2.getPassword());

        UserService.delete(updatedUser);
    }

    @Test(expected = NoResultException.class)
    public void deleteUsrTest(){
        User userTest = new User();
        userTest.setName("deleteTestName");
        userTest.setMail("deleteTest@Test.eu");
        userTest.setPassword("deleteTestPassword");

        int id = UserService.save(userTest);
        User userTest2 = UserService.findUserById(id);

        Assert.assertNotNull(userTest2);

        UserService.delete(userTest2);
        userTest2 = UserService.findUserById(id);

        Assert.assertNull(userTest2);
    }
}
