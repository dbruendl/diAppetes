package com.example.diappetes.sentilo.request.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class SentiloRequestDTO {
    @SerializedName("observations")
    private final List<BatteryObservationDTO> batteryObservationDTOList;

    public SentiloRequestDTO(BatteryObservationDTO... batteryObservationDTOS) {
        this.batteryObservationDTOList = Arrays.asList(batteryObservationDTOS);
    }

    public List<BatteryObservationDTO> getBatteryObservationDTOList() {
        return batteryObservationDTOList;
    }
}
