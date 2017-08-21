package com.database.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sensor")
public class Sensor {

    @Id
    @GeneratedValue
    @Column(name = "id")
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schema_id")
    private Schema schema;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "system_id")
    private System system;

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

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sensor sensor = (Sensor) o;

        if (sensorId != sensor.sensorId) return false;
        if (type != sensor.type) return false;
        if (isActive != sensor.isActive) return false;
        if (schemaX != sensor.schemaX) return false;
        if (schemaY != sensor.schemaY) return false;
        if (name != null ? !name.equals(sensor.name) : sensor.name != null) return false;
        if (lastUpdate != null ? !lastUpdate.equals(sensor.lastUpdate) : sensor.lastUpdate != null) return false;
        if (schema != null ? !schema.equals(sensor.schema) : sensor.schema != null) return false;
        return system != null ? system.equals(sensor.system) : sensor.system == null;
    }

    @Override
    public int hashCode() {
        int result = sensorId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + type;
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (schema != null ? schema.hashCode() : 0);
        result = 31 * result + (system != null ? system.hashCode() : 0);
        result = 31 * result + schemaX;
        result = 31 * result + schemaY;
        return result;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "sensorId=" + sensorId +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", lastUpdate=" + lastUpdate +
                ", isActive=" + isActive +
                ", schema=" + schema +
                ", system=" + system +
                ", schemaX=" + schemaX +
                ", schemaY=" + schemaY +
                '}';
    }
}
