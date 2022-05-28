package com.example.apharma.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.apharma.models.Reading;
import com.example.apharma.models.Sensor;

import java.util.List;

@Dao
public interface ReadingDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Reading reading);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Reading> readingList);

    @Update
    void update(Reading reading);

    @Delete
    void delete(Reading reading);

    @Query("DELETE FROM reading_table")
    void deleteAll();

    @Query("SELECT * FROM reading_table")
    LiveData<List<Reading>> getAll();

    @Query("SELECT * FROM reading_table WHERE roomId = :roomId AND sensorType = :sensorType")
    LiveData<List<Reading>> getAllFromRoom(String roomId, String sensorType);
}
