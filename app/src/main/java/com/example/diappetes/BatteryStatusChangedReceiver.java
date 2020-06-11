package com.example.diappetes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.example.diappetes.sentilo.SentiloConnector;
import com.example.diappetes.sentilo.request.dto.BatteryObservationDTO;
import com.example.diappetes.sentilo.request.dto.SentiloRequestDTO;

import java.util.Collections;

public class BatteryStatusChangedReceiver extends BroadcastReceiver {
    private SentiloConnector sentiloConnector;

    public BatteryStatusChangedReceiver(SentiloConnector sentiloConnector) {
        this.sentiloConnector = sentiloConnector;
    }

    public BatteryStatusChangedReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level * 100 / (float)scale;

        SentiloRequestDTO requestDTO = SentiloRequestDTO.builder()
                .batteryObservationDTOList(Collections.singletonList(
                        BatteryObservationDTO.builder()
                                .value(String.valueOf(batteryPct))
                                .location("41.22157865201759 1.7300500669616392")
                                .build()
                ))
                .build();

        sentiloConnector.updateBatteryLocation(requestDTO);
    }
}
