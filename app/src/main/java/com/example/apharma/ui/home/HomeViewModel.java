package com.example.apharma.ui.home;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;

import androidx.annotation.NonNull;
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

    RoomRepository repository;

    private final MutableLiveData<String> mText;

    public HomeViewModel(Application application) {
        super(application);
        repository = RoomRepository.getInstance(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<List<Room>> getRoomList() {
        return repository.getAll();
    }

    public LiveData<String> getText() {
        return mText;
    }

//    public void fetchRooms() {
//        repository.fetchRooms();
//    }

}