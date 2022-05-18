package com.example.apharma.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.apharma.R;
import com.example.apharma.ui.signIn.SignInActivity;
import com.google.firebase.auth.UserInfo;

public class ProfileFragment extends Fragment {
    private Button signOut;
    private TextView email;
    private TextView name;

    ProfileViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        signOut = view.findViewById(R.id.signOut);
        email = view.findViewById(R.id.email);
//        name = view.findViewById(R.id.usename);


        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.signOut();
                startActivity(new Intent(getActivity(), SignInActivity.class));
                getActivity().finish();
            }
        });


        mViewModel.getUserLiveData().observe(getViewLifecycleOwner(), firebaseUser -> {
            if (firebaseUser != null) {
                UserInfo profile = firebaseUser.getProviderData().get(0);
//                name.setText(firebaseUser.getDisplayName());
                email.setText(profile.getEmail());
            }
        });

        return view;
    }
}