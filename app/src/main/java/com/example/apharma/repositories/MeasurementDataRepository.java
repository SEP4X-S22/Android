package com.example.apharma.repositories;

public class MeasurementDataRepository {
    private static MeasurementDataRepository instance;

    public static MeasurementDataRepository getInstance() {
        if (instance == null) {
            instance = new MeasurementDataRepository();
        }
        return instance;
    }
}
