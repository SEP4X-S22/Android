package com.example.apharma.repositories;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import com.example.apharma.database.LocalDatabase;
import com.example.apharma.database.SensorDAO;
import com.example.apharma.models.Sensor;
import com.example.apharma.network.RoomApi;
import com.example.apharma.network.ServiceGenerator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import okhttp3.internal.annotations.EverythingIsNonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SensorRepository {

    private static SensorRepository instance;
    private final SensorDAO sensorDAO;
    private final Executor executor;
    private final HashMap<String, Long> lastRequestTimes;

    private SensorRepository(Application application) {
        LocalDatabase localDatabase = LocalDatabase.getInstance(application);
        sensorDAO = localDatabase.sensorDAO();
        executor = Executors.newFixedThreadPool(2);
        lastRequestTimes = new HashMap<>();
    }

    public static SensorRepository getInstance(Application application) {
        if (instance == null) instance = new SensorRepository(application);
        return instance;
    }

    public LiveData<List<Sensor>> getAllInRoom(String roomId) {
        executor.execute(() -> refresh(roomId));
        final MediatorLiveData<List<Sensor>> mediator = new MediatorLiveData<>();
        mediator.addSource(sensorDAO.getAllInRoom(roomId), mediator::setValue);
        return mediator;
    }

    @WorkerThread
    private void refresh(String roomId) {
        long crtTime = new Date().getTime();
        // Limit to making one request per every 10 seconds
        if (lastRequestTimes.containsKey(roomId) && crtTime < lastRequestTimes.get(roomId) + 10000) return;
        lastRequestTimes.put(roomId, crtTime);

        Log.d("REPO", "Fetching sensors from remote for roomId: " + roomId);

        RoomApi roomApi = ServiceGenerator.getRoomApi();
        Call<List<Sensor>> call = roomApi.getSensors(roomId);
        call.enqueue(new Callback<List<Sensor>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Sensor>> call, Response<List<Sensor>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Sensor> newData = response.body();
                    for (Sensor sensor : newData) {
                        sensor.setRoomId(roomId);
                    }
                    executor.execute(() -> sensorDAO.insertAll(newData));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Sensor>> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
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
