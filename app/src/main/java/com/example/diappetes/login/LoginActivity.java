package com.example.diappetes.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.EmptyResultSetException;

import com.example.diappetes.databinding.ActivityLoginBinding;
import com.example.diappetes.main.MainActivity;
import com.example.diappetes.R;
import com.example.diappetes.StatisticsFragment;
import com.example.diappetes.register.RegisterActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {
    @Inject
    public LoginViewModel loginViewModel;

    private Disposable loginDisposable;

    ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());

        activityLoginBinding.signinbtn.setOnClickListener(v -> {
            String uid = ((TextView) findViewById(R.id.logintxt)).getText().toString();
            String password = ((TextView) findViewById(R.id.passwordtxt)).getText().toString();
            TextView errorTextView = findViewById(R.id.loginerrortxt);

            loginDisposable = loginViewModel.login(uid, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        Intent startHomeIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(startHomeIntent);
                    }, error -> {
                        if (error instanceof EmptyResultSetException) {
                            errorTextView.setText(R.string.login_invalid_username);
                        } else {
                            errorTextView.setText(error.getMessage());
                        }
                    });
        });

        activityLoginBinding.registerbtn.setOnClickListener(v -> {
            Intent startRegisterIntent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(startRegisterIntent);
        });

        /*
         * this button is just for development and takes you directly to the stats page, just for
         * testing not intended for the final project
         */
        activityLoginBinding.hackMeInPls.setOnClickListener(v -> {
            Intent takeMeToStatIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(takeMeToStatIntent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (loginDisposable != null) {
            loginDisposable.dispose();
        }
    }
}
