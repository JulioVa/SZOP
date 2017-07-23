package com.database.model;

/**
 * Created by damia_000 on 2017-06-10.
 */
public class Temperature {

    static float temperature = 0;

    public Temperature(){
    }

    public void setTemperature(float temperature){
        this.temperature = temperature;
    }

    public float getTemperature(){
        return temperature;
    }

    @Override
    public String toString() {
        return String.valueOf(temperature);
    }
}
