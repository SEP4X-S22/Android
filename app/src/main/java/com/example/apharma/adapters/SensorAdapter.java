package com.example.apharma.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apharma.R;
import com.example.apharma.models.Sensor;
import com.example.apharma.ui.sensors.SensorsViewModel;

import java.util.ArrayList;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.ViewHolder> {

    private ArrayList<Sensor> list;
    private Context context;
    private OnListItemClickListener mOnListItemClickListener;
    private SensorsViewModel sensorsViewModel;


    public SensorAdapter(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
        sensorsViewModel = SensorsViewModel.getInstance();
    }

    public interface OnListItemClickListener {
        void onClick(Sensor sensor);
    }

    public void setOnClickListener(OnListItemClickListener listener) {
        mOnListItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.sensor_item, parent, false);
        return new SensorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.setCancelable(true);

        Button submit = dialog.findViewById(R.id.setConstraints);
        EditText sensorIdText = dialog.findViewById(R.id.sensorsId);
        EditText sensorMinVal = dialog.findViewById(R.id.minVal);
        EditText sensorMaxVal = dialog.findViewById(R.id.maxVal);


        sensorIdText.setText(list.get(position).getId() + "");
        sensorMinVal.setText(list.get(position).getConstraintMinValue() + "");
        sensorMaxVal.setText(list.get(position).getConstraintMaxValue() + "");


        holder.name.setText(list.get(position).getSensor().toString());
//        holder.measurement.setText(list.get(position).getId() + "°C");
//        holder.measurement.setText("Current value "+list.get(position).getReadings().get(0).getReadingValue() );
        holder.measurement.setText("" + list.get(position).getReadingValue());
        if (list.get(position).getSensor().toString().equalsIgnoreCase("Temperature")) {
            holder.image.setBackground(context.getDrawable(R.drawable.ic_baseline_wb_sunny_24));
        }
        if (list.get(position).getSensor().toString().equalsIgnoreCase("CO2")) {
            holder.image.setBackground(context.getDrawable(R.drawable.ic_baseline_co2_24));
        }
        if (list.get(position).getSensor().toString().equalsIgnoreCase("Light")) {
            holder.image.setBackground(context.getDrawable(R.drawable.ic_baseline_lightbulb_24));
        }
        if (list.get(position).getSensor().toString().equalsIgnoreCase("Humidity")) {
            holder.image.setBackground(context.getDrawable(R.drawable.ic_baseline_waves_24));
        }

        if (list.get(position).getReadingValue() > list.get(position).getConstraintMaxValue()) {
            holder.measurement.setBackground(context.getDrawable(R.color.red));
            holder.measurement.setText(list.get(position).getReadingValue() + " DANGER!");
            Toast.makeText(context, list.get(position).getSensor() + " measurement should be less than " + list.get(position).getConstraintMaxValue(), Toast.LENGTH_SHORT).show();
        }

        holder.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

                submit.setOnClickListener(v1 -> {
                    double min = Double.parseDouble(sensorMinVal.getText().toString());
                    double max = Double.parseDouble(sensorMaxVal.getText().toString());

                        list.get(position).setConstraintMinValue(min);
                        list.get(position).setConstraintMaxValue(max);
                        sensorsViewModel.updateConstraints(list.get(position).getId(), list.get(position).getConstraintMinValue(), list.get(position).getConstraintMaxValue());
                        notifyDataSetChanged();
                    dialog.dismiss();
                });
            }
        });
    }

    public void update(ArrayList<Sensor> sensors) {
        list = sensors;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Sensor getSelectedSensor(int position) {
        if (list != null) {
            if (list.size() > 0) {
                return list.get(position);
            }
        }
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView image;
        TextView measurement;
        ImageView settingsButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.sensor_title);
            measurement = itemView.findViewById(R.id.sensor_measurement);
            image = itemView.findViewById(R.id.sensor_image);
            settingsButton = itemView.findViewById(R.id.settings);
            itemView.setOnClickListener(view -> mOnListItemClickListener.onClick(list.get(getAdapterPosition())));
        }


    }
}
