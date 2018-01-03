package com.database.dto;

import java.util.Date;

public class SensorDetailsDto extends SensorIdLevelDto {

    private String systemName;
    private String value;

    public SensorDetailsDto() {}

    public SensorDetailsDto(String sensorId, String name, int type, Date lastUpdate, boolean isActive, int schemaId, int systemId, int schemaX, int schemaY, int id, String color, String systemName, String value) {
        super(sensorId, name, type, lastUpdate, isActive, schemaId, systemId, schemaX, schemaY, id, color);
        this.systemName = systemName;
        this.value = value;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SensorDetailsDto that = (SensorDetailsDto) o;

        if (systemName != null ? !systemName.equals(that.systemName) : that.systemName != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (systemName != null ? systemName.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
