package com.example.apharma.ui.readings;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.apharma.models.Reading;
import com.example.apharma.models.Sensor;
import com.example.apharma.repositories.ReadingRepository;
import com.example.apharma.repositories.SensorRepository;
import java.util.List;

public class ReadingViewModel extends AndroidViewModel {

    ReadingRepository readingRepository;
    SensorRepository sensorRepository;

    public ReadingViewModel(Application application) {
        super(application);
        readingRepository = ReadingRepository.getInstance(application);
        sensorRepository = SensorRepository.getInstance(application);
    }

    public LiveData<List<Sensor>> getSensors(String roomId) {
        return sensorRepository.getAllInRoom(roomId);
    }

    public LiveData<List<Reading>> getReadings(String roomId, String sensorType) {
        return readingRepository.getAllFromSensor(roomId, sensorType);
    }
}