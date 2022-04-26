package com.example.apharma.models;

public class Device {

    private String name;
    private int id;

    public Device(String name, int id){
        this.name=name;
        this.id=id;
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

    @Override
    public String toString() {
        return "Device{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
