package com.database.model;

public class TemperatureData {

    private long date;
    private String type;
    private String sensorId;
    private float value;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
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

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
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
        int result = (int) (date ^ (date >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (sensorId != null ? sensorId.hashCode() : 0);
        result = 31 * result + (value != +0.0f ? Float.floatToIntBits(value) : 0);
        return result;
    }
}
