package com.example.diappetes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    CheckInput ci;
    EditText emailId;
    Editable email;
    EditText passwordId;
    Editable password;
    EditText passwordId2;
    Editable password2;
    boolean cMail;
    boolean cPassword;
    Button signupBtn;
    String error1;
    String error2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ci = new CheckInput();
        emailId = findViewById(R.id.signupemailtxt);   // Obtaining data from user input
        email = emailId.getText();
        passwordId = findViewById(R.id.signuppasswordtxt);
        password = passwordId.getText();
        passwordId2 = findViewById(R.id.signuppasswordtxt2);
        password2 = passwordId2.getText();
        final TextView signupErrorTxt = findViewById(R.id.signuperrortxt);
        cMail = ci.checkEmail(email);
        cPassword = ci.checkPassword(password,password2);


        signupBtn = (Button) findViewById(R.id.signupbtn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cMail){
                    if (cPassword){
                         //Register placeholder
                        Intent startLoginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(startLoginIntent);
                    } else{
                        signupErrorTxt.setText("The Passwords does not match, please make sure that they do");
                    }
                } else {
                    signupErrorTxt.setText("The Email is not valid");
                }
            }
        });

    }
}
