package com.example.apharma.ui.sensors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.apharma.models.Sensor;
import com.example.apharma.repositories.SensorRepository;

import java.util.ArrayList;

public class SensorsViewModel extends ViewModel {
    SensorRepository sensorRepository;

    public SensorsViewModel()
    {
        sensorRepository = SensorRepository.getInstance();
    }

    public LiveData<ArrayList<Sensor>> getSensors()
    {
        return sensorRepository.getSensors();
    }

    public void fetchSensors(int room){
        sensorRepository.fetchSensors(room);
    }
}
