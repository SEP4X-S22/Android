package com.example.apharma.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Sensor {
    private int id;
    @SerializedName("sensorType")
    private SensorType sensor;
    private int constraintMinValue;
    private int constraintMaxValue;
    private double readingValue;

    public Sensor(int id, SensorType sensor, int constraintMinValue, int constraintMaxValue, double readingValue) {
        this.id = id;
        this.sensor = sensor;
        this.constraintMinValue = constraintMinValue;
        this.constraintMaxValue = constraintMaxValue;
        this.readingValue = readingValue;
    }

    public double getReadingValue() {
        return readingValue;
    }

    public int getId() {
        return id;
    }

    public int getConstraintMinValue() {
        return constraintMinValue;
    }

    public int getConstraintMaxValue() {
        return constraintMaxValue;
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

    public void setConstraintMinValue(int constraintMinValue) {
        this.constraintMinValue = constraintMinValue;
    }

    public void setConstraintMaxValue(int constraintMaxValue) {
        this.constraintMaxValue = constraintMaxValue;
    }

    public enum SensorType {
        Humidity, CO2, Light, Temperature
    }
}
