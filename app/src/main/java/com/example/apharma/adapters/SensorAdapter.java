package com.example.apharma.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apharma.R;
import com.example.apharma.models.Sensor;
import com.example.apharma.ui.sensors.SensorsFragment;
import com.example.apharma.ui.sensors.SensorsViewModel;
import com.example.apharma.utils.NotificationService;

import java.util.ArrayList;
import java.util.List;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.ViewHolder> {

    private List<Sensor> list;
    private Context context;
    private OnListItemClickListener mOnListItemClickListener;
    private SensorsViewModel sensorsViewModel;
    private static SensorAdapter instance;
    private MutableLiveData<Boolean> conditionsSurpassConstraints = new MutableLiveData<>();
    private NotificationService notificationService;

    public LiveData<Boolean> isConditionsSurpassConstraints() {
        return conditionsSurpassConstraints;
    }

    public SensorAdapter(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
        this.notificationService = new NotificationService(context);
        sensorsViewModel = SensorsViewModel.getInstance();
    }

    public void setContext(Context context) {
        this.context = context;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.setCancelable(true);

        Button submit = dialog.findViewById(R.id.setConstraints);
        EditText sensorIdText = dialog.findViewById(R.id.sensorsId);
        EditText sensorMinVal = dialog.findViewById(R.id.minVal);
        EditText sensorMaxVal = dialog.findViewById(R.id.maxVal);


        sensorIdText.setText(String.valueOf(list.get(position).getId()));
        sensorMinVal.setText(String.valueOf(list.get(position).getConstraintMinValue()));
        sensorMaxVal.setText(String.valueOf(list.get(position).getConstraintMaxValue()));


        holder.name.setText(list.get(position).getSensor().toString());

        holder.measurement.setText(String.valueOf(list.get(position).getReadingValue()));
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

        if (checkForCurrentConditions(position)) {
           notificationService.addNotification();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.cardView.setCardBackgroundColor(context.getColor(R.color.red));
            }
            holder.measurement.setText(list.get(position).getReadingValue() + " DANGER!");


        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.cardView.setCardBackgroundColor(context.getColor(R.color.cadet_blue));
            }
        }


        holder.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

                submit.setOnClickListener(v1 -> {
                    int min = Integer.parseInt(sensorMinVal.getText().toString());
                    int max = Integer.parseInt(sensorMaxVal.getText().toString());

                    //Setting message manually and performing action on button click
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to set new constraints?").setPositiveButton("Yes", (dialogInterface, i) -> {
                        list.get(position).setConstraintMinValue(min);
                        list.get(position).setConstraintMaxValue(max);
                        sensorsViewModel.updateConstraints(list.get(position).getId(), list.get(position).getConstraintMinValue(), list.get(position).getConstraintMaxValue());
                        notifyItemChanged(position);

                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //  Action for 'Cancel' Button
                            dialogInterface.cancel();
                            notifyItemChanged(position);
                        }
                    });
                    //Creating dialog box
                    AlertDialog alertDialog = builder.create();

                    //Setting the title
                    alertDialog.setTitle("Confirmation");
                    alertDialog.show();
                    dialog.dismiss();
                });
            }
        });
    }

    public void update(List<Sensor> sensors) {
        if (sensors != null) {
            list = sensors;
            notifyDataSetChanged();
        }
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

    public boolean checkForCurrentConditions(int position) {

        if (list.get(position).getConstraintMinValue() > list.get(position).getReadingValue() || list.get(position).getConstraintMaxValue() < list.get(position).getReadingValue()) {
            conditionsSurpassConstraints.setValue(true);
            return true;
        }
        conditionsSurpassConstraints.setValue(false);
        return false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView image;
        TextView measurement;
        ImageView settingsButton;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.sensor_title);
            measurement = itemView.findViewById(R.id.sensor_measurement);
            image = itemView.findViewById(R.id.sensor_image);
            settingsButton = itemView.findViewById(R.id.settings);
            cardView = itemView.findViewById(R.id.sensorCardView);
            itemView.setOnClickListener(view -> mOnListItemClickListener.onClick(list.get(getAdapterPosition())));
        }


    }

}
