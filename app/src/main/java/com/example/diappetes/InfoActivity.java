package com.example.diappetes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InfoActivity extends AppCompatActivity {
    ListView dbList;
    ArrayAdapter UserArrayAdapter;
    DatabaseSLite dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        dbList = findViewById(R.id.dbList);

        dataBaseHelper = new DatabaseSLite(InfoActivity.this);
        UpdateList(dataBaseHelper);

        //TIME
        DateFormat datetime = new SimpleDateFormat("yyyy MM HH:mm:ss");
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
    private void UpdateList(DatabaseSLite dataBaseHelper2) {
        UserArrayAdapter = new ArrayAdapter<>(InfoActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getEveryone());
        dbList.setAdapter(UserArrayAdapter);
    }
}
