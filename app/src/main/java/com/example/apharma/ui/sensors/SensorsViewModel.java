package com.example.apharma.ui.sensors;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.apharma.models.Room;
import com.example.apharma.models.Sensor;
import com.example.apharma.repositories.SensorRepository;

import java.util.ArrayList;
import java.util.List;

public class SensorsViewModel extends AndroidViewModel {

    private static SensorsViewModel instance;
    private final SensorRepository sensorRepository;

    public SensorsViewModel(Application application) {
        super(application);
        sensorRepository = SensorRepository.getInstance(application);
    }

    public static synchronized SensorsViewModel getInstance(Application application) {
        if (instance == null) instance = new SensorsViewModel(application);
        return instance;
    }

    public LiveData<List<Sensor>> getAllSensorsInRoom(String roomId) {
        return sensorRepository.getAllInRoom(roomId);
    }

    public ArrayList<Room> getRoomsById(ArrayList<Room> rooms, String id) {
        ArrayList<Room> roomsToReturn = new ArrayList<>();

        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getId().equals(id)) {
                roomsToReturn.add(rooms.get(i));
            }
        }
        return roomsToReturn;

    }

    public void updateConstraints(int id, int min, int max) {
        sensorRepository.updateConstraints(id, min, max);
    }
}
