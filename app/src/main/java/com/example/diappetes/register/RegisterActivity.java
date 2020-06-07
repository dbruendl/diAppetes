package com.example.diappetes.register;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.example.diappetes.CheckInput;
import com.example.diappetes.R;
import com.example.diappetes.ViewModelProviderFactory;
import com.example.diappetes.persistence.AppDatabase;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class RegisterActivity extends DaggerAppCompatActivity {

    @Inject
    AppDatabase appDatabase;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    private RegisterViewModel registerViewModel;

    CheckInput ci;
    EditText emailId;
    Editable email;
    EditText userNametxt;
    Editable username;
    EditText passwordId;
    Editable password;
    EditText passwordId2;
    Editable password2;
    Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerViewModel = new ViewModelProvider(this, viewModelProviderFactory).get(RegisterViewModel.class);

        ci = new CheckInput();
        emailId = findViewById(R.id.signupemailtxt);   // Obtaining data from user input
        email = emailId.getText();
        userNametxt = findViewById(R.id.usernametxt);
        username = userNametxt.getText();
        passwordId = findViewById(R.id.signuppasswordtxt);
        password = passwordId.getText();
        passwordId2 = findViewById(R.id.signuppasswordtxt2);
        password2 = passwordId2.getText();
        final TextView signUpErrorTxt = findViewById(R.id.signuperrortxt);

        signUpBtn = findViewById(R.id.finishregisterbtn);
        // In android error record there is info about null error inside setOnClickListener below
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterModel rm;

                if (ci.checkEmail(email)) {
                    if (ci.checkPassword(password, password2)) {
                        Disposable registerUserDisposable = registerViewModel.registerUser(email.toString(), username.toString(), password.toString())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    Intent startRegisterFormIntent = new Intent(getApplicationContext(), RegisterFormActivity.class);
                                    startActivity(startRegisterFormIntent);
                                }, error -> {
                                    if(error instanceof SQLiteConstraintException) {
                                        signUpErrorTxt.setText("Username already exists");
                                    }
                                });
                    } else {
                        signUpErrorTxt.setText("The Passwords does not match, please make sure that they do");
                    }
                } else {
                    signUpErrorTxt.setText("The Email is not valid");
                }

            }
        });

    }
}
