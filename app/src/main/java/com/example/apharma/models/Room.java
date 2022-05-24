package com.example.apharma.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "rooms")
public class Room {

    private String name;
    @SerializedName("id")
    @PrimaryKey
    @NotNull
    private String id;
    private int sensorsCount;

    public Room(String name, String id, int sensorsCount) {
        this.name = name;
        this.id = id;
        this.sensorsCount = sensorsCount;
    }

    public int getSensorsCount() {
        return sensorsCount;
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


    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
