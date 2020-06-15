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
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;

import com.android.volley.toolbox.Volley;
import com.example.diappetes.BatteryStatusChangedReceiver;
import com.example.diappetes.PetFragment;
import com.example.diappetes.R;
import com.example.diappetes.StatisticsFragment;
import com.example.diappetes.WalkReminderNotificationReceiver;
import com.example.diappetes.databinding.ActivityMainBinding;
import com.example.diappetes.info.InfoFragment;
import com.example.diappetes.login.LoginActivity;
import com.example.diappetes.login.LoginService;
import com.example.diappetes.persistence.AppDatabase;
import com.example.diappetes.persistence.model.Report;
import com.example.diappetes.sentilo.SentiloConnector;
import com.example.diappetes.sentilo.SentiloConnectorVolleyImpl;
import com.example.diappetes.sentilo.SentiloUpdateService;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.disposables.Disposable;

import static com.example.diappetes.WalkReminderNotificationReceiver.KEY_CHANNEL_ID;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    public AppDatabase appDatabase;

    @Inject
    public MainViewModel mainViewModel;

    @Inject
    public LoginService loginService;

    public ActivityMainBinding binding;

    private StepTrackingFragment stepTrackingFragment;
    private StatisticsFragment statisticsFragment;
    private InfoFragment infoFragment;
    private NavigationDrawerReplaceInContainerFragment navigationDrawerReplaceInContainerFragment;

    private final static String SENTILO_IDENTITY_KEY = "c7337d3fc4ec28d0dddc81478808a8b6b82beb83110fcb00157cc0a711956475";
    public final static String CHANNEL_ID = "69"; //nice
    private static final int SENTILO_STEP_UPDATE_INTERVAL = 10;

    private Calendar calendar = Calendar.getInstance();

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        stepTrackingFragment = new StepTrackingFragment(R.id.fragment_container, loginService.getLoggedInUID(), mainViewModel.getUserReportForToday(loginService.getLoggedInUID()));
        statisticsFragment = new StatisticsFragment(mainViewModel.findAllReports(loginService.getLoggedInUID()));
        infoFragment = new InfoFragment(R.id.fragment_container);

        navigationDrawerReplaceInContainerFragment = new NavigationDrawerReplaceInContainerFragment(R.id.fragment_container, new PetFragment(), infoFragment, stepTrackingFragment, statisticsFragment);

        SentiloConnector sentiloConnector = new SentiloConnectorVolleyImpl(Volley.newRequestQueue(this), SENTILO_IDENTITY_KEY);
        LiveData<Report> userReportForToday = mainViewModel.getUserReportForToday(loginService.getLoggedInUID());

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

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        BatteryStatusChangedReceiver bscr = new BatteryStatusChangedReceiver(sentiloConnector);
        registerReceiver(bscr, ifilter);

        StepTrackingFragment stepTrackingFragment = new StepTrackingFragment(R.id.fragment_container, loginService.getLoggedInUID(), userReportForToday);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, stepTrackingFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(loginService.isLoggedIn()) {
            getMenuInflater().inflate(R.menu.logged_in_toolbar, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (item.getItemId()) {
            case android.R.id.home:
                navigationDrawerReplaceInContainerFragment.show(fragmentManager, navigationDrawerReplaceInContainerFragment.getTag());
                return true;
            case R.id.action_logout:
                Disposable loginDisposable = loginService.logout().subscribe(() -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
