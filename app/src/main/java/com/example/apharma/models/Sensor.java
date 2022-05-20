package com.example.apharma.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Sensor {
    private int id;
    @SerializedName("sensorType")
    private SensorType sensor;
    private double constraintMinValue;
    private double constraintMaxValue;
    private double readingValue;

    public Sensor(int id, SensorType sensor, double constraintMinValue, double constraintMaxValue, double readingValue) {
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

    public double getConstraintMinValue() {
        return constraintMinValue;
    }

    public double getConstraintMaxValue() {
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

    public void setConstraintMinValue(double constraintMinValue) {
        this.constraintMinValue = constraintMinValue;
    }

    public void setConstraintMaxValue(double constraintMaxValue) {
        this.constraintMaxValue = constraintMaxValue;
    }

    public enum SensorType {
        Humidity, CO2, Light, Temperature
    }
}
