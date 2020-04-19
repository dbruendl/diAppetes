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

    EditText emailId;
    Editable email;
    EditText passwordId;
    Editable password;
    EditText passwordId2;
    Editable password2;
    Matcher matcher;    //Checking if email input is correct
    boolean cMail;
    boolean cPassword;
    Button signupBtn;
    String error1;
    String error2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailId = findViewById(R.id.signupemailtxt);   // Obtaining data from user input
        email = emailId.getText();
        passwordId = findViewById(R.id.signuppasswordtxt);
        password = passwordId.getText();
        passwordId2 = findViewById(R.id.signuppasswordtxt2);
        password2 = passwordId2.getText();
        final TextView signupErrorTxt = findViewById(R.id.signuperrortxt);
        cMail = checkEmail(email);
        cPassword = checkPassword(password,password2);


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

    private boolean checkPassword(Editable password, Editable password2) {
        if (password == password2){
            return true;
        }
        return false;
    }

    private boolean checkEmail(Editable email) {
        if (email != null) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(email);
            return matcher.matches();
        }
        return false;
    }
}
