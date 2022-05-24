package com.example.apharma.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.apharma.models.Room;

import java.util.List;
@Dao
public interface RoomsDao {
    @Insert
    void insert(Room room);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Room> rooms);

    @Query("SELECT * FROM rooms")
    LiveData<List<Room>> getAllRooms();

    @Query("DELETE FROM rooms")
    void deleteAllRooms();
}
