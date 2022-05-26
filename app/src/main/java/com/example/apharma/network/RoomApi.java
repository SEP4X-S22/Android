package com.example.apharma.network;

import com.example.apharma.models.Reading;
import com.example.apharma.models.Room;
import com.example.apharma.models.Sensor;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RoomApi {
    @GET("/rooms")
    Call<ArrayList<Room>> getRooms();

    @GET("/sensors/{roomId}")
    Call<ArrayList<Sensor>> getSensors(@Path("roomId") String room);

    @GET("/readings/{roomId}/{sensorType}")
    Call<ArrayList<Reading>> getSensorData(@Path("roomId") String room, @Path("sensorType") String sensorType);

    @PATCH("/sensor/constraints")
    Call<Void> setConstraints(@Query("id") int id, @Query("min") int min, @Query("max") int max);

}
