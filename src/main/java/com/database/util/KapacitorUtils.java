package com.database.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.database.util.InfluxUtils.DB_NAME;

public class KapacitorUtils {

    public static String createAlertCommand(int alertId, String user, String sensor, String system, String condition, String title, String message){
        StringBuilder replaceCommand = new StringBuilder();
        replaceCommand.append("cp ./alerts/alert.tick ./alerts/");
        replaceCommand.append(alertId);
        replaceCommand.append(".tick && sed -i -e 's/%%USER%%/" + user + "/g' ./alerts/" + alertId + ".tick'\"");
        replaceCommand.append(" && sed -i -e 's/%%SENSOR%%/" + sensor + "/g' ./alerts/" + alertId + ".tick'\"");
        replaceCommand.append(" && sed -i -e 's/%%SYSTEM%%/" + system + "/g' ./alerts/" + alertId + ".tick'\"");
        replaceCommand.append(" && sed -i -e 's/%%CONDITION%%/" + condition + "/g' ./alerts/" + alertId + ".tick'\"");
        replaceCommand.append(" && sed -i -e 's/%%TITLE%%/" + title + "/g' ./alerts/" + alertId + ".tick'\"");
        replaceCommand.append(" && sed -i -e 's/%%MESSAGE%%/" + message + "/g' ./alerts/" + alertId + ".tick'\"");
        replaceCommand.append(" && sed -i -e 's/%%EMAIL%%/" + user + "/g' ./alerts/" + alertId + ".tick'\"");
        return replaceCommand.toString();
    }

    public static String defineNewTaskCommand(int alertId){
        StringBuilder defineCommand = new StringBuilder();
        defineCommand.append("kapacitor define ");
        defineCommand.append(alertId);
        defineCommand.append(" -type \"stream\" -tick ./alerts/");
        defineCommand.append(alertId);
        defineCommand.append(".tick -dbrp \"");
        defineCommand.append(DB_NAME);
        defineCommand.append("\".two_days");
        return defineCommand.toString();
    }

    public static String enableTaskCommand(int alertId){
        StringBuilder enableTaskCommand = new StringBuilder();
        enableTaskCommand.append("kapacitor enable ");
        enableTaskCommand.append(alertId);
        return enableTaskCommand.toString();
    }

    public static String disableTaskCommand(int alertId){
        StringBuilder enableTaskCommand = new StringBuilder();
        enableTaskCommand.append("kapacitor disable ");
        enableTaskCommand.append(alertId);
        return enableTaskCommand.toString();
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
