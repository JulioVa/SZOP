package com.database.service;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.ibatis.common.jdbc.ScriptRunner;

public class ScriptRunnerUtil {

    private static final String DRIVER_NAME = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/szopapplicationdatabase";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static void executeScript(String path) throws ClassNotFoundException, SQLException, IOException {
        Class.forName(DRIVER_NAME);
        Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
        ScriptRunner sr = new ScriptRunner(con, false, false);
        Reader reader = new BufferedReader(new FileReader(path));
        sr.runScript(reader);
        reader.close();
        con.close();
    }
}
