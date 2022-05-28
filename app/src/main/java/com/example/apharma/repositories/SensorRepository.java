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
import com.example.apharma.database.SensorDAO;
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
    private MutableLiveData<List<Sensor>> sensors;
    private LiveData<List<Sensor>> listOfSensors;
    LocalDatabase localDatabase;
    SensorDAO sensorDAO;
    ExecutorService executorService;
    Handler mainThreadHandler;

    public static SensorRepository getInstance(Application application) {
        if (instance == null) {
            instance = new SensorRepository(application);
        }
        return instance;
    }

    public SensorRepository(Application application) {
        sensors = new MutableLiveData<>();
        localDatabase = LocalDatabase.getInstance(application);
        sensorDAO = localDatabase.sensorDAO();
        executorService = Executors.newFixedThreadPool(2);
        mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    }

    public LiveData<List<Sensor>> getSensors() {
        return sensors;
    }

    public LiveData<List<Sensor>> getListOfSensors() {


        if (listOfSensors == null) {
            listOfSensors = sensorDAO.getAllSensors();
        }

        return listOfSensors;

    }

    public LiveData<List<Sensor>> getSensorsFromRoom(String id) {
       return listOfSensors = sensorDAO.getAllSensorsFromRoom(id);
    }

    public void fetchSensors(String room) {
        RoomApi roomApi = ServiceGenerator.getRoomApi();
        Call<ArrayList<Sensor>> call = roomApi.getSensors(room);
        if (isConnected()) {
            call.enqueue(new Callback<ArrayList<Sensor>>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<ArrayList<Sensor>> call, Response<ArrayList<Sensor>> response) {
                    if (response.isSuccessful()) {
                        System.out.println("############ SIZE" + response.body().size());

                        sensors.setValue(response.body());

                        for (Sensor sensor : response.body()) {
                            sensor.setRoomId(room);
                            insert(sensor);

                        }


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
        } else {
            System.out.println("no internet");
            sensors.setValue(getListOfSensors().getValue());
        }
    }

    public void insert(Sensor sensor) {
        executorService.execute(() -> sensorDAO.insert(sensor));
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
