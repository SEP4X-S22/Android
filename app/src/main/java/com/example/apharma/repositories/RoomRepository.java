package com.example.apharma.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.apharma.models.Room;
import com.example.apharma.network.RoomApi;
import com.example.apharma.network.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.annotations.EverythingIsNonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomRepository {
    private static RoomRepository instance;
    private MutableLiveData<ArrayList<Room> > rooms;

    public static RoomRepository getInstance() {
        if (instance == null) {
            instance = new RoomRepository();
        }
        return instance;
    }

    public RoomRepository() {
        rooms = new MutableLiveData<>();

    }
    public LiveData <ArrayList<Room>> getRooms() {
        return rooms;
    }

    public void fetchRooms() {
        RoomApi roomApi = ServiceGenerator.getRoomApi();
        Call<ArrayList<Room>>  call = roomApi.getRooms();
        call.enqueue(new Callback<ArrayList<Room>>  () {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ArrayList<Room>>  call, Response <ArrayList<Room>>  response) {
                if (response.isSuccessful()) {
                    System.out.println("############"+response.body());

                    rooms.setValue(response.body());
                    }else {
                    System.out.println("Failure ###");
                    System.out.println("########"+response.message());
                }
            }


            @Override
            public void onFailure(@NonNull Call<ArrayList<Room>>  call, Throwable t) {
                Log.i("Retrofit", "#######Something went wrong :(");
            }
        });
    }
}
