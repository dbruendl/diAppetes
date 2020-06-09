package com.example.diappetes.sentilo;

import com.example.diappetes.sentilo.request.dto.SentiloRequestDTO;

public interface SentiloConnector {
    void updateBatteryLocation(SentiloRequestDTO sentiloRequestDTO);
}
