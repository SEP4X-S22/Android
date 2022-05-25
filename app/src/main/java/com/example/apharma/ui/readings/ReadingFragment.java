package com.example.apharma.ui.readings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.example.apharma.R;
import com.example.apharma.models.Reading;

import java.util.ArrayList;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;

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
    ArrayList<Reading> readings;
    TextView textView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReadingFragment() {
        // Required empty public constructor
        this.readings = new ArrayList<>();
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
        View view = inflater.inflate(R.layout.fragment_reading, container, false);

        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view);

        String id = ReadingFragmentArgs.fromBundle(getArguments()).getRoomId();
        String sensorType = ReadingFragmentArgs.fromBundle(getArguments()).getSensorType();

        Cartesian line = AnyChart.line();

        List<DataEntry> data = new ArrayList<>();

        measurementDataViewModel.getReadings().observe(getViewLifecycleOwner(), values -> {
         /*   readings = values;
            System.out.println("@@@@@@@@"+values);
            textView = view.findViewById(R.id.value);*/


            readings = values;
            for (Reading item : readings) {
                data.add(new CustomDataEntry(item.getTimeStamp(),item.getReadingValue()));
            }
           // textView.setText("Reading: " +readings.get(readings.size()-1).getReadingValue());
            Set set = Set.instantiate();
            set.data(data);

            Mapping seriesMapping = set.mapAs("{ x: 'x', value: 'value' }");

            Line series = line.line(seriesMapping);

            anyChartView.setChart(line);
        });

//        measurementDataViewModel.fetchReadings(id,sensorType);
//
        measurementDataViewModel.fetchReadings(id, sensorType);

        return view;

    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value) {
            super(x, value);

        }
    }
}