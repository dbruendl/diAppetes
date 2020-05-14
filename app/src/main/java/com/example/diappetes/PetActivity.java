package com.example.diappetes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);

        Button homebtn3 = findViewById(R.id.homebtn);
        homebtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent starthomeintent3 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(starthomeintent3);
            }
        });

        Button infobtn3 = (Button)findViewById(R.id.infobtn);
        infobtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startinfointent3 = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(startinfointent3);
            }
        });

        Button statbtn3 = findViewById(R.id.statbtn);
        statbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startstatintent3 = new Intent(getApplicationContext(), StatActivity.class);
                startActivity(startstatintent3);
            }
        });




    }
}
