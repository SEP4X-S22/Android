package com.example.apharma.repositories;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.apharma.database.LocalDatabase;
import com.example.apharma.database.RoomDAO;
import com.example.apharma.models.Room;
import com.example.apharma.network.RoomApi;
import com.example.apharma.network.ServiceGenerator;

import java.net.InetAddress;
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
    private MutableLiveData<List<Room>> rooms;
    private LiveData<List<Room>> listOfRooms;
    LocalDatabase localDatabase;
    RoomDAO roomDAO;
    ExecutorService executorService;
    Handler mainThreadHandler;


    public static RoomRepository getInstance(Application application) {
        if (instance == null) {
            instance = new RoomRepository(application);
        }
        return instance;
    }

    public RoomRepository(Application application) {
        rooms = new MutableLiveData<>();

        localDatabase = LocalDatabase.getInstance(application);
        roomDAO = localDatabase.roomDAO();
        listOfRooms = roomDAO.getAllRooms();
                executorService = Executors.newFixedThreadPool(2);
        mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    }

    public LiveData<List<Room>> getRooms() {
        return rooms;
    }

    public LiveData<List<Room>> getListOfRooms() {


            if (listOfRooms == null) {
                listOfRooms = roomDAO.getAllRooms();
            }

            return listOfRooms;

    }

    public void insert(Room room) {
        executorService.execute(() -> roomDAO.insert(room));
    }


    public void fetchRooms() {
        RoomApi roomApi = ServiceGenerator.getRoomApi();
        Call<ArrayList<Room>> call = roomApi.getRooms();

        Log.i("internet", internetIsConnected() + "");

        if (internetIsConnected()) {
            call.enqueue(new Callback<ArrayList<Room>>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<ArrayList<Room>> call, Response<ArrayList<Room>> response) {
                    if (response.isSuccessful()) {
                        System.out.println("############" + response.body());




                        rooms.setValue(response.body());
                        for (Room room : response.body()) {
                            insert(room);
                        }
                    } else {
                        System.out.println("Failure ###");
                        System.out.println("########" + response.message());
                    }
                }


                @Override
                public void onFailure(@NonNull Call<ArrayList<Room>> call, Throwable t) {
                    Log.i("Retrofit", "#######Something went wrong :(");
                }
            });

        } else {
//            rooms.setValue(roomDAO.getAllRooms().getValue());

          rooms.setValue(getListOfRooms().getValue());
        }


    }

    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }


}
