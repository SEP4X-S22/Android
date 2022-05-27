package com.example.apharma.ui.readings;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.apharma.R;
import com.example.apharma.models.Reading;
import com.example.apharma.models.Sensor;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
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
    private GraphView graphView;
    private DatePicker datePicker;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ReadingViewModel measurementDataViewModel = new ViewModelProvider(this).get(ReadingViewModel.class);
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_reading, container, false);


        String id = ReadingFragmentArgs.fromBundle(getArguments()).getRoomId();
        String sensorType = ReadingFragmentArgs.fromBundle(getArguments()).getSensorType();
        int sensorId = ReadingFragmentArgs.fromBundle(getArguments()).getSensorId();

        measurementDataViewModel.fetchReadings(id,sensorType);

        measurementDataViewModel.getReadings().observe(getViewLifecycleOwner(),historical->{
            readings= historical;
            System.out.println("HISTORY"+historical);

            graphView = view.findViewById(R.id.idGraphView);

            if (readings.size() > 0) {
                graphView.removeAllSeries();
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                for (int i = 0; i < readings.size(); i++)
                {
                    DataPoint point = new DataPoint( i,readings.get(i).getReadingValue());
                    series.appendData(point, false, readings.size());

                }

                graphView.addSeries(series);
            }
            graphView.setTitle("Readings overview");

            graphView.setTitleTextSize(50);





        });
        datePicker = view.findViewById(R.id.idDatePicker);
        DatePicker.OnDateChangedListener dateListener = (v, year, monthOfYear, dayOfMonth) ->
        {
            LocalDateTime ld = LocalDateTime.of(v.getYear(), v.getMonth()+1, v.getDayOfMonth(), 12, 0);
            String d = ld.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            //System.out.println(ld);
            //System.out.println(d);
            //System.out.println(sensorId);
            measurementDataViewModel.fetchReadingsPerDay(Integer.parseInt(d),sensorId);
        };

        LocalDateTime today = LocalDateTime.now();
        datePicker.init(today.getYear(), (today.getMonthValue()-1), today.getDayOfMonth(), dateListener);
        datePicker.setMaxDate(Calendar.getInstance().getTimeInMillis()+3600);

//        measurementDataViewModel.getReadings().observe(getViewLifecycleOwner(),values -> {
//            if (internetIsConnected()) {
//                readings = values;
//                System.out.println("@@@@@@@@" + values);
//                textView = view.findViewById(R.id.value);
//                textView.setText("Reading: " + readings.get(readings.size() - 1).getReadingValue());
//
//
//                System.out.println("HISTORY"+values);
//
//                graphView = view.findViewById(R.id.idGraphView);
//
//                if (readings.size() > 0) {
//                    graphView.removeAllSeries();
//                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
//                    for (int i = 0; i < readings.size(); i++)
//                    {
//                        DataPoint point = new DataPoint( i,readings.get(i).getReadingValue());
//                        series.appendData(point, false, readings.size());
//
//                    }
//
//                    graphView.addSeries(series);
//                }
//                graphView.setTitle("Readings overview");
//
//                graphView.setTitleTextSize(50);
//
//
//            }
//
//        });

        measurementDataViewModel.getListOfSensors(id, sensorType).observe(getViewLifecycleOwner(),values -> {
            if (!internetIsConnected()) {
                readings = values;
                System.out.println("@@@@@@@@" + values);
                textView = view.findViewById(R.id.value);
                textView.setText("Reading: " + readings.get(readings.size() - 1).getReadingValue());


                System.out.println("HISTORY"+values);

                graphView = view.findViewById(R.id.idGraphView);

                if (readings.size() > 0) {
                    graphView.removeAllSeries();
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                    for (int i = 0; i < readings.size(); i++)
                    {
                        DataPoint point = new DataPoint( i,readings.get(i).getReadingValue());
                        series.appendData(point, false, readings.size());

                    }

                    graphView.addSeries(series);
                }
                graphView.setTitle("Readings overview");

                graphView.setTitleTextSize(50);

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

    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }
}