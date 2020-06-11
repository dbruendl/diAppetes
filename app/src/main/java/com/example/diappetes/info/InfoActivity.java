package com.example.diappetes.info;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diappetes.PetActivity;
import com.example.diappetes.R;
import com.example.diappetes.StatActivity;
import com.example.diappetes.databinding.ActivityInfoBinding;
import com.example.diappetes.main.MainActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class InfoActivity extends AppCompatActivity {
    @Inject
    public InfoViewModel infoViewModel;

    ActivityInfoBinding activityInfoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityInfoBinding = ActivityInfoBinding.inflate(getLayoutInflater());
        setContentView(activityInfoBinding.getRoot());

        activityInfoBinding.navigationView.setSelectedItemId(R.id.tab_info);
        activityInfoBinding.navigationView.setOnNavigationItemSelectedListener(item -> {

            Intent startIntent;

            switch (item.getItemId()) {
                case R.id.tab_home:
                    startIntent = new Intent(getApplicationContext(), MainActivity.class);
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
}
