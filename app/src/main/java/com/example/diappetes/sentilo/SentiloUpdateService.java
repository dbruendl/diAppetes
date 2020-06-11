package com.example.diappetes.sentilo;

import androidx.lifecycle.Observer;

import com.example.diappetes.persistence.model.Report;
import com.example.diappetes.sentilo.request.dto.BatteryObservationDTO;
import com.example.diappetes.sentilo.request.dto.SentiloRequestDTO;

import java.util.Collections;

/**
 * Counts the total amount of steps a user has taken and sends an update request to a Sentilo api
 * at a configurable interval.
 */
public class SentiloUpdateService implements Observer<Report> {

    private final SentiloConnector sentiloConnector;
    private final int stepUpdateInterval;

    public SentiloUpdateService(SentiloConnector sentiloConnector, int stepUpdateInterval) {
        this.sentiloConnector = sentiloConnector;
        this.stepUpdateInterval = stepUpdateInterval;
    }

    @Override
    public void onChanged(Report report) {
        if(updateIntervalReached(report.steps)) {
            sendUpdateRequest(report.steps);
        }
    }

    private boolean updateIntervalReached(int totalSteps) {
        return totalSteps % stepUpdateInterval == 0;
    }

    private void sendUpdateRequest(int totalSteps) {

        SentiloRequestDTO requestStepDTO = SentiloRequestDTO.builder()
                .batteryObservationDTOList(Collections.singletonList(
                        BatteryObservationDTO.builder()
                                .value(String.valueOf(totalSteps))
                                .location("41.22157865201759 1.7300500669616392")
                                .build()
                ))
                .build();

        sentiloConnector.updateSteps(requestStepDTO);
    }
}
