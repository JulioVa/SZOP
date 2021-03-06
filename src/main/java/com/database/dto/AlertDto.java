package com.database.dto;

import java.io.Serializable;

public class AlertDto implements Serializable {

    private Integer userId;
    private Integer sensorId;
    private String greaterLower;
    private Double value;
    private Boolean isActive;

    public AlertDto() {}

    public AlertDto(Integer userId, Integer sensorId, String greaterLower, Double value, Boolean isActive) {
        this.userId = userId;
        this.sensorId = sensorId;
        this.greaterLower = greaterLower;
        this.value = value;
        this.isActive = isActive;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }

    public String getGreaterLower() {
        return greaterLower;
    }

    public void setGreaterLower(String greaterLower) {
        this.greaterLower = greaterLower;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlertDto alertDto = (AlertDto) o;

        if (userId != null ? !userId.equals(alertDto.userId) : alertDto.userId != null) return false;
        if (sensorId != null ? !sensorId.equals(alertDto.sensorId) : alertDto.sensorId != null) return false;
        if (greaterLower != null ? !greaterLower.equals(alertDto.greaterLower) : alertDto.greaterLower != null)
            return false;
        if (value != null ? !value.equals(alertDto.value) : alertDto.value != null) return false;
        return isActive != null ? isActive.equals(alertDto.isActive) : alertDto.isActive == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (sensorId != null ? sensorId.hashCode() : 0);
        result = 31 * result + (greaterLower != null ? greaterLower.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (isActive != null ? isActive.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AlertDto{" +
                "userId=" + userId +
                ", sensorId=" + sensorId +
                ", greaterLower='" + greaterLower + '\'' +
                ", value=" + value +
                ", isActive=" + isActive +
                '}';
    }
}
