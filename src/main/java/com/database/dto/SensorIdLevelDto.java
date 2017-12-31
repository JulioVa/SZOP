package com.database.dto;

import java.util.Date;

public class SensorIdLevelDto extends SensorDto {

    private int id;

    public SensorIdLevelDto() {}

    public SensorIdLevelDto(String sensorId, String name, int type, Date lastUpdate, boolean isActive, int schemaId, int systemId,
                            int schemaX, int schemaY, int id, String color) {
        super(sensorId, name, type, lastUpdate, isActive, schemaId, systemId, schemaX, schemaY, color);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SensorIdLevelDto that = (SensorIdLevelDto) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id;
        return result;
    }
}
