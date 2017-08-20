package com.database;

import com.database.service.ScriptRunnerUtil;
import org.junit.After;
import org.junit.Before;

public class TestBase {

    private static final String CREATE_DATABASE_PATH = "src/main/resources/startup.sql";
    private static final String LOAD_DATA_PATH = "src/test/java/com/resources/createtestdata.sql";
    private static final String CLEAR_DATA_PATH = "src/test/java/com/resources/cleardatabase.sql";

    @Before
    public void createTestData() {
        try {
            ScriptRunnerUtil.executeScript(LOAD_DATA_PATH);
        } catch (Exception e) {
            // log exception
            e.printStackTrace();
        }
    }

    @After
    public void clearData() {
        try {
            ScriptRunnerUtil.executeScript(CLEAR_DATA_PATH);
        } catch (Exception e) {
            // log exception
            e.printStackTrace();
        }
    }
}
