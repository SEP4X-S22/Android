package com.example.apharma.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static RoomApi roomApi;

    public static RoomApi getRoomApi() {
        if (roomApi == null) {
            roomApi = new Retrofit.Builder()
                    .baseUrl("someUrl")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(RoomApi.class);
        }
        return roomApi;
    }
}
