package com.example.diappetes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class StatActivity extends AppCompatActivity {
    Button homebtn4, infobtn4, petbtn4;
    ListView dbList;
    ArrayAdapter UserArrayAdapter;
    DatabaseSLite dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        homebtn4 = findViewById(R.id.homebtn4);
        petbtn4 = findViewById(R.id.petbtn4);
        infobtn4 = findViewById(R.id.infobtn4);
        dbList = findViewById(R.id.dbList);

        dataBaseHelper = new DatabaseSLite(StatActivity.this);
        UpdateList(dataBaseHelper);




        // Menu
        homebtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent starthomeintent4 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(starthomeintent4);
            }
        });


        infobtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startinfointent4 = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(startinfointent4);
            }
        });


        petbtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startpetintent4 = new Intent(getApplicationContext(), PetActivity.class);
                startActivity(startpetintent4);
            }
        });



    }
    private void UpdateList(DatabaseSLite dataBaseHelper2) {
        UserArrayAdapter = new ArrayAdapter<>(StatActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getEveryone());
        dbList.setAdapter(UserArrayAdapter);
    }
}
