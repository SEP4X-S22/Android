package com.example.apharma.ui.readings;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.apharma.models.Reading;
import com.example.apharma.models.Sensor;
import com.example.apharma.repositories.ReadingRepository;

import java.util.ArrayList;
import java.util.List;

public class ReadingViewModel extends AndroidViewModel {
    ReadingRepository repository;

    public ReadingViewModel(Application application) {
        super(application);
        repository = ReadingRepository.getInstance(application);
    }

    public LiveData<List<Sensor>> getSensors() {
        return repository.getSensors();
    }

    public LiveData<ArrayList<Reading>> getReadings() {
        return repository.getReadings();
    }

    public LiveData<ArrayList<Reading>> getReadingsPerDay() {
        return repository.getReadingsPerDay();
    }

    public void fetchReadings(String room, String sensorType) {
        repository.fetchReadings(room, sensorType);
    }

    public void fetchReadingsPerDay(int date, int sensorId) {
        repository.fetchReadingsPerDay(date, sensorId);
    }


}