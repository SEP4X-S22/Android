package com.example.apharma.repositories;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.apharma.database.LocalDatabase;
import com.example.apharma.database.RoomDAO;
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

        Log.i("internet", isConnected() + "");

        if (isConnected()) {
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
            rooms.setValue(getListOfRooms().getValue());
        }


    }

    public boolean isConnected() {
        @SuppressLint("RestrictedApi") ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        /* NetworkInfo is deprecated in API 29 so we have to check separately for higher API Levels */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Network network = cm.getActiveNetwork();
            if (network == null) {
                return false;
            }
            NetworkCapabilities networkCapabilities = cm.getNetworkCapabilities(network);
            if (networkCapabilities == null) {
                return false;
            }
            boolean isInternetSuspended = !networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED);
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                    && !isInternetSuspended;
        } else {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
    }


}
