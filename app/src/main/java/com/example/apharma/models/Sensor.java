package com.example.apharma.models;

import java.util.ArrayList;
import java.util.List;

public class Sensor {
    private int id;
    private SensorType sensor;
    private List<Reading> readings;

    public Sensor(int id, SensorType sensor) {
        this.id = id;
        this.sensor = sensor;
        this.readings = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public SensorType getSensor() {
        return sensor;
    }

    public List<Reading> getReadings() {
        return readings;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSensor(SensorType sensor) {
        this.sensor = sensor;
    }

    public void setReadings(List<Reading> readings) {
        this.readings = readings;
    }

    public enum SensorType {
        Humidity, CO2, Light, Temperature
    }
}
