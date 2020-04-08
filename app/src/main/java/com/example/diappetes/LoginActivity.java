package com.example.diappetes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button signinbtn = findViewById(R.id.signinbtn);
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailid = findViewById(R.id.logintxt);
                String emailinput = emailid.getText().toString();
                EditText passwordid = findViewById(R.id.passwordtxt);
                String passwordinput = passwordid.getText().toString();

                TextView errortxt = (TextView) findViewById(R.id.loginerrortxt);

                // PROFILE LIST
                String profilename="";
                String password ="";
                switch (emailinput){
                    case "olafwarzocha1998@gmail.com":
                        password = "1234";
                        profilename = "Admin";
                        break;
                    case "sample0@gmail.com":
                        password = "sample0";
                        profilename = "Sample0";
                        break;
                    case "sample1@gmail.com":
                        password ="sample1";
                        profilename = "Sample1";
                        break;
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("com.example.diappetes", profilename);
                startActivity(intent);

                // PROFILE LIST

                if(passwordinput.equals(password)){
                    Intent starthomeintent4 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(starthomeintent4);
                }
                else errortxt.setText("error");
            }
        });

        Button signupbtn2 = findViewById(R.id.signupbtn2);
        signupbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startregisterintent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(startregisterintent);
            }
        });
    }
}
