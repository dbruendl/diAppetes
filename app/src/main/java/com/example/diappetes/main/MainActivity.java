package com.example.diappetes.main;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.android.volley.toolbox.Volley;
import com.example.diappetes.BatteryStatusChangedReceiver;
import com.example.diappetes.PetActivity;
import com.example.diappetes.R;
import com.example.diappetes.StatActivity;
import com.example.diappetes.StepTrackerService;
import com.example.diappetes.WalkReminderNotificationReceiver;
import com.example.diappetes.databinding.ActivityMainBinding;
import com.example.diappetes.info.InfoActivity;
import com.example.diappetes.login.LoginViewModel;
import com.example.diappetes.observer.PetStepGoalObserver;
import com.example.diappetes.observer.ProgressBarStepGoalObserver;
import com.example.diappetes.observer.TextViewStepObserver;
import com.example.diappetes.persistence.AppDatabase;
import com.example.diappetes.persistence.model.Report;
import com.example.diappetes.sentilo.SentiloConnector;
import com.example.diappetes.sentilo.SentiloConnectorVolleyImpl;
import com.example.diappetes.sentilo.SentiloUpdateService;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

import static com.example.diappetes.WalkReminderNotificationReceiver.KEY_CHANNEL_ID;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    public AppDatabase appDatabase;

    @Inject
    public MainViewModel mainViewModel;

    @Inject
    public LoginViewModel loginViewModel;

    public ActivityMainBinding activityMainBinding;

    private final static String SENTILO_IDENTITY_KEY = "c7337d3fc4ec28d0dddc81478808a8b6b82beb83110fcb00157cc0a711956475";
    private final static String CHANNEL_ID = "69"; //nice
    private static final int SENTILO_STEP_UPDATE_INTERVAL = 10;

    // TODO: replace this with a call to the respective login manager
    private final static String LOGGED_IN_USER_ID = "t";
    private final static int USER_STEP_GOAL = 10;

    private Calendar calendar = Calendar.getInstance();

    public int progress;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        SentiloConnector sentiloConnector = new SentiloConnectorVolleyImpl(Volley.newRequestQueue(this), SENTILO_IDENTITY_KEY);
        LiveData<Report> userReportForToday = mainViewModel.getUserReportForToday(LOGGED_IN_USER_ID);

        userReportForToday.observe(this, new PetStepGoalObserver(activityMainBinding.petmini, USER_STEP_GOAL));
        userReportForToday.observe(this, new ProgressBarStepGoalObserver(activityMainBinding.pbSteps, USER_STEP_GOAL));
        userReportForToday.observe(this, new TextViewStepObserver(activityMainBinding.tvSteps));
        userReportForToday.observe(this, new SentiloUpdateService(sentiloConnector, SENTILO_STEP_UPDATE_INTERVAL));

        createNotificationChannel();

        // Create an explicit intent for an Activity in your app
        final Intent intent = new Intent(this, WalkReminderNotificationReceiver.class)
                .putExtra(KEY_CHANNEL_ID, CHANNEL_ID);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 2135323, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        activityMainBinding.trackSteps.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Intent startStepTrackerServiceIntent = new Intent(this, StepTrackerService.class)
                        .putExtra(StepTrackerService.UID_INTENT_KEY, LOGGED_IN_USER_ID)
                        .putExtra(StepTrackerService.NOTIFICATION_CHANNEL_INTENT_KEY, CHANNEL_ID);
                startService(startStepTrackerServiceIntent);
            } else {
                Intent stopStepTrackerServiceIntent = new Intent(this, StepTrackerService.class);

                stopService(stopStepTrackerServiceIntent);
            }
        });

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        BatteryStatusChangedReceiver bscr = new BatteryStatusChangedReceiver(sentiloConnector);
        registerReceiver(bscr, ifilter);

        activityMainBinding.navigationView.setSelectedItemId(R.id.tab_home);
        activityMainBinding.navigationView.setOnNavigationItemSelectedListener(item -> {
            Intent startIntent;

            switch (item.getItemId()) {
                case R.id.tab_info:
                    startIntent = new Intent(getApplicationContext(), InfoActivity.class);
                    break;
                case R.id.tab_pet:
                    startIntent = new Intent(getApplicationContext(), PetActivity.class);
                    break;
                case R.id.tab_stat:
                    startIntent = new Intent(getApplicationContext(), StatActivity.class);
                    break;
                default:
                    return false;
            }

            startActivity(startIntent);

            return true;
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
