package com.database.model;

import javax.persistence.*;

@Entity
@Table(name = "alert")
public class Alert {

    @Id
    @GeneratedValue
    @Column(name ="id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @Column(name = "greater_lower")
    private String greaterLower;

    @Column(name = "value")
    private double value;

    public Alert() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public String getGreaterLower() {
        return greaterLower;
    }

    public void setGreaterLower(String greaterLower) {
        this.greaterLower = greaterLower;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
