package com.database.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class SensorDto implements Serializable {

    private Integer sensorId;
    private String name;
    private Integer type;
    private Date lastUpdate;
    private Boolean isActive;
    private Integer schemaId;
    private Integer systemId;
    private Integer schemaX;
    private Integer schemaY;

    public SensorDto() {}

    public SensorDto(Integer sensorId, String name, int type, Date lastUpdate, boolean isActive, int schemaId, int systemId, int schemaX, int schemaY) {
        this.sensorId = sensorId;
        this.name = name;
        this.type = type;
        this.lastUpdate = lastUpdate;
        this.isActive = isActive;
        this.schemaId = schemaId;
        this.systemId = systemId;
        this.schemaX = schemaX;
        this.schemaY = schemaY;
    }

    public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
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

    public Integer getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(Integer schemaId) {
        this.schemaId = schemaId;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SensorDto sensorDto = (SensorDto) o;

        if (isActive != sensorDto.isActive) return false;
        if (sensorId != null ? !sensorId.equals(sensorDto.sensorId) : sensorDto.sensorId != null) return false;
        if (name != null ? !name.equals(sensorDto.name) : sensorDto.name != null) return false;
        if (type != null ? !type.equals(sensorDto.type) : sensorDto.type != null) return false;
        if (lastUpdate != null ? !lastUpdate.equals(sensorDto.lastUpdate) : sensorDto.lastUpdate != null) return false;
        if (schemaId != null ? !schemaId.equals(sensorDto.schemaId) : sensorDto.schemaId != null) return false;
        if (systemId != null ? !systemId.equals(sensorDto.systemId) : sensorDto.systemId != null) return false;
        if (schemaX != null ? !schemaX.equals(sensorDto.schemaX) : sensorDto.schemaX != null) return false;
        return schemaY != null ? schemaY.equals(sensorDto.schemaY) : sensorDto.schemaY == null;
    }

    @Override
    public int hashCode() {
        int result = sensorId != null ? sensorId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (schemaId != null ? schemaId.hashCode() : 0);
        result = 31 * result + (systemId != null ? systemId.hashCode() : 0);
        result = 31 * result + (schemaX != null ? schemaX.hashCode() : 0);
        result = 31 * result + (schemaY != null ? schemaY.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SensorDto{" +
                "sensorId=" + sensorId +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", lastUpdate=" + lastUpdate +
                ", isActive=" + isActive +
                ", schemaId=" + schemaId +
                ", systemId=" + systemId +
                ", schemaX=" + schemaX +
                ", schemaY=" + schemaY +
                '}';
    }
}
