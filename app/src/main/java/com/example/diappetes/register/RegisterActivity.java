package com.example.diappetes.register;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.example.diappetes.R;
import com.example.diappetes.ViewModelProviderFactory;
import com.example.diappetes.login.InvalidEmailException;
import com.example.diappetes.login.InvalidPasswordException;
import com.example.diappetes.login.InvalidUidException;
import com.example.diappetes.login.PasswordDoNotMatchException;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends DaggerAppCompatActivity {

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    private RegisterViewModel registerViewModel;

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
        signUpBtn.setOnClickListener(v -> {
            Disposable registerUserDisposable = registerViewModel.registerUser(email.toString(), username.toString(), password.toString(), password2.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        Intent startRegisterFormIntent = new Intent(getApplicationContext(), RegisterFormActivity.class);
                        startActivity(startRegisterFormIntent);
                    }, error -> {
                        if (error instanceof SQLiteConstraintException) {
                            signUpErrorTxt.setText(R.string.register_username_already_exists);
                        } else if(error instanceof InvalidEmailException) {
                            signUpErrorTxt.setText(R.string.register_invalid_email);
                        } else if(error instanceof InvalidUidException) {
                            signUpErrorTxt.setText(R.string.login_invalid_username);
                        } else if(error instanceof InvalidPasswordException) {
                            signUpErrorTxt.setText(R.string.register_invalid_password);
                        } else if(error instanceof PasswordDoNotMatchException) {
                            signUpErrorTxt.setText(R.string.register_passwords_do_not_match);
                        }
                    });
        });

    }
}
