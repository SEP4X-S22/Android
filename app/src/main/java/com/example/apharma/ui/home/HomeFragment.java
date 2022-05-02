package com.example.apharma.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apharma.R;
import com.example.apharma.adapters.RoomAdapter;
import com.example.apharma.databinding.FragmentHomeBinding;
import com.example.apharma.models.MeasurementData;
import com.example.apharma.models.Room;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment  implements RoomAdapter.OnListItemClickListener {

    private FragmentHomeBinding binding;
    private NavController navController;
    private RecyclerView roomList;
    private RoomAdapter roomAdapter;
    ArrayList<Room> rooms;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rooms = new ArrayList<>();
        rooms.add(new Room("Storage room", 1));
        rooms.add(new Room("Main room", 2));
        rooms.add(new Room("room", 3));

        List<MeasurementData> sensorData = new ArrayList<>();
        MeasurementData temp = new MeasurementData(1, 40, 10, 60, "4/29/22", "Temp");
        sensorData.add(temp);
        rooms.get(0).setSensors(sensorData);


        roomList = root.findViewById(R.id.recycleView);
        ConfigureRecyclerView();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void ConfigureRecyclerView() {

        roomAdapter = new RoomAdapter(rooms, this);
        roomList.setAdapter(roomAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);

        roomList.setLayoutManager(gridLayoutManager);
        roomList.setHasFixedSize(true);


    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        if (rooms.get(clickedItemIndex).isEmpty()) {
            Toast.makeText(getActivity(), "Nothing here yet", Toast.LENGTH_SHORT).show();
        } else {
            Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_sensors);
        }

    }
}