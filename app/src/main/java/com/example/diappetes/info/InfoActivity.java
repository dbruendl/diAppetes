package com.example.diappetes.info;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diappetes.PetActivity;
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

        activityInfoBinding.homebtn.setOnClickListener(v -> {
            Intent starthomeintent2 = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(starthomeintent2);
        });

        activityInfoBinding.petbtn.setOnClickListener(v -> {
            Intent startpetintent2 = new Intent(getApplicationContext(), PetActivity.class);
            startActivity(startpetintent2);
        });

        activityInfoBinding.statbtn.setOnClickListener(v -> {
            Intent startstatintent2 = new Intent(getApplicationContext(), StatActivity.class);
            startActivity(startstatintent2);
        });
    }
}
