package com.example.diappetes.observer;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.Observer;

import com.example.diappetes.StepTrackerService;
import com.example.diappetes.main.MainActivity;
import com.example.diappetes.persistence.model.Setting;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StartStepTrackingServiceObserver implements Observer<Setting> {

    private final Context context;
    private final String loggedInUID;

    @Override
    public void onChanged(Setting setting) {
        if(setting.enabled) {
            Intent startStepTrackerServiceIntent = new Intent(context, StepTrackerService.class)
                    .putExtra(StepTrackerService.UID_INTENT_KEY, loggedInUID)
                    .putExtra(StepTrackerService.NOTIFICATION_CHANNEL_INTENT_KEY, MainActivity.CHANNEL_ID);

            context.startService(startStepTrackerServiceIntent);
        } else {
            context.stopService(new Intent(context, StepTrackerService.class));
        }
    }
}
