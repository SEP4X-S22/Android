package com.example.apharma.repositories;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.apharma.db.RoomsDao;
import com.example.apharma.db.RoomsDatabase;
import com.example.apharma.models.Room;
import com.example.apharma.network.RoomApi;
import com.example.apharma.network.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.internal.annotations.EverythingIsNonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomRepository {
    private static RoomRepository instance;
    private LiveData<List<Room> > rooms;
    private RoomsDao roomsDao;
    private final ExecutorService executorService;

    public static RoomRepository getInstance(Application application) {
        if (instance == null) {
            instance = new RoomRepository(application);
        }
        return instance;
    }

    public RoomRepository(Application application   ) {
        RoomsDatabase roomsDatabase = RoomsDatabase.getInstance(application);
        roomsDao = roomsDatabase.roomsDao();
        rooms = roomsDao.getAllRooms();
        executorService = Executors.newFixedThreadPool(2);
//        rooms = new MutableLiveData<>();

    }
    public LiveData <List<Room>> getRooms() {
        return rooms;
    }

    public void insert(List<Room> rooms) {
        executorService.execute(() -> roomsDao.insert(rooms));
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

                    insert(response.body());
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
