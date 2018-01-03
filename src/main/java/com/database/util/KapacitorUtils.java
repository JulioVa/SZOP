package com.database.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class KapacitorUtils {

    public static String createAlertCommand(){
        StringBuilder replaceCommand = new StringBuilder();

        return replaceCommand.toString();
    }

    public static String defineNewTaskCommand(){
        StringBuilder defineCommand = new StringBuilder();
        return defineCommand.toString();
    }

    public static String runTaskCommand(){
        StringBuilder runTaskCommand = new StringBuilder();
        return runTaskCommand.toString();
    }

    public static String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        Process p;
        try {
            String[] cmd = {"/bin/bash","-c",command};
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line).append("\n");
            }
        } catch (Exception e) {
        }
        return output.toString();
    }

}
