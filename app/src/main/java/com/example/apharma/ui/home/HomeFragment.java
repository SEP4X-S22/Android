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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apharma.R;
import com.example.apharma.adapters.RoomAdapter;
import com.example.apharma.databinding.FragmentHomeBinding;
import com.example.apharma.models.Room;
import com.example.apharma.ui.sensors.SensorsViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private NavController navController;
    private RecyclerView roomList;
    private RoomAdapter roomAdapter;
    private HomeViewModel homeViewModel;
    private SensorsViewModel sensorsViewModel;
    private TextView name;
    ArrayList<Room> roomsData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        roomList = root.findViewById(R.id.recycleView);
        name = root.findViewById(R.id.room_title);
        homeViewModel.getRooms().observe(getViewLifecycleOwner(), rooms -> {
            roomAdapter.update(rooms);

        });
        homeViewModel.getListOfRooms().observe(getViewLifecycleOwner(), rooms -> {
            roomAdapter.update(rooms);

        });


        homeViewModel.fetchRooms();


        ConfigureRecyclerView();

        roomAdapter.setOnClickListener(view -> {
            HomeFragmentDirections.ActionNavigationHomeToSensors actionNavigationHomeToSensors = HomeFragmentDirections.actionNavigationHomeToSensors();
            actionNavigationHomeToSensors.setRoomId(view.getId());
            NavHostFragment.findNavController(this).navigate(actionNavigationHomeToSensors);
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void ConfigureRecyclerView() {

        roomAdapter = new RoomAdapter(getContext());
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