package com.database.model;

public class Alert {

    private int id;
    private int userId;
    private int sensorId;
    private String greaterLower;
    private double value;

    public Alert() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public String getGreaterLower() {
        return greaterLower;
    }

    public void setGreaterLower(String greaterLower) {
        this.greaterLower = greaterLower;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
