package com.example.apharma.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Room implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.id);
        dest.writeList(this.sensorsList);
    }

    public void readFromParcel(Parcel source) {
        this.name = source.readString();
        this.id = source.readString();
        this.sensorsList = new ArrayList<Sensor>();
        source.readList(this.sensorsList, Sensor.class.getClassLoader());
    }

    protected Room(Parcel in) {
        this.name = in.readString();
        this.id = in.readString();
        this.sensorsList = new ArrayList<Sensor>();
        in.readList(this.sensorsList, Sensor.class.getClassLoader());
    }

    public static final Parcelable.Creator<Room> CREATOR = new Parcelable.Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel source) {
            return new Room(source);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };
}
