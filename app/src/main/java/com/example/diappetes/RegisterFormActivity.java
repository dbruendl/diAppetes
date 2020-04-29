package com.example.diappetes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        weighttxt = findViewById(R.id.weighttxt);

        nametxt.getText();
        surnametxt.getText();
        proffesiontxt.getText();
        heihttxt.getText();
        weighttxt.getText();

        height = Double.parseDouble(heihttxt.toString());  //Converting to double from text
        weight = Double.parseDouble(weighttxt.toString());


        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startLoginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(startLoginIntent);
            }
        });




    }
}
