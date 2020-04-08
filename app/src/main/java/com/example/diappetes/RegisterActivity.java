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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText emailid = findViewById(R.id.signupemailtxt);   // Obtaining data from user input
        Editable email = emailid.getText();
        EditText passwordid = findViewById(R.id.signuppasswordtxt);
        Editable password = passwordid.getText();
        EditText passwordid2 = findViewById(R.id.signuppasswordtxt2);
        Editable password2 = passwordid.getText();
        final TextView signuperrortxt = findViewById(R.id.signuperrortxt);

        Matcher matcher;    //Checking if email input is correct
        final boolean[][] checkemail = {{false}};
        if (email != null) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(email);
            checkemail[0][0] = matcher.matches();
        }

        final boolean[] checkpassword = {false};   //Checking if password input is correct
        if (password == password2){
            checkpassword[0] = true;
        }

        Button signupbtn = (Button) findViewById(R.id.signupbtn);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String errormessage;
                String error1;
                String error2;

                if (checkemail[0][0] = true){
                    if (checkpassword[0] = true){
                                                                 //Register placeholder
                        Intent startloginintent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(startloginintent);
                    }
                    else error2="Passwords does not match";
                }
                else error1="Email is incorrect";




            }
        });







    }
}
