package com.example.apharma.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.apharma.models.Room;
import com.example.apharma.models.Sensor;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface RoomDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Room room);

    @Update
    void update(Room room);

    @Delete
    void delete(Room room);

    @Query("DELETE FROM room_table")
    void deleteAllRooms();

    @Query("SELECT * FROM room_table")
    LiveData<List<Room>> getAllRooms();

}
