package com.database.model;

import java.util.List;

public class SensorTempData {

    private String sensorID;
    private List<Temperature> temps;

    public SensorTempData(String sensorID, List<Temperature> temps) {
        this.sensorID = sensorID;
        this.temps = temps;
    }

    public SensorTempData() {
    }

    public String getSensorID() {
        return sensorID;
    }

    public void setSensorID(String sensorID) {
        this.sensorID = sensorID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SensorTempData that = (SensorTempData) o;

        if (!sensorID.equals(that.sensorID)) return false;
        return temps != null ? temps.equals(that.temps) : that.temps == null;
    }

    @Override
    public int hashCode() {
        int result = sensorID.hashCode();
        result = 31 * result + (temps != null ? temps.hashCode() : 0);
        return result;
    }
}
