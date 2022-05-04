package com.example.apharma.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apharma.models.Room;
import com.example.apharma.models.Sensor;
import com.example.apharma.repositories.RoomRepository;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    RoomRepository repository;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
        repository = RoomRepository.getInstance();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData <ArrayList<Room> > getRooms() {
        return repository.getRooms();
    }

    public void fetchRooms( ) {
        repository.fetchRooms();
    }
}