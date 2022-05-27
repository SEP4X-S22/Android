package com.example.apharma.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apharma.models.Room;
import com.example.apharma.models.Sensor;
import com.example.apharma.repositories.RoomRepository;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText;

    RoomRepository repository;

    public HomeViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
        repository = RoomRepository.getInstance(application);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Room>> getRooms() {
        return repository.getRooms();
    }

    public LiveData<List<Room>> getListOfRooms() {
        return repository.getListOfRooms();
    }



    public void fetchRooms() {
        repository.fetchRooms();
    }

}