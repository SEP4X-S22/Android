package com.example.apharma.ui.sensors;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apharma.R;
import com.example.apharma.adapters.RoomAdapter;
import com.example.apharma.adapters.SensorAdapter;
import com.example.apharma.models.MeasurementData;
import com.example.apharma.models.Room;

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
    ArrayList<MeasurementData> sensors;
    private ArrayList<Room> rooms;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sensors, container, false);
        sensors = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rv);
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