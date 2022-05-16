package com.example.apharma.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.apharma.models.Room;
import com.example.apharma.models.Sensor;
import com.example.apharma.network.RoomApi;
import com.example.apharma.network.ServiceGenerator;

import java.util.ArrayList;

import okhttp3.internal.annotations.EverythingIsNonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SensorRepository
{
    private static SensorRepository instance;
    private MutableLiveData<ArrayList<Sensor>> sensors;

    public static SensorRepository getInstance() {
        if (instance == null) {
            instance = new SensorRepository();
        }
        return instance;
    }

    public SensorRepository() {
        sensors = new MutableLiveData<>();

    }

    public LiveData<ArrayList<Sensor>> getSensors() {
        return sensors;
    }

    public void fetchSensors(String room) {
        RoomApi roomApi = ServiceGenerator.getRoomApi();
        Call<ArrayList<Sensor>>  call = roomApi.getSensors(room);
        call.enqueue(new Callback<ArrayList<Sensor>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ArrayList<Sensor>>  call, Response<ArrayList<Sensor>> response) {
                if (response.isSuccessful()) {
                    System.out.println("############ SIZE"+response.body().size());

                    sensors.setValue(response.body());

                }else {
                    System.out.println("Failure ###");
                    System.out.println("########"+response.message());
                }
            }


            @Override
            public void onFailure(@NonNull Call<ArrayList<Sensor>> call, Throwable t) {
                Log.i("Retrofit", "#######Something went wrong :(");
            }
        });
    }
}
