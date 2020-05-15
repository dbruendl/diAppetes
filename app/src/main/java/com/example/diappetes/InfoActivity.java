package com.example.diappetes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //TIME
        DateFormat datetime = new SimpleDateFormat("yyyy HH:mm:ss");
        String date = datetime.format(Calendar.getInstance().getTime());

        TextView timetxt = findViewById(R.id.timetxt);
        timetxt.setText(date);
        //TIME

        Button homebtn2 = findViewById(R.id.homebtn);
        homebtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent starthomeintent2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(starthomeintent2);
            }
        });

        Button petbtn2 = findViewById(R.id.petbtn);
        petbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startpetintent2 = new Intent(getApplicationContext(), PetActivity.class);
                startActivity(startpetintent2);
            }
        });

        Button statbtn2 = findViewById(R.id.statbtn);
        statbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startstatintent2 = new Intent(getApplicationContext(), StatActivity.class);
                startActivity(startstatintent2);
            }
        });



    }
}
