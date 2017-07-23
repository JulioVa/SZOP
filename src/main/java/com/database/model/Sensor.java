package com.database.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sensor")
public class Sensor {

    @Id
    @GeneratedValue
    @Column(name ="id")
    private int id;

    @Column(name = "sensor_id")
    private int sensorId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private int type;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "raspberry_id")
    private int raspberryId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schema_id")
    private Schema schema;

    @Column(name = "schema_x")
    private int schemaX;

    @Column(name = "schema_y")
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

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schemaId) {
        this.schema = schema;
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
