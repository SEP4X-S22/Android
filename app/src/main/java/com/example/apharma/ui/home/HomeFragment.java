package com.example.apharma.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apharma.R;
import com.example.apharma.adapters.RoomAdapter;
import com.example.apharma.databinding.FragmentHomeBinding;
import com.example.apharma.models.Room;
import com.example.apharma.ui.sensors.SensorsArgs;

import java.util.ArrayList;

public class HomeFragment extends Fragment  {

    private FragmentHomeBinding binding;
    private NavController navController;
    private RecyclerView roomList;
    private RoomAdapter roomAdapter;
    private HomeViewModel homeViewModel;
    private TextView name;
    ArrayList<Room> rooms;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        rooms = new ArrayList<>();
//        rooms.add(new Room("Storage room", 1));
//        rooms.add(new Room("Main room", 2));
//        rooms.add(new Room("room", 3));

//        List<MeasurementData> sensorData = new ArrayList<>();
//        MeasurementData temp = new MeasurementData(1, 40, 10, 60, "4/29/22", "Temp");
//        sensorData.add(temp);
//        rooms.get(0).setSensors(sensorData);


        rooms = new ArrayList<>();

        roomList = root.findViewById(R.id.recycleView);
        name = root.findViewById(R.id.room_title);
        homeViewModel.getRooms().observe(getViewLifecycleOwner(), room -> {


            rooms.addAll(getActualRooms(room));
        });
        homeViewModel.fetchRooms();

        ConfigureRecyclerView();

        roomAdapter.setOnClickListener(view ->{
            HomeFragmentDirections.ActionNavigationHomeToSensors actionNavigationHomeToSensors = HomeFragmentDirections.actionNavigationHomeToSensors();
            actionNavigationHomeToSensors.setRoomId(view.getId());
            Navigation.findNavController(getView()).navigate(actionNavigationHomeToSensors);
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public ArrayList<Room> getActualRooms(ArrayList<Room> rooms){
        ArrayList<Room> roomsToReturn = new ArrayList<>();

        for (int i = 0; i < rooms.size(); i++){
            if (rooms.get(i).getId() == 1 || rooms.get(i).getId() == 2){
                roomsToReturn.add(rooms.get(i));
            }
        }
        return roomsToReturn;

    }


    private void ConfigureRecyclerView() {

        roomAdapter = new RoomAdapter(rooms);
        roomList.setAdapter(roomAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);

        roomList.setLayoutManager(gridLayoutManager);
        roomList.setHasFixedSize(true);



    }

//    @Override
//    public void onListItemClick(int clickedItemIndex) {
////        if (rooms.get(clickedItemIndex).isEmpty()) {
////            Toast.makeText(getActivity(), "Nothing here yet", Toast.LENGTH_SHORT).show();
////        } else {
////homeViewModel.setRooms(rooms.get(clickedItemIndex));
//
//
//        HomeFragmentDirections.ActionNavigationHomeToSensors actionNavigationHomeToSensors = HomeFragmentDirections.actionNavigationHomeToSensors();
//        actionNavigationHomeToSensors.setRoomId(clickedItemIndex);
//        Navigation.findNavController(getView()).navigate(actionNavigationHomeToSensors);
//
//    }


}