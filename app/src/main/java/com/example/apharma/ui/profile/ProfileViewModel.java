package com.example.apharma.ui.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.apharma.repositories.UserLiveData;
import com.example.apharma.repositories.UserRepository;

public class ProfileViewModel extends AndroidViewModel {
    private UserLiveData userLiveData;
    private UserRepository userRepository;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        userLiveData = new UserLiveData();
        userRepository = UserRepository.getInstance(application);
    }

    public UserLiveData getUserLiveData() {
        return userLiveData;
    }

    public void signOut() {
        userRepository.signOut();
    }
}