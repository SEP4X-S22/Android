package com.example.apharma.models;

public class MeasurementData {
    private int sensorId;
    private double value;
    private String timeStamp;
    private String type;

public MeasurementData(int sensorId, double value, String timeStamp, String type)
{
    this.sensorId = sensorId;
    this.value = value;
    this.timeStamp = timeStamp;
    this.type = type;
}
    public int getSensorId() {
        return sensorId;
    }

    public double getValue() {
        return value;
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
                ", timeStamp='" + timeStamp + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
