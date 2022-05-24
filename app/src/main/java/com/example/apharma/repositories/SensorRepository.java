package com.example.apharma.repositories;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.apharma.db.RoomsDao;
import com.example.apharma.db.RoomsDatabase;
import com.example.apharma.db.SensorsDao;
import com.example.apharma.models.Room;
import com.example.apharma.models.Sensor;
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

public class SensorRepository {
    private static SensorRepository instance;
    private LiveData<List<Sensor>> sensors;
    private SensorsDao sensorsDao;
    private final ExecutorService executorService;
Application application;
    public static SensorRepository getInstance() {
        if (instance == null) {
            instance = new SensorRepository();
        }
        return instance;
    }

    public SensorRepository() {
        RoomsDatabase roomsDatabase = RoomsDatabase.getInstance(application);
        sensorsDao = roomsDatabase.sensorsDao();
        sensors = sensorsDao.getAllSensors();
        executorService = Executors.newFixedThreadPool(2);

    }

    public LiveData<List<Sensor>> getSensors() {
        return sensors;
    }

    public void insert(List<Sensor> sensors) {
        executorService.execute(() -> sensorsDao.insert(sensors));
    }

    public void fetchSensors(String room) {
        RoomApi roomApi = ServiceGenerator.getRoomApi();
        Call<ArrayList<Sensor>> call = roomApi.getSensors(room);
        call.enqueue(new Callback<ArrayList<Sensor>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ArrayList<Sensor>> call, Response<ArrayList<Sensor>> response) {
                if (response.isSuccessful()) {
//                    insert(response.body());
                    System.out.println("############ SIZE" + response.body().size());


                } else {
                    System.out.println("Failure ###");
                    System.out.println("########" + response.message());
                }
            }


            @Override
            public void onFailure(@NonNull Call<ArrayList<Sensor>> call, Throwable t) {
                Log.i("Retrofit", "#######Something went wrong :(");
            }
        });
    }

    public void updateConstraints(int id, int minValue, int maxValue) {
        RoomApi roomApi = ServiceGenerator.getRoomApi();
        Call<Void> call = roomApi.setConstraints(id, minValue, maxValue);


        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("success");
                } else {
                    System.out.println("Failure ======");
                    System.out.println(response.message() + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("!!! Failure");
                t.printStackTrace();
            }
        });
    }
}
