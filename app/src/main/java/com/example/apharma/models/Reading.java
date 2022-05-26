package com.example.apharma.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "reading_table")
public class Reading {

    @PrimaryKey
    private int id;
    private double readingValue;
    private String timestamp;
    private int sensorId;
    private String sensorType;
    private String roomId;



    public Reading() {
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
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
                && timestamp.equals(reading.timestamp);
    }

    @Ignore
    public Reading(int readingValue, String timeStamp) {
        this.readingValue = readingValue;
        this.timestamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Reading{" + "id=" + id + ", readingValue=" + readingValue
                + ", timestamp=" + timestamp + '}';
    }

    public void setReadingValue(double readingValue) {
        this.readingValue = readingValue;
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


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
