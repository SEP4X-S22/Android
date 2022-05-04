package com.example.apharma.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apharma.R;
import com.example.apharma.models.MeasurementData;

import java.util.ArrayList;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.ViewHolder> {

    private ArrayList<MeasurementData> list;
    private Context context;
    final private SensorAdapter.OnListItemClickListener mOnListItemClickListener;

    public SensorAdapter( ArrayList<MeasurementData> list, SensorAdapter.OnListItemClickListener mOnListItemClickListener) {
        this.list = list;
        this.context = context;
        this.mOnListItemClickListener = mOnListItemClickListener;
    }

    public interface OnListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.sensor_item, parent, false);
        return new SensorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorAdapter.ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getType());
        holder.measurement.setText(list.get(position).getValue() + "°C");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public MeasurementData getSelectedSensor(int position) {
        if (list != null) {
            if (list.size() > 0) {
                return list.get(position);
            }
        }
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        ImageView src;
        TextView measurement;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.sensor_title);
            measurement = itemView.findViewById(R.id.sensor_measurement);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnListItemClickListener.onListItemClick(getAdapterPosition());
        }
    }
}