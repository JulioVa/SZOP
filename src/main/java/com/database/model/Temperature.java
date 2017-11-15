package com.database.model;

/**
 * Created by damia_000 on 2017-06-10.
 */
public class Temperature implements Comparable<Temperature>{

    private double value;
    private long time;

    public Temperature(double value, long time) {
        this.value = value;
        this.time = time;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Temperature(){
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Temperature that = (Temperature) o;

        if (Double.compare(that.value, value) != 0) return false;
        return time == that.time;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(value);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (time ^ (time >>> 32));
        return result;
    }

    @Override
    public int compareTo(Temperature o) {
        if(time>o.time){
            return 1;
        } else if(time<o.time){
            return -1;
        } else return 0;
    }
}
