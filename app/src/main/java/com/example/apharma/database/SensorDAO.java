package com.example.apharma.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.apharma.models.Reading;
import com.example.apharma.models.Room;
import com.example.apharma.models.Sensor;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface SensorDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Sensor sensor);

    @Update
    void update(Sensor sensor);

    @Query("SELECT * FROM sensor_table")
    LiveData<List<Sensor>> getAllSensors();

    @Query("SELECT * FROM sensor_table WHERE roomId=:id")
    LiveData<List<Sensor>> getAllSensorsFromRoom(String id);

    @Query("DELETE FROM sensor_table")
    void deleteAllSensors();
}
