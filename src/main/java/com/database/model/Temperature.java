package com.database.model;

import java.io.Serializable;

/**
 * Created by damia_000 on 2017-06-10.
 */
public class Temperature implements Comparable<Temperature>, Serializable {

    private double y;
    private long x;

    public Temperature(double value, long time) {
        this.y = value;
        this.x = time;
    }

    public double getY() {
        return y;
    }

    public void setY(double value) {
        this.y = value;
    }

    public long getX() {
        return x;
    }

    public void setX(long time) {
        this.x = time;
    }

    public Temperature(){
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Temperature that = (Temperature) o;

        if (Double.compare(that.y, y) != 0) return false;
        return x == that.x;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(y);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (x ^ (x >>> 32));
        return result;
    }

    @Override
    public int compareTo(Temperature o) {
        if(x >o.x){
            return 1;
        } else if(x <o.x){
            return -1;
        } else return 0;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "y=" + y +
                ", x=" + x +
                '}';
    }
}
