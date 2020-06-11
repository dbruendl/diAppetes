package com.example.diappetes.sentilo.request.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class BatteryObservationDTO {


    @SerializedName("value")
    private final String value;

    /**
     * (latitude, longitude) e.g. "41.22157865201759 1.7300500669616392"
     */
    @SerializedName("location")
    private final String location;
}
