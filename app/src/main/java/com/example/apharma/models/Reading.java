package com.example.apharma.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reading_table")
public class Reading {

    @PrimaryKey
    private int id;
    private double readingValue;
    private String timeStamp;
    private String sensorId;

    public Reading() {
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Reading reading = (Reading) o;
        return id == reading.id && readingValue == reading.readingValue
                && timeStamp.equals(reading.timeStamp);
    }

    public Reading(int readingValue, String timeStamp) {
        this.readingValue = readingValue;
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Reading{" + "id=" + id + ", readingValue=" + readingValue
                + ", timeStamp=" + timeStamp + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getReadingValue() {
        return readingValue;
    }

    public void setReadingValue(int readingValue) {
        this.readingValue = readingValue;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
