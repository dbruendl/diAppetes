package com.example.diappetes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        Button homebtn4 = findViewById(R.id.homebtn4);
        homebtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent starthomeintent4 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(starthomeintent4);
            }
        });

        Button infobtn4 = (Button)findViewById(R.id.infobtn4);
        infobtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startinfointent4 = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(startinfointent4);
            }
        });

        Button petbtn4 = findViewById(R.id.petbtn4);
        petbtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startpetintent4 = new Intent(getApplicationContext(), PetActivity.class);
                startActivity(startpetintent4);
            }
        });

    }
}
