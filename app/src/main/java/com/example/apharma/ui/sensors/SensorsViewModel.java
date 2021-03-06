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

public class SensorsViewModel extends ViewModel {
    SensorRepository sensorRepository;
    private static SensorsViewModel instance;

    public SensorsViewModel() {

        sensorRepository = SensorRepository.getInstance();
    }

    public static synchronized SensorsViewModel getInstance() {
        if (instance == null) {
            instance = new SensorsViewModel();
        }
        return instance;
    }

    public LiveData<List<Sensor>> getSensors() {
        return sensorRepository.getSensors();
    }

    public LiveData<List<Sensor>> getListOfSensors(String roomId) {
        return sensorRepository.getSensorsFromRoom(roomId);
    }

    public void fetchSensors(String room) {
        sensorRepository.fetchSensors(room);
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
