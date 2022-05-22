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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apharma.MainActivity;
import com.example.apharma.R;
import com.example.apharma.models.Sensor;
import com.example.apharma.ui.home.HomeFragment;
import com.example.apharma.ui.sensors.Sensors;
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
            addNotification();
            holder.measurement.setBackground(context.getDrawable(R.color.red));
            holder.measurement.setText(list.get(position).getReadingValue() + " DANGER!");
//            Toast.makeText(context, list.get(position).getSensor() + " measurement should be less than " + list.get(position).getConstraintMaxValue(), Toast.LENGTH_SHORT).show();
//        }



        }

        holder.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

                submit.setOnClickListener(v1 -> {
                    double min = Double.parseDouble(sensorMinVal.getText().toString());
                    double max = Double.parseDouble(sensorMaxVal.getText().toString());

//                        list.get(position).setConstraintMinValue(min);
//                        list.get(position).setConstraintMaxValue(max);
//                        sensorsViewModel.updateConstraints(list.get(position).getId(), list.get(position).getConstraintMinValue(), list.get(position).getConstraintMaxValue());

                    //Setting message manually and performing action on button click
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to set new constraints?").setPositiveButton("Yes", (dialogInterface, i) -> {
                        list.get(position).setConstraintMinValue(min);
                        list.get(position).setConstraintMaxValue(max);
                        sensorsViewModel.updateConstraints(list.get(position).getId(), list.get(position).getConstraintMinValue(), list.get(position).getConstraintMaxValue());
                        notifyDataSetChanged();

                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //  Action for 'Cancel' Button
                            dialogInterface.cancel();
                            notifyDataSetChanged();
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
    private void addNotification() {

        final String CHANNEL_ID = "HEADS_UP_NOTIFICATION";
        NotificationChannel channel= new NotificationChannel(
                CHANNEL_ID,
                "Heads Up Notification",
                NotificationManager.IMPORTANCE_HIGH
        );
        context.getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(context,CHANNEL_ID).setContentTitle("aPharma")
                .setContentText("DANGER, check conditions").setSmallIcon(R.drawable.pharmacy_icon)
                .setAutoCancel(true);

        Intent notificationIntent = new Intent(context, Sensors.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context
                , 0, notificationIntent,
                PendingIntent.FLAG_IMMUTABLE);
        notification.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification.build());
//        NotificationManagerCompat.from(context).notify(1,notification.build());
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
