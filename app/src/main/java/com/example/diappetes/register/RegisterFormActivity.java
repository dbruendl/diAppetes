package com.example.diappetes.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.diappetes.LoginActivity;
import com.example.diappetes.R;

public class RegisterFormActivity extends AppCompatActivity {

    Button nextbtn;
    EditText nametxt, surnametxt, proffesiontxt, heihttxt, weighttxt;
    double height, weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        nextbtn = findViewById(R.id.nextbtn);
        nametxt = findViewById(R.id.nametxt);
        surnametxt = findViewById(R.id.surnametxt);
        proffesiontxt = findViewById(R.id.professiontxt);
        heihttxt = findViewById(R.id.heighttxt);

        nextbtn.setOnClickListener(v -> {
            Intent startLoginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(startLoginIntent);
        });
    }
}
