package com.example.apharma.db;

import android.content.Context;

import androidx.room.Database;

import com.example.apharma.models.Room;
import com.example.apharma.models.Sensor;

@Database(entities = {Room.class, Sensor.class}, version = 7)
public abstract class RoomsDatabase extends androidx.room.RoomDatabase {
    private static RoomsDatabase instance;

    public abstract RoomsDao roomsDao();
    public abstract SensorsDao sensorsDao();

    public static synchronized RoomsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = androidx.room.Room.databaseBuilder(context.getApplicationContext(),
                    RoomsDatabase.class, "rooms")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
