package com.example.apharma.ui.readings;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.apharma.R;
import com.example.apharma.models.Reading;
import com.example.apharma.utils.NetworkCheck;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReadingFragment extends Fragment {

    List<Reading> readings;
    TextView textView;
    NetworkCheck networkCheck;
    private GraphView graphView;
    private DatePicker datePicker;


    public ReadingFragment() {
        // Required empty public constructor
        this.readings = new ArrayList<>();
        networkCheck = new NetworkCheck();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ReadingViewModel measurementDataViewModel = new ViewModelProvider(this).get(ReadingViewModel.class);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reading, container, false);


        String id = ReadingFragmentArgs.fromBundle(getArguments()).getRoomId();
        String sensorType = ReadingFragmentArgs.fromBundle(getArguments()).getSensorType();
        int sensorId = ReadingFragmentArgs.fromBundle(getArguments()).getSensorId();

        measurementDataViewModel.fetchReadings(id, sensorType);

        measurementDataViewModel.getReadings().observe(getViewLifecycleOwner(), values -> {
            if (networkCheck.isConnected()) {

                readings = values;
                System.out.println("@@@@@@@@" + values);
                textView = view.findViewById(R.id.value);
                if (readings.size() != 0) {
                    textView.setText("Reading: " + readings.get(readings.size() - 1).getReadingValue());
                } else {
                    textView.setText("No readings available");
                }
                graphView = view.findViewById(R.id.idGraphView);
                graphView.removeAllSeries();

                if (readings.size() > 0) {

                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                    for (int i = 0; i < readings.size(); i++) {
                        DataPoint point = new DataPoint(i, readings.get(i).getReadingValue());
                        series.appendData(point, false, readings.size());
                    }

                    graphView.addSeries(series);
                }
                graphView.setTitle("Readings overview");

                graphView.setTitleTextSize(50);
            }


        });

        measurementDataViewModel.getListOfSensors(id, sensorType).observe(getViewLifecycleOwner(), values -> {
            if (!networkCheck.isConnected()) {
                readings = values;
                System.out.println("@@@@@@@@" + values);
                textView = view.findViewById(R.id.value);
                if (readings.size() != 0) {
                    textView.setText("Reading: " + readings.get(readings.size() - 1).getReadingValue());
                } else {
                    textView.setText("No readings available");
                }

                graphView = view.findViewById(R.id.idGraphView);
                graphView.removeAllSeries();

                if (readings.size() > 0) {

                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                    for (int i = 0; i < readings.size(); i++) {
                        DataPoint point = new DataPoint(i, readings.get(i).getReadingValue());
                        series.appendData(point, false, readings.size());
                    }

                    graphView.addSeries(series);
                }
                graphView.setTitle("Readings overview");

                graphView.setTitleTextSize(50);

            }

        });

        if (Build.VERSION.SDK_INT >= 26) {
            datePicker = view.findViewById(R.id.idDatePicker);
            DatePicker.OnDateChangedListener dateListener = (v, year, monthOfYear, dayOfMonth) ->
            {
                LocalDateTime ld = LocalDateTime.of(v.getYear(), v.getMonth() + 1, v.getDayOfMonth(), 12, 0);
                String d = ld.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                measurementDataViewModel.fetchReadingsPerDay(Integer.parseInt(d), sensorId);
            };

            LocalDateTime today = LocalDateTime.now();
            datePicker.init(today.getYear(), (today.getMonthValue() - 1), today.getDayOfMonth(), dateListener);
            datePicker.setMaxDate(Calendar.getInstance().getTimeInMillis() + 3600);
        }

        return view;
    }


}