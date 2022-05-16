package com.example.apharma.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private String name;
    @SerializedName("id")
    private String id;
    @SerializedName("sensorsList")
    private List<Sensor> sensorsList;

    public Room(String name, String id) {
        this.name = name;
        this.id = id;
        this.sensorsList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sensor> getSensors() {
        return sensorsList;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensorsList = sensors;
    }

    public boolean isEmpty() {
        return sensorsList.isEmpty();
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", sensorData=" + sensorsList +
                '}';
    }
}
