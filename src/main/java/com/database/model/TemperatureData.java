package com.database.model;

public class TemperatureData {

    private String date;
    private String type;
    private String sensorId;
    private double value;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemperatureData tempData = (TemperatureData) o;

        if (value != tempData.value) return false;
        if (type != null ? !type.equals(tempData.type) : tempData.sensorId != null) return false;
        if (date != tempData.date) return false;
        return sensorId != null ? sensorId.equals(tempData.sensorId) : tempData.sensorId == null;
    }

    @Override
    public String toString() {
        return "TemperatureData{" +
                "sensorId=" + sensorId +
                ", type=" + type +
                ", date=" + date +
                ", value=" + value +
                '}';
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = date.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + sensorId.hashCode();
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
