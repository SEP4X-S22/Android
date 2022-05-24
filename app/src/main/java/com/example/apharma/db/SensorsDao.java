package com.example.apharma.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.apharma.models.Sensor;

import java.util.List;
@Dao
public interface SensorsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Sensor> sensors);

    @Query("SELECT * FROM sensors")
    LiveData<List<Sensor>> getAllSensors();
}
