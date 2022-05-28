package com.example.apharma.repositories;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import com.example.apharma.database.LocalDatabase;
import com.example.apharma.database.RoomDAO;
import com.example.apharma.models.Room;
import com.example.apharma.network.RoomApi;
import com.example.apharma.network.ServiceGenerator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import okhttp3.internal.annotations.EverythingIsNonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomRepository {

    private static RoomRepository instance;
    private final RoomDAO roomDAO;
    private final Executor executor;
    private Long lastRequestTime;

    private RoomRepository(Application application) {
        LocalDatabase localDatabase = LocalDatabase.getInstance(application);
        roomDAO = localDatabase.roomDAO();
        executor = Executors.newFixedThreadPool(2);
        lastRequestTime = - Long.MAX_VALUE;
    }

    public static RoomRepository getInstance(Application application) {
        if (instance == null) instance = new RoomRepository(application);
        return instance;
    }

    public LiveData<List<Room>> getAll() {
        executor.execute(this::refresh);
        final MediatorLiveData<List<Room>> mediator = new MediatorLiveData<>();
        mediator.addSource(roomDAO.getAll(), mediator::setValue);
        return mediator;
    }

    @WorkerThread
    private void refresh() {
        long crtTime = new Date().getTime();
        // Limit to making one request per every 10 seconds
        if (crtTime < lastRequestTime + 10000) return;
        lastRequestTime = crtTime;

        Log.d("REPO", "Fetching rooms from remote");

        RoomApi roomApi = ServiceGenerator.getRoomApi();
        Call<List<Room>> call = roomApi.getRooms();
        call.enqueue(new Callback<List<Room>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    executor.execute(() -> roomDAO.insertAll(response.body()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Room>>  call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
            }
        });
    }
}
