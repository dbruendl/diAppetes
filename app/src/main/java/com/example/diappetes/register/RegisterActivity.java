package com.example.diappetes.register;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.diappetes.R;
import com.example.diappetes.databinding.ActivityRegisterBinding;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static com.example.diappetes.register.AdditionalDataActivity.INTENT_KEY_EMAIL;
import static com.example.diappetes.register.AdditionalDataActivity.INTENT_KEY_PASSWORD;
import static com.example.diappetes.register.AdditionalDataActivity.INTENT_KEY_UID;

public class RegisterActivity extends AppCompatActivity {

    @Inject
    public RegisterViewModel registerViewModel;

    private ActivityRegisterBinding activityRegisterBinding;
    private Disposable validateUniqueUsernameDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterBinding.getRoot());

        // In android error record there is info about null error inside setOnClickListener below
        activityRegisterBinding.finishregisterbtn.setOnClickListener(v -> {
            String email = activityRegisterBinding.signupemailtxt.getText().toString();
            String username = activityRegisterBinding.usernametxt.getText().toString();
            String password = activityRegisterBinding.signuppasswordtxt.getText().toString();
            String passwordConfirmation = activityRegisterBinding.signuppasswordtxt2.getText().toString();
            try {
                RegisterValidator.builder()
                        .email(email)
                        .uid(username)
                        .password(password)
                        .passwordConfirmation(passwordConfirmation)
                        .build()
                        .validate();

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
            } catch (InvalidEmailException e) {
                activityRegisterBinding.signuperrortxt.setText(R.string.register_invalid_email);
            } catch (InvalidUidException e) {
                activityRegisterBinding.signuperrortxt.setText(R.string.register_invalid_username);
            } catch (InvalidPasswordException e) {
                activityRegisterBinding.signuperrortxt.setText(R.string.register_invalid_password);
            } catch (PasswordDoNotMatchException e) {
                activityRegisterBinding.signuperrortxt.setText(R.string.register_passwords_do_not_match);
            }
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
