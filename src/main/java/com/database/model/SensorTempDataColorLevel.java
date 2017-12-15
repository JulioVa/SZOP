package com.database.model;

import java.util.List;

public class SensorTempDataColorLevel extends SensorTempData {

    private String color;

    public SensorTempDataColorLevel(String sensorID, List<Temperature> temps, String color) {
        super(sensorID, temps);
        this.color = color;
    }

    public SensorTempDataColorLevel(String color) {
        this.color = color;
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
        if (!super.equals(o)) return false;

        SensorTempDataColorLevel that = (SensorTempDataColorLevel) o;

        return color != null ? color.equals(that.color) : that.color == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SensorTempDataColorLevel{" +
                "color='" + color + '\'' +
                super.toString() +
                '}';
    }
}
