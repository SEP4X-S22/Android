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

public class SensorsFragment extends Fragment  {

    private RecyclerView recyclerView;
    private SensorAdapter sensorAdapter;
    private SensorsViewModel sensorsViewModel;
    ArrayList<Sensor> sensorsList;

    public SensorsFragment() {
        // Required empty public constructor
    }


//    // TODO: Rename and change types and number of parameters
//    public static SensorsFragment newInstance(String param1, String param2) {
//        SensorsFragment fragment = new SensorsFragment();
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


        String roomId = SensorsFragmentArgs.fromBundle(getArguments()).getRoomId();


        ConfigureRecyclerView();

        sensorsViewModel.getSensors().observe(getViewLifecycleOwner(), sensors -> {

            sensorAdapter.update(sensors);

        });

        sensorsViewModel.fetchSensors(roomId);


        sensorAdapter.setOnClickListener(v ->{
            SensorsFragmentDirections.ActionSensorsToReadingFragment actionSensorsToReadingFragment = SensorsFragmentDirections.actionSensorsToReadingFragment();
            actionSensorsToReadingFragment.setRoomId(roomId);
            actionSensorsToReadingFragment.setSensorType(v.getSensor().toString());
            actionSensorsToReadingFragment.setSensorId(v.getId());
            Navigation.findNavController(view).navigate(actionSensorsToReadingFragment);


        });

        return view;
    }

    private void ConfigureRecyclerView() {

        sensorAdapter = SensorAdapter.getInstance();
        sensorAdapter.setContext(getContext());

        recyclerView.setAdapter(sensorAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);




    }


}