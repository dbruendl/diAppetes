package com.example.diappetes.sentilo.request.dto;

import androidx.annotation.NonNull;

public class LocationDTO {
    private final float latitude;

    private final float longitude;

    public LocationDTO(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    @NonNull
    @Override
    public String toString() {
        return latitude + " " + longitude;
    }
}
