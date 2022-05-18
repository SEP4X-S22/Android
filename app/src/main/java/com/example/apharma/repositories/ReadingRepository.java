package com.example.apharma.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.apharma.models.Reading;
import com.example.apharma.network.RoomApi;
import com.example.apharma.network.ServiceGenerator;

import java.util.ArrayList;

import okhttp3.internal.annotations.EverythingIsNonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadingRepository {
    private static ReadingRepository instance;
    private MutableLiveData<ArrayList<Reading>> values;

    public static ReadingRepository getInstance() {
        if (instance == null) {
            instance = new ReadingRepository();
        }
        return instance;
    }

    public ReadingRepository() {
        values = new MutableLiveData<>();

    }
    public LiveData<ArrayList<Reading>> getReadings() {
        return values;
    }

    public void fetchReadings(String room, String sensorType) {
        RoomApi roomApi = ServiceGenerator.getRoomApi();
        Call<ArrayList<Reading>> call = roomApi.getSensorData(room,sensorType);
        call.enqueue(new Callback<ArrayList<Reading>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ArrayList<Reading>>  call, Response<ArrayList<Reading>> response) {
                if (response.isSuccessful()) {
                    System.out.println("############"+response.body().size());

                    values.setValue(response.body());
                }else {
                    System.out.println("Failure ###");
                    System.out.println("########"+response.message());
                }
            }


            @Override
            public void onFailure(@NonNull Call<ArrayList<Reading>>  call, Throwable t) {
                Log.i("Retrofit", "#######Something went wrong :(");
            }
        });
    }
}