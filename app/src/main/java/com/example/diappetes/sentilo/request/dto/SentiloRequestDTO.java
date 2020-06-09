package com.example.diappetes.sentilo.request.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class SentiloRequestDTO {
    @SerializedName("observations")
    private final List<BatteryObservationDTO> batteryObservationDTOList;
}
