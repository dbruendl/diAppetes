package com.example.diappetes.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.EmptyResultSetException;

import com.example.diappetes.CheckInput;
import com.example.diappetes.MainActivity;
import com.example.diappetes.R;
import com.example.diappetes.StatActivity;
import com.example.diappetes.ViewModelProviderFactory;
import com.example.diappetes.register.RegisterActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends DaggerAppCompatActivity {
    CheckInput c;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    private LoginViewModel loginViewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginBtn = findViewById(R.id.signinbtn);

        loginViewModel = new ViewModelProvider(this, viewModelProviderFactory).get(LoginViewModel.class);

        /*
         *loginBtn send the data to CheckInput, and checks if email or password is already registered
         * if one of the two inputs is empty or there is no register in the local database an Errortextmessage will appear
         * if everything is correct the screen changes to the homescreen
         */
        loginBtn.setOnClickListener(v -> {
            String uid = ((TextView) findViewById(R.id.logintxt)).getText().toString();
            String password = ((TextView) findViewById(R.id.passwordtxt)).getText().toString();
            TextView errorTextView = findViewById(R.id.loginerrortxt);

            Disposable disposable = loginViewModel.login(uid, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        Intent startHomeIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(startHomeIntent);
                    }, error -> {
                        if(error instanceof EmptyResultSetException) {
                             errorTextView.setText(R.string.login_invalid_username);
                        } else {
                            errorTextView.setText(error.getMessage());
                        }
                    });
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
            Disposable disposable = loginViewModel.findAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(users -> {
                        return;
                    });
            Intent takeMeToStatIntent = new Intent(getApplicationContext(), StatActivity.class);
            startActivity(takeMeToStatIntent);
        });

    }
}
