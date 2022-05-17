package com.example.apharma.ui.sensors;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apharma.R;
import com.example.apharma.adapters.SensorAdapter;
import com.example.apharma.models.MeasurementData;
import com.example.apharma.models.Room;
import com.example.apharma.models.Sensor;
import com.example.apharma.ui.home.HomeFragmentDirections;
import com.example.apharma.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class Sensors extends Fragment implements SensorAdapter.OnListItemClickListener {

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


        String id = SensorsArgs.fromBundle(getArguments()).getRoomId();


        sensorsViewModel.getSensors().observe(getViewLifecycleOwner(), sensors -> {

            sensorAdapter.update(sensors);

        });

        sensorsViewModel.fetchSensors(id);

        ConfigureRecyclerView();

        return view;
    }

    private void ConfigureRecyclerView() {

        sensorAdapter = new SensorAdapter(sensorsList, this);

        recyclerView.setAdapter(sensorAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);


    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }
}