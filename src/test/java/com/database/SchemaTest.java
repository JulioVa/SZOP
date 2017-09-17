package com.database;

import com.database.model.Schema;
import com.database.service.SchemaService;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.NoResultException;
import java.util.List;

public class SchemaTest extends TestBase {

    @Test
    public void findSchemaByIdTest() {
        Schema schemaTest = SchemaService.findSchemaById(1);
        Assert.assertNotNull(schemaTest);
        Assert.assertEquals(1, schemaTest.getId());
        Assert.assertEquals("schema1", schemaTest.getName());
        //  Assert.assertEquals("dgfdsghfdthf".getBytes(),schemaTest.getImg());
    }

    @Test
    public void findAllTest() {
        Schema schemaTest = SchemaService.findSchemaById(1);

        List<Schema> schemasTest = SchemaService.findAll();
        Assert.assertNotNull(schemasTest);
        Assert.assertFalse(schemasTest.isEmpty());
        Assert.assertTrue(schemasTest.contains(schemaTest));
    }

    @Test
    public void createSchemaTest() {
        Schema schemaTest = new Schema();
        schemaTest.setName("testSchema");
        schemaTest.setImg("lala".getBytes());

        int id = SchemaService.save(schemaTest);
        Schema newSchema = SchemaService.findSchemaById(id);

        Assert.assertNotNull(newSchema);
        Assert.assertEquals(schemaTest.getName(), newSchema.getName());
        //Assert.assertEquals(schemaTest.getImg(), newSchema.getImg());

        SchemaService.delete(newSchema);
    }

    @Test
    public void updateSchemaTest() {
        Schema schemaTest = new Schema();
        schemaTest.setName("testSchema");
        schemaTest.setImg("lala".getBytes());

        int id = SchemaService.save(schemaTest);
        Schema newSchema = SchemaService.findSchemaById(id);

        newSchema.setName("newSchema");

        Assert.assertNotNull(newSchema);
        Assert.assertNotEquals(schemaTest.getName(), newSchema.getName());
        //Assert.assertEquals(schemaTest.getImg(), newSchema.getImg());

        SchemaService.update(newSchema);
        Schema updatedSchema = SchemaService.findSchemaById(id);

        Assert.assertNotNull(updatedSchema);
        Assert.assertEquals(newSchema.getName(), updatedSchema.getName());
        //Assert.assertEquals(newSchema.getImg(), updatedSchema.getImg());

        SchemaService.delete(updatedSchema);
    }

    @Test(expected = NoResultException.class)
    public void deleteSchemaTest() {
        Schema schemaTest = new Schema();
        schemaTest.setName("testSchema");
        schemaTest.setImg("lala".getBytes());

        int id = SchemaService.save(schemaTest);
        Schema newSchema = SchemaService.findSchemaById(id);

        Assert.assertNotNull(newSchema);

        SchemaService.delete(newSchema);
        newSchema = SchemaService.findSchemaById(id);

        Assert.assertNull(newSchema);
    }
}
