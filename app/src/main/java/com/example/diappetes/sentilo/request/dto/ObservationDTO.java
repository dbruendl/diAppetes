package com.example.diappetes.sentilo.request.dto;

import android.location.Location;

public class ObservationDTO {
    private final String value;

    private final String location;

    public ObservationDTO(String value, String location) {
        this.value = value;
        this.location = location;
    }

    public String getValue() {
        return value;
    }

    public String getLocation() {
        return location;
    }
}
