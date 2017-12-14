package com.database.model;

import javax.persistence.*;

@Entity
@Table(name = "alert")
public class Alert {

    @Id
    @GeneratedValue
    @Column(name = "id")
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
    private Double value;

    @Column(name = "is_active")
    private Boolean isActive;

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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alert alert = (Alert) o;

        if (Double.compare(alert.value, value) != 0) return false;
        if (user != null ? !user.equals(alert.user) : alert.user != null) return false;
        if (sensor != null ? !sensor.equals(alert.sensor) : alert.sensor != null) return false;
        if (greaterLower != null ? !greaterLower.equals(alert.greaterLower) : alert.greaterLower != null) return false;
        return isActive != null ? isActive.equals(alert.isActive) : alert.isActive == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = user != null ? user.hashCode() : 0;
        result = 31 * result + (sensor != null ? sensor.hashCode() : 0);
        result = 31 * result + (greaterLower != null ? greaterLower.hashCode() : 0);
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isActive != null ? isActive.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "user=" + user +
                ", sensor=" + sensor +
                ", greaterLower='" + greaterLower + '\'' +
                ", value=" + value +
                ", isActive=" + isActive +
                '}';
    }
}
