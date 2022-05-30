package com.example.apharma.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static PharmaApi pharmaApi;

    public static PharmaApi getPharmaApi() {
        if (pharmaApi == null) {
            pharmaApi = new Retrofit.Builder()
                    .baseUrl("http://sep4data-env-1.eba-9xg2hdtz.eu-central-1.elasticbeanstalk.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PharmaApi.class);
        }
        return pharmaApi;
    }
}
