package com.example.apharma.models;

import java.io.Serializable;

public class MeasurementData implements Serializable     {
    private int sensorId;
    private double value;
    private double minValue;
    private double maxValue;
    private String timeStamp;
    private String type;

    public MeasurementData(int sensorId, double value, double minValue, double maxValue, String timeStamp, String type) {
        this.sensorId = sensorId;
        this.value = value;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.timeStamp = timeStamp;
        this.type = type;
    }

    public int getSensorId() {
        return sensorId;
    }

    public double getValue() {
        return value;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getType() {
        return type;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MeasurementData{" +
                "sensorId=" + sensorId +
                ", value=" + value +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", timeStamp='" + timeStamp + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
