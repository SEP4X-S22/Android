package com.example.apharma.ui.sensors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apharma.R;
import com.example.apharma.adapters.SensorAdapter;
import com.example.apharma.utils.NetworkCheck;

public class SensorsFragment extends Fragment {

    private RecyclerView recyclerView;
    private SensorAdapter sensorAdapter;
    NetworkCheck networkCheck;

    public SensorsFragment() {
        // Required empty public constructor
        networkCheck = new NetworkCheck();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SensorsViewModel sensorsViewModel =
                new ViewModelProvider(this).get(SensorsViewModel.class);


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sensors, container, false);
        recyclerView = view.findViewById(R.id.rv);


        String roomId = SensorsFragmentArgs.fromBundle(getArguments()).getRoomId();


        ConfigureRecyclerView();

        sensorsViewModel.getSensors().observe(getViewLifecycleOwner(), sensors -> {
            if (networkCheck.isConnected()) {
                sensorAdapter.update(sensors);
            }
        });

        sensorsViewModel.getListOfSensors(roomId).observe(getViewLifecycleOwner(), sensors -> {
            if (!networkCheck.isConnected()) {
                sensorAdapter.update(sensors);
            }


        });

        sensorsViewModel.fetchSensors(roomId);


        sensorAdapter.setOnClickListener(v -> {
            SensorsFragmentDirections.ActionSensorsToReadingFragment actionSensorsToReadingFragment = SensorsFragmentDirections.actionSensorsToReadingFragment();
            actionSensorsToReadingFragment.setRoomId(roomId);
            actionSensorsToReadingFragment.setSensorType(v.getSensor().toString());
            actionSensorsToReadingFragment.setSensorId(v.getId());
            Navigation.findNavController(view).navigate(actionSensorsToReadingFragment);


        });

        return view;
    }


    private void ConfigureRecyclerView() {

        sensorAdapter = new SensorAdapter(getContext());
        sensorAdapter.setContext(getContext());

        recyclerView.setAdapter(sensorAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);


    }


}