package com.database.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "sensor")
public class Sensor implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "sensor_id")
    private String sensorId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private Integer type;

    @Column(name = "last_update")
    private Date lastUpdate;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schema_id")
    private Schema schema;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "system_id")
    private System system;

    @Column(name = "schema_x")
    private Integer schemaX;

    @Column(name = "schema_y")
    private Integer schemaY;

    @Column(name = "color")
    private String color;

    public Sensor() {
    }

    public Sensor(String sensorId, String name, Integer type, Date lastUpdate, Boolean isActive, Schema schema, System system,
                  Integer schemaX, Integer schemaY, String color) {
        this.sensorId = sensorId;
        this.name = name;
        this.type = type;
        this.lastUpdate = lastUpdate;
        this.isActive = isActive;
        this.schema = schema;
        this.system = system;
        this.schemaX = schemaX;
        this.schemaY = schemaY;
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
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

    public Integer getSchemaX() {
        return schemaX;
    }

    public void setSchemaX(Integer schemaX) {
        this.schemaX = schemaX;
    }

    public Integer getSchemaY() {
        return schemaY;
    }

    public void setSchemaY(Integer schemaY) {
        this.schemaY = schemaY;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
        if (system != null ? !system.equals(sensor.system) : sensor.system != null) return false;
        return color != null ? color.equals(sensor.color) : sensor.color == null;
    }

    @Override
    public int hashCode() {
        int result = sensorId.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + type;
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (schema != null ? schema.hashCode() : 0);
        result = 31 * result + (system != null ? system.hashCode() : 0);
        result = 31 * result + (schemaX != null ? schemaX.hashCode() : 0);
        result = 31 * result + (schemaY != null ? schemaY.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "sensorId='" + sensorId + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", lastUpdate=" + lastUpdate +
                ", isActive=" + isActive +
                ", schema=" + schema +
                ", system=" + system +
                ", schemaX=" + schemaX +
                ", schemaY=" + schemaY +
                ", color='" + color + '\'' +
                '}';
    }
}
