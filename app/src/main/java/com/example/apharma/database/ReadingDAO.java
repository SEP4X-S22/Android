package com.example.apharma.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.apharma.models.Reading;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ReadingDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Reading reading);

    @Update
    void update(Reading reading);

    @Delete
    void delete(Reading reading);

    @Query("DELETE FROM reading_table")
    void deleteAllReadings();

    @Query("SELECT * FROM reading_table")
     LiveData<List<Reading>> getAllReadings();
}
