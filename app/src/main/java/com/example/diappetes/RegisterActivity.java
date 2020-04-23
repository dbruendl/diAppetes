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
    Button signUpBtn;

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
        final TextView signUpErrorTxt = findViewById(R.id.signuperrortxt);


        signUpBtn = (Button) findViewById(R.id.signupbtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterModel rm;
                if (ci.checkEmail(email)){
                    if (ci.checkPassword(password,password2)){
                        rm = new RegisterModel(-1,email.toString(),password.toString());
                         //Register placeholder
                        DatabaseSLite dbsl = new DatabaseSLite(RegisterActivity.this);
                        boolean success = dbsl.addOne(rm);
                        if(success) {
                            Intent startLoginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(startLoginIntent);
                        }else{
                            signUpErrorTxt.setText("The Database input wasn´t succesful");
                        }
                    } else{
                        signUpErrorTxt.setText("The Passwords does not match, please make sure that they do");
                    }
                } else {
                    signUpErrorTxt.setText("The Email is not valid");
                }
            }
        });

    }
}
