package com.example.apharma.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.apharma.repositories.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class HomeViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public HomeViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return userRepository.getCurrentUser();
    }

    public void signOut() {
        userRepository.signOut();
    }
}