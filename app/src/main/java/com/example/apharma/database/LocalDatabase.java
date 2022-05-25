package com.example.apharma.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.apharma.models.Reading;
import com.example.apharma.models.Room;
import com.example.apharma.models.Sensor;

@Database(entities = {Room.class, Sensor.class, Reading.class}, version = 6)
public abstract class LocalDatabase extends RoomDatabase {

    private static LocalDatabase instance;

    public abstract RoomDAO roomDAO();

    public abstract SensorDAO sensorDAO();

    public abstract ReadingDAO readingDAO();

    public static synchronized LocalDatabase getInstance(Context context) {
        if (instance == null) {
            instance = androidx.room.Room.databaseBuilder(context.getApplicationContext(),
                            LocalDatabase.class, "local_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
