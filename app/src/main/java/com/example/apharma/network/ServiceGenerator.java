package com.example.apharma.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static MeasurementDataApi measurementDataApi;

    public static MeasurementDataApi getMeasurementDataApi() {
        if (measurementDataApi == null) {
            measurementDataApi = new Retrofit.Builder()
                    .baseUrl("someUrl")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MeasurementDataApi.class);
        }
        return measurementDataApi;
    }
}
