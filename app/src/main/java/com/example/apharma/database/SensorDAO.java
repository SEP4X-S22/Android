package com.example.apharma.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.apharma.models.Sensor;
import java.util.List;

@Dao
public interface SensorDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Sensor sensor);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Sensor> sensorListList);

    @Update
    void update(Sensor sensor);

    @Delete
    void delete(Sensor sensor);

    @Query("DELETE FROM sensor_table")
    void deleteAll();

    @Query("SELECT * FROM sensor_table")
    LiveData<List<Sensor>> getAll();

    @Query("SELECT * FROM sensor_table WHERE roomId = :roomId")
    LiveData<List<Sensor>> getAllInRoom(String roomId);
}
