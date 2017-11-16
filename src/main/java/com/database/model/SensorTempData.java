package com.database.model;

import java.io.Serializable;
import java.util.List;

public class SensorTempData implements Serializable {

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

    public List<Temperature> getTemps() {
        return temps;
    }

    public void setTemps(List<Temperature> temps) {
        this.temps = temps;
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

    @Override
    public String toString() {
        return "SensorTempData{" +
                "sensorID='" + sensorID + '\'' +
                ", temps=" + temps +
                '}';
    }
}
