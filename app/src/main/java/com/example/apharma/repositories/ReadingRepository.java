package com.example.apharma.repositories;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.apharma.database.LocalDatabase;
import com.example.apharma.database.ReadingDAO;
import com.example.apharma.database.SensorDAO;
import com.example.apharma.models.Reading;
import com.example.apharma.models.Sensor;
import com.example.apharma.network.PharmaApi;
import com.example.apharma.network.ServiceGenerator;
import com.example.apharma.utils.NetworkCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.internal.annotations.EverythingIsNonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadingRepository {
    private static ReadingRepository instance;
    private MutableLiveData<List<Reading>> values;
    LocalDatabase localDatabase;
    SensorDAO sensorDAO;
    ReadingDAO readingDAO;
    LiveData<List<Sensor>> sensorList;
    MutableLiveData<List<Reading>> readingList;
    ExecutorService executorService;
    Handler mainThreadHandler;
    private LiveData<List<Reading>> listOfReadings;
    NetworkCheck networkCheck;


    public static ReadingRepository getInstance(Application application) {
        if (instance == null) {
            instance = new ReadingRepository(application);
        }
        return instance;
    }

    public ReadingRepository(Application application) {
        values = new MutableLiveData<>();
        localDatabase = LocalDatabase.getInstance(application);
        sensorDAO = localDatabase.sensorDAO();
        readingDAO = localDatabase.readingDAO();
        executorService = Executors.newFixedThreadPool(2);
        mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        sensorList = sensorDAO.getAllSensors();
        readingList = new MutableLiveData<>();
        listOfReadings = readingDAO.getAllReadings();
        networkCheck = new NetworkCheck();


    }

    public LiveData<List<Sensor>> getSensors() {
        return sensorList;
    }

    public LiveData<List<Reading>> getReadingsFromDB() {
        return readingList;
    }

    public LiveData<List<Reading>> getReadings() {
        return values;
    }

    public LiveData<List<Reading>> getSensorsFromRoom(String id, String sensorType) {
        return listOfReadings = readingDAO.getAllReadings(id, sensorType);
    }

    public void insert(Reading reading) {
        executorService.execute(() -> readingDAO.insert(reading));
    }

    public LiveData<List<Reading>> getListOfReadings() {


        if (listOfReadings == null) {
            listOfReadings = readingDAO.getAllReadings();
        }

        return listOfReadings;

    }

    public void fetchReadings(String room, String sensorType) {
        PharmaApi pharmaApi = ServiceGenerator.getPharmaApi();
        Call<ArrayList<Reading>> call = pharmaApi.getSensorData(room, sensorType);

        if (networkCheck.isConnected()) {
            call.enqueue(new Callback<ArrayList<Reading>>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<ArrayList<Reading>> call, Response<ArrayList<Reading>> response) {
                    if (response.isSuccessful()) {
                        System.out.println("############" + response.body().size());
// checking to fetch from database
                        values.setValue(response.body());

                        for (Reading reading : response.body()) {
                            reading.setRoomId(room);
                            reading.setSensorType(sensorType);
                            insert(reading);
                        }

                    } else {
                        System.out.println("Failure ###");
                        System.out.println("########" + response.message());
                    }
                }


                @Override
                public void onFailure(@NonNull Call<ArrayList<Reading>> call, Throwable t) {
                    Log.i("Retrofit", "#######Something went wrong :(");
                }
            });
        } else {
            values.setValue(getListOfReadings().getValue());
        }
    }

    public void fetchReadingsPerDay(int date, int sensorId) {
        PharmaApi pharmaApi = ServiceGenerator.getPharmaApi();
        Call<ArrayList<Reading>> call = pharmaApi.getReadingsPerDay(date, sensorId);
        call.enqueue(new Callback<ArrayList<Reading>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ArrayList<Reading>> call, Response<ArrayList<Reading>> response) {
                if (response.isSuccessful()) {
                    System.out.println("############" + response.body().size());

                    values.setValue(response.body());

                } else {
                    System.out.println("Failure ###");
                    System.out.println("########" + response.message());
                }
            }


            @Override
            public void onFailure(@NonNull Call<ArrayList<Reading>> call, Throwable t) {
                Log.i("Retrofit", "#######Something went wrong :(");
            }
        });

    }


}