package com.example.diappetes.sentilo;

import com.example.diappetes.StepListener;
import com.example.diappetes.sentilo.request.dto.BatteryObservationDTO;
import com.example.diappetes.sentilo.request.dto.SentiloRequestDTO;

import java.util.Collections;

/**
 * Counts the total amount of steps a user has taken and sends an update request to a Sentilo api
 * at a configurable interval.
 */
public class SentiloUpdateService implements StepListener {

    private final SentiloConnector sentiloConnector;
    private final int stepUpdateInterval;
    private int totalSteps;

    public SentiloUpdateService(SentiloConnector sentiloConnector, int stepUpdateInterval) {
        this.sentiloConnector = sentiloConnector;
        this.stepUpdateInterval = stepUpdateInterval;
        this.totalSteps = 0;
    }

    @Override
    public void step() {
        this.totalSteps++;

        if(thresholdReached()) {
            sendUpdateRequest();
        }
    }

    private boolean thresholdReached() {
        return totalSteps % stepUpdateInterval == 0;
    }

    private void sendUpdateRequest() {
        SentiloRequestDTO requestDTO = SentiloRequestDTO.builder()
                .batteryObservationDTOList(Collections.singletonList(
                        BatteryObservationDTO.builder()
                                .batteryPercentage("56")
                                .location("41.22157865201759 1.7300500669616392")
                                .build()
                ))
                .build();

        sentiloConnector.updateBatteryLocation(requestDTO);
    }
}
