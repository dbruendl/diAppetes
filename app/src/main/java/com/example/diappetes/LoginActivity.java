package com.example.diappetes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diappetes.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {
    CheckInput c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginBtn = findViewById(R.id.signinbtn);

        /*
         *loginBtn send the data to CheckInput, and checks if email or password is already registered
         * if one of the two inputs is empty or there is no register in the local database an Errortextmessage will appear
         * if everything is correct the screen changes to the homescreen
         */
        loginBtn.setOnClickListener(v -> {
            EditText emailId = findViewById(R.id.logintxt);
            EditText passwordId = findViewById(R.id.passwordtxt);
            TextView errorTxt = findViewById(R.id.loginerrortxt);

            if (emailId.getText().toString().isEmpty() || passwordId.getText().toString().isEmpty()) {
                errorTxt.setText("error: Please type in an Email and password");
            } else {
                c = new CheckInput(emailId.getText().toString(), passwordId.getText().toString(), LoginActivity.this);

                if (c.loginCheckEmail()) {
                    if (c.loginCheckPassword()) {
                        Intent startHomeIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(startHomeIntent);
                    } else {
                        errorTxt.setText("error: Your password is wrong");
                    }
                } else {
                    errorTxt.setText("error: Your Email is not registered yet, please do so");
                }
            }
        });

        Button registerbtn = findViewById(R.id.registerbtn);
        /*
         * if the user is not registered yet this button takes him to the registerscreen
         */
        registerbtn.setOnClickListener(v -> {
            Intent startRegisterIntent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(startRegisterIntent);
        });

        Button takemetostat = findViewById(R.id.takemetostat);
        /*
         * this button is just for development and takes you directly to the stats page, just for testing not intended for
         * the final project
         */
        takemetostat.setOnClickListener(v -> {
            Intent takeMeToStatIntent = new Intent(getApplicationContext(), StatActivity.class);
            startActivity(takeMeToStatIntent);
        });

        }
}
