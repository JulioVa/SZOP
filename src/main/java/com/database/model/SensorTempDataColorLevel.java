package com.database.model;

import java.util.List;

public class SensorTempDataColorLevel extends SensorTempData {

    private String color;
    private String name;

    public SensorTempDataColorLevel(String sensorID, List<Temperature> temps, String color, String name) {
        super(sensorID, temps);
        this.color = color;
        this.name = name;
    }

    public SensorTempDataColorLevel() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SensorTempDataColorLevel that = (SensorTempDataColorLevel) o;

        if (color != null ? !color.equals(that.color) : that.color != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SensorTempDataColorLevel{" +
                "color='" + color + '\'' +
                "name='" + name + '\'' +
                super.toString() +
                '}';
    }
}
