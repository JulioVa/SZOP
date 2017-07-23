package com.database.model;

import java.time.LocalDateTime;

public class Sensor {

    private int id;
    private int sensorId;
    private String name;
    private int type;
    private LocalDateTime lastUpdate;
    private boolean isActive;
    private int raspberryId;
    private int schemaId;
    private int schemaX;
    private int schemaY;

    public Sensor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getRaspberryId() {
        return raspberryId;
    }

    public void setRaspberryId(int raspberryId) {
        this.raspberryId = raspberryId;
    }

    public int getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(int schemaId) {
        this.schemaId = schemaId;
    }

    public int getSchemaX() {
        return schemaX;
    }

    public void setSchemaX(int schemaX) {
        this.schemaX = schemaX;
    }

    public int getSchemaY() {
        return schemaY;
    }

    public void setSchemaY(int schemaY) {
        this.schemaY = schemaY;
    }
}
