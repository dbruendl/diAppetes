package com.example.diappetes.sentilo.request.dto;

import java.util.Arrays;
import java.util.List;

public class SentiloRequestDTO {
    private final List<ObservationDTO> observationDTOList;

    public SentiloRequestDTO(ObservationDTO ... observationDTOS) {
        this.observationDTOList = Arrays.asList(observationDTOS);
    }

    public List<ObservationDTO> getObservationDTOList() {
        return observationDTOList;
    }
}
