package com.example.apharma.ui.readings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.apharma.models.Reading;
import com.example.apharma.repositories.ReadingRepository;

import java.util.ArrayList;

public class ReadingViewModel extends ViewModel {
    ReadingRepository repository;

    public ReadingViewModel() {
        repository = ReadingRepository.getInstance();
    }

    public LiveData<ArrayList<Reading>> getReadings() {
        return repository.getReadings();
    }

    public void fetchReadings(String room, String sensorType) {
        repository.fetchReadings(room, sensorType);
    }


}