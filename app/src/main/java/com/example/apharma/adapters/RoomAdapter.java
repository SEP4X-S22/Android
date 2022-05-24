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
import com.example.apharma.models.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private List<Room> rooms;
    Context context;
    private OnListItemClickListener mOnListItemClickListener;

    public RoomAdapter(Context context) {
        this.context = context;
        this.rooms = new ArrayList<>();
    }

    public interface OnListItemClickListener {
        void onClick(Room room);
    }

    public void setOnClickListener(OnListItemClickListener listener) {
        mOnListItemClickListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.room_item, parent, false);
        return new RoomAdapter.ViewHolder(view);
    }


    public void update(List<Room> list) {
        rooms = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (rooms.get(position).getId().equalsIgnoreCase("0004A30B00E7E072")) {
            holder.name.setText("Room 1");
        } else {
            holder.name.setText("Room 2");
        }

        holder.nrOfSensors.setText( rooms.get(position).getSensorsCount() + " sensors");


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

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView src;
        TextView nrOfSensors;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.room_title);
            nrOfSensors = itemView.findViewById(R.id.room_sensors);
            src = itemView.findViewById(R.id.room_image);
            itemView.setOnClickListener(view -> {
                mOnListItemClickListener.onClick(rooms.get(getAdapterPosition()));
            });
        }


    }
}