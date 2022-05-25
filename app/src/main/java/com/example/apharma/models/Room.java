package com.example.apharma.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "room_table")
public class Room {

    private String name;
    @SerializedName("id")
    @PrimaryKey @NonNull
    private String id;
    private int sensorsCount;

    public Room(){

    }


    public Room(String name, String id, int size) {
        this.name = name;
        this.id = id;
        this.sensorsCount = size;
    }

    public int getSensorsCount() {
        return sensorsCount;
    }

    public void setSensorsCount(int sensorsCount) {
        this.sensorsCount = sensorsCount;
    }

    public int getSize() {
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
