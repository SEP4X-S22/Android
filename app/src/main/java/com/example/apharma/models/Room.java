package com.example.apharma.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {

    private String name;
    private int id;
    private List<MeasurementData> sensorData;

    public Room(String name, int id) {
        this.name = name;
        this.id = id;
        sensorData = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MeasurementData> getSensors() {
        return sensorData;
    }

    public void setSensors(List<MeasurementData> sensors) {
        this.sensorData = sensors;
    }
    public boolean isEmpty() {
        return sensorData.isEmpty();
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", sensorData=" + sensorData +
                '}';
    }
}
