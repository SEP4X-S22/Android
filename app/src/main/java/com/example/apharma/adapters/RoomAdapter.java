package com.example.apharma.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apharma.R;
import com.example.apharma.models.Room;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private ArrayList<Room> rooms;
    final private OnListItemClickListener mOnListItemClickListener;

    public RoomAdapter(ArrayList<Room> rooms, OnListItemClickListener mOnListItemClickListener) {
        this.rooms = rooms;
        this.mOnListItemClickListener = mOnListItemClickListener;
    }

    public interface OnListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.room_item, parent, false);
        return new RoomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(rooms.get(position).getName());
        String s = holder.nrOfSensors.getText().toString();
        if (rooms.get(position).getSensors().size() == 1)
        {
            holder.nrOfSensors.setText(rooms.get(position).getSensors().size() + " sensor");
        } else {
            holder.nrOfSensors.setText(rooms.get(position).getSensors().size() + " sensors");
        }
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public Room getSelectedRoom(int position) {
        if (rooms != null) {
            if (rooms.size() > 0) {
                return rooms.get(position);
            }
        }
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        ImageView src;
        TextView nrOfSensors;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.room_title);
            nrOfSensors = itemView.findViewById(R.id.room_sensors);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnListItemClickListener.onListItemClick(getAdapterPosition());
        }
    }
}