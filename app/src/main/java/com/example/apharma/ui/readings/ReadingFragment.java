package com.example.apharma.ui.readings;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apharma.R;
import com.example.apharma.models.Reading;
import com.example.apharma.models.Sensor;
import com.example.apharma.utils.NetworkCheck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReadingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ReadingViewModel measurementDataViewModel;
    List<Reading> readings;
    List<Sensor> sensors;
    List<Reading> readingsFromDB;
    TextView textView;
    NetworkCheck networkCheck;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReadingFragment() {
        // Required empty public constructor
        this.readings = new ArrayList<>();
        networkCheck = new NetworkCheck();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReadingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReadingFragment newInstance(String param1, String param2) {
        ReadingFragment fragment = new ReadingFragment();
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
        ReadingViewModel measurementDataViewModel = new ViewModelProvider(this).get(ReadingViewModel.class);
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_reading, container, false);


        String id = ReadingFragmentArgs.fromBundle(getArguments()).getRoomId();
        String sensorType = ReadingFragmentArgs.fromBundle(getArguments()).getSensorType();

        measurementDataViewModel.fetchReadings(id,sensorType);

        measurementDataViewModel.getReadings().observe(getViewLifecycleOwner(),values -> {
            if (networkCheck.isConnected()) {
                readings = values;
                System.out.println("@@@@@@@@" + values);
                textView = view.findViewById(R.id.value);
                textView.setText("Reading: " + readings.get(readings.size() - 1).getReadingValue());
            }

        });

        measurementDataViewModel.getListOfSensors(id, sensorType).observe(getViewLifecycleOwner(),values -> {
            if (!networkCheck.isConnected()) {
                readings = values;
                System.out.println("@@@@@@@@" + values);
                textView = view.findViewById(R.id.value);
                textView.setText("Reading: " + readings.get(readings.size() - 1).getReadingValue());
            }

        });

//        measurementDataViewModel.getSensors().observe(getViewLifecycleOwner(),values->{
//            sensors = values;
//        });

        // This gives an error
//        measurementDataViewModel.getReadingsFromDB().observe(getViewLifecycleOwner(), values ->{
//            readingsFromDB = values;
//        });

//        measurementDataViewModel.fetchReadings(id,sensorType);
//

        return view;
    }



}