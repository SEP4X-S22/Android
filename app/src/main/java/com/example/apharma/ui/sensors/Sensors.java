package com.example.apharma.ui.sensors;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apharma.R;
import com.example.apharma.adapters.SensorAdapter;
import com.example.apharma.models.Sensor;

import java.util.ArrayList;

public class Sensors extends Fragment  {

    private RecyclerView recyclerView;
    private SensorAdapter sensorAdapter;
    private SensorsViewModel sensorsViewModel;
    ArrayList<Sensor> sensorsList;

    public Sensors() {
        // Required empty public constructor
    }


//    // TODO: Rename and change types and number of parameters
//    public static Sensors newInstance(String param1, String param2) {
//        Sensors fragment = new Sensors();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

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


        String roomId = SensorsArgs.fromBundle(getArguments()).getRoomId();


        sensorsViewModel.getSensors().observe(getViewLifecycleOwner(), sensors -> {

            sensorAdapter.update(sensors);

        });

        sensorsViewModel.fetchSensors(roomId);

        ConfigureRecyclerView();

        sensorAdapter.setOnClickListener(v ->{
            SensorsDirections.ActionSensorsToReadingFragment actionSensorsToReadingFragment = SensorsDirections.actionSensorsToReadingFragment();
            actionSensorsToReadingFragment.setRoomId(roomId);
            actionSensorsToReadingFragment.setSensorType(v.getSensor().toString());
            Navigation.findNavController(view).navigate(actionSensorsToReadingFragment);


        });

        return view;
    }

    private void ConfigureRecyclerView() {

        sensorAdapter = new SensorAdapter( getContext());

        recyclerView.setAdapter(sensorAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);




    }


}