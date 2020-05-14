package com.example.diappetes.sentilo.request.dto;

import com.google.gson.annotations.SerializedName;

public class BatteryObservationDTO {
    @SerializedName("value")
    private final String batteryPercentage;

    @SerializedName("location")
    private final String location;

    /**
     *
     * @param batteryPercentage e.g. "54" without the {@literal %} sign
     * @param location location (latitude, longitude) e.g. "41.22157865201759 1.7300500669616392"
     */
    public BatteryObservationDTO(String batteryPercentage, String location) {
        this.batteryPercentage = batteryPercentage;
        this.location = location;
    }

    public String getBatteryPercentage() {
        return batteryPercentage;
    }

    public String getLocation() {
        return location;
    }
}
