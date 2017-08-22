package com.database;

import com.database.service.ScriptRunnerUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;

public class TestBase {

    private static final Logger LOGGER = LogManager.getLogger("TestBase");

    private static final String LOAD_DATA_PATH = "src/test/java/com/resources/createtestdata.sql";
    private static final String CLEAR_DATA_PATH = "src/test/java/com/resources/cleardatabase.sql";

    @BeforeClass
    public static void createTestData() {
        try {
            ScriptRunnerUtil.executeScript(CLEAR_DATA_PATH);
            ScriptRunnerUtil.executeScript(LOAD_DATA_PATH);
        } catch (Exception e) {
            LOGGER.error("executing script error", e);
        }
    }
}
