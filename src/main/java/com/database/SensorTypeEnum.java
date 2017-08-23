package com.database;

public enum SensorTypeEnum {
    TEMPERATURE(1),
    HUMIDITY(2);

    private final int id;
    SensorTypeEnum(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
}
