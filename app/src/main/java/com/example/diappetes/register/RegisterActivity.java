package com.example.diappetes.register;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.example.diappetes.R;
import com.example.diappetes.ViewModelProviderFactory;
import com.example.diappetes.databinding.ActivityRegisterBinding;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.diappetes.register.AdditionalDataActivity.INTENT_KEY_EMAIL;
import static com.example.diappetes.register.AdditionalDataActivity.INTENT_KEY_PASSWORD;
import static com.example.diappetes.register.AdditionalDataActivity.INTENT_KEY_UID;

public class RegisterActivity extends DaggerAppCompatActivity {

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    private RegisterViewModel registerViewModel;
    private ActivityRegisterBinding activityRegisterBinding;
    private Disposable validateUniqueUsernameDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterBinding.getRoot());

        registerViewModel = new ViewModelProvider(this, viewModelProviderFactory).get(RegisterViewModel.class);

        // In android error record there is info about null error inside setOnClickListener below
        activityRegisterBinding.finishregisterbtn.setOnClickListener(v -> {
            validateUniqueUsernameDisposable = registerViewModel.usernameAlreadyTaken(activityRegisterBinding.usernametxt.getText().toString())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        Intent startRegisterFormIntent = new Intent(getApplicationContext(), AdditionalDataActivity.class)
                                .putExtra(INTENT_KEY_EMAIL, activityRegisterBinding.signupemailtxt.getText().toString())
                                .putExtra(INTENT_KEY_UID, activityRegisterBinding.usernametxt.getText().toString())
                                .putExtra(INTENT_KEY_PASSWORD, activityRegisterBinding.signuppasswordtxt.getText().toString());
                        startActivity(startRegisterFormIntent);
                    }, error -> {
                        if (error instanceof UsernameAlreadyTakenException) {
                            activityRegisterBinding.signuperrortxt.setText(R.string.register_username_already_exists);
                        } else {
                            activityRegisterBinding.signuperrortxt.setText(error.getMessage());
                        }
                    });
//            Disposable registerUserDisposable = registerViewModel.registerUser(email.toString(), username.toString(), password.toString(), password2.toString())
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(() -> {
//
//                    }, error -> {
//                        if (error instanceof SQLiteConstraintException) {
//                            signUpErrorTxt.setText(R.string.register_username_already_exists);
//                        } else if(error instanceof InvalidEmailException) {
//                            signUpErrorTxt.setText(R.string.register_invalid_email);
//                        } else if(error instanceof InvalidUidException) {
//                            signUpErrorTxt.setText(R.string.login_invalid_username);
//                        } else if(error instanceof InvalidPasswordException) {
//                            signUpErrorTxt.setText(R.string.register_invalid_password);
//                        } else if(error instanceof PasswordDoNotMatchException) {
//                            signUpErrorTxt.setText(R.string.register_passwords_do_not_match);
//                        }
//                    });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (validateUniqueUsernameDisposable != null) {
            validateUniqueUsernameDisposable.dispose();
        }
    }
}
