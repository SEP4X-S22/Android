package com.example.apharma.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Sensor {
    private int id;
    @SerializedName("sensorType")
    private SensorType sensor;
    private double readingValue;

    public Sensor(int id, SensorType sensor, double readingValue) {
        this.id = id;
        this.sensor = sensor;
        this.readingValue = readingValue;
    }

    public double getReadingValue() {
        return readingValue;
    }

    public int getId() {
        return id;
    }

    public SensorType getSensor() {
        return sensor;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setSensor(SensorType sensor) {
        this.sensor = sensor;
    }

    public enum SensorType {
        Humidity, CO2, Light, Temperature
    }
}
