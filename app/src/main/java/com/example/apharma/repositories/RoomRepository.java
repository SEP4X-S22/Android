package com.example.apharma.repositories;

public class RoomRepository {
    private static RoomRepository instance;

    public static RoomRepository getInstance() {
        if (instance == null) {
            instance = new RoomRepository();
        }
        return instance;
    }
}
