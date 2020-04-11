package com.example.diappetes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    CheckInput c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button signInBtn = findViewById(R.id.signinbtn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailId = findViewById(R.id.logintxt);
                EditText passwordId = findViewById(R.id.passwordtxt);
                c = new CheckInput(emailId.getText().toString(),passwordId.getText().toString());
                /*
                String emailInput = emailId.getText().toString();
                String passwordInput = passwordId.getText().toString();*/
                TextView errorTxt = (TextView) findViewById(R.id.loginerrortxt);

                if(c.checkEmail()) {
                    if(c.checkPassword()) {
                        Intent starthomeintent4 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(starthomeintent4);
                    }else{
                        errorTxt.setText("error: your password is wrong");
                    }
                }else{
                    errorTxt.setText("error: your Email is not registered, please do so");
                }
            }
        });

        Button signInBtn2 = findViewById(R.id.signupbtn2);
        signInBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startregisterintent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(startregisterintent);
            }
        });
    }
}
