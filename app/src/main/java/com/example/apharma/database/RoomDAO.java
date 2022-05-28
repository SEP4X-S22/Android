package com.example.apharma.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.apharma.models.Room;
import java.util.List;

@Dao
public interface RoomDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Room room);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Room> roomList);

    @Update
    void update(Room room);

    @Delete
    void delete(Room room);

    @Query("DELETE FROM room_table")
    void deleteAll();

    @Query("SELECT * FROM room_table")
    LiveData<List<Room>> getAll();
}
