package com.example.apharma.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "sensor_table")
public class Sensor {
    @PrimaryKey
    private int id;
    @SerializedName("sensorType")
    private SensorType sensor;
    private int constraintMinValue;
    private int constraintMaxValue;
    private double readingValue;
    private String roomId;

    public Sensor() {
    }

    @Ignore
    public Sensor(int id, SensorType sensor, int constraintMinValue, int constraintMaxValue, double readingValue) {
        this.id = id;
        this.sensor = sensor;
        this.constraintMinValue = constraintMinValue;
        this.constraintMaxValue = constraintMaxValue;
        this.readingValue = readingValue;
        roomId = "";
    }

    public void setReadingValue(double readingValue) {
        this.readingValue = readingValue;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
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
