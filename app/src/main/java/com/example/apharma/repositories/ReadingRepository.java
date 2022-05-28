package com.example.apharma.repositories;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import com.example.apharma.database.LocalDatabase;
import com.example.apharma.database.ReadingDAO;
import com.example.apharma.models.Reading;
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

public class ReadingRepository {

    private static ReadingRepository instance;
    private final ReadingDAO readingDAO;
    private final Executor executor;
    private final HashMap<String, Long> lastRequestTimes;

    private ReadingRepository(Application application) {
        LocalDatabase localDatabase = LocalDatabase.getInstance(application);
        readingDAO = localDatabase.readingDAO();
        executor = Executors.newFixedThreadPool(2);
        lastRequestTimes = new HashMap<>();
    }

    public static ReadingRepository getInstance(Application application) {
        if (instance == null) instance = new ReadingRepository(application);
        return instance;
    }

    public LiveData<List<Reading>> getAllFromSensor(String roomId, String sensorType) {
        executor.execute(() -> refresh(roomId, sensorType));
        final MediatorLiveData<List<Reading>> mediator = new MediatorLiveData<>();
        mediator.addSource(readingDAO.getAllFromRoom(roomId, sensorType), mediator::setValue);
        return mediator;
    }

    @WorkerThread
    private void refresh(String roomId, String sensorType) {
        long crtTime = new Date().getTime();
        // Limit to making one request per every 10 seconds
        if (lastRequestTimes.containsKey(roomId + sensorType) && crtTime < lastRequestTimes.get(roomId + sensorType) + 10000) return;
        lastRequestTimes.put(roomId + sensorType, crtTime);

        Log.d("REPO", "Fetching reading from remote for roomId: " + roomId + ", sensor type: " + sensorType);

        RoomApi roomApi = ServiceGenerator.getRoomApi();
        Call<List<Reading>> call = roomApi.getSensorData(roomId, sensorType);
        call.enqueue(new Callback<List<Reading>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Reading>> call, Response<List<Reading>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Reading> newData = response.body();
                    for (Reading reading : newData) {
                        reading.setRoomId(roomId);
                    }
                    executor.execute(() -> readingDAO.insertAll(newData));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Reading>>  call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
            }
        });
    }
}