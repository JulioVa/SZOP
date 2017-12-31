package com.database.dto;

public class AlertIdLevelDto extends AlertDto {


    private int id;

    public AlertIdLevelDto() {}

    public AlertIdLevelDto(Integer userId, Integer sensorId, String greaterLower, Double value, int id) {
        super(userId, sensorId, greaterLower, value);
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

        AlertIdLevelDto that = (AlertIdLevelDto) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id;
        return result;
    }
}
