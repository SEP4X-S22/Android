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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Sensors#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sensors extends Fragment implements SensorAdapter.OnListItemClickListener {

    private RecyclerView recyclerView;
    private SensorAdapter sensorAdapter;
//    ArrayList<MeasurementData> sensors;
//    ArrayList<Room> rooms;
//    ArrayList<MeasurementData> sensorList;
    private SensorsViewModel sensorsViewModel;
    private HomeViewModel homeViewModel;
    ArrayList<Room> rooms;

    ArrayList<Sensor> sensors;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Sensors() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Sensors.
     */
    // TODO: Rename and change types and number of parameters
    public static Sensors newInstance(String param1, String param2) {
        Sensors fragment = new Sensors();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SensorsViewModel sensorsViewModel =
                new ViewModelProvider(this).get(SensorsViewModel.class);
         homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sensors, container, false);
        recyclerView = view.findViewById(R.id.rv);

//        rooms = new ArrayList<>();
//        rooms.add(new Room("Storage room", 1));
//        rooms.add(new Room("Main room", 2));
//        rooms.add(new Room("room", 3));
//
//
//        sensorList =  new ArrayList<>();
//        MeasurementData temp = new MeasurementData(1, 40, 10, 60, "4/29/22", "Temp");
//        sensorList.add(temp);
//        rooms.get(0).setSensors(sensorList);

        sensors = new ArrayList<>();
        rooms = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rv);
//        sensorsViewModel.getSensors().observe(getViewLifecycleOwner(),sensor -> {
//            sensors.addAll(sensor);
//        });
//        sensorsViewModel.fetchSensors(1);


        homeViewModel.getRooms().observe(getViewLifecycleOwner(),room -> {


            int id = SensorsArgs.fromBundle(getArguments()).getRoomId();
            rooms.addAll(sensorsViewModel.getRoomsById(room,id));




            for (int i =0; i < rooms.size(); i++) {

                    sensors.addAll(rooms.get(0).getSensors());

                }
//
//            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +rooms.get(0).getSensors().size());


        });
        homeViewModel.fetchRooms();

        ConfigureRecyclerView();

        return view;
    }

    private void ConfigureRecyclerView() {

    sensorAdapter = new SensorAdapter(sensors, this);

        recyclerView.setAdapter(sensorAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);


    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }
}