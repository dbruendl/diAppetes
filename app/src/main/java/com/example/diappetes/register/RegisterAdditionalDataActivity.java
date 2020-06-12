package com.example.diappetes.register;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diappetes.R;
import com.example.diappetes.databinding.RegisterAdditionalDataActivityBinding;
import com.example.diappetes.login.LoginActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@AndroidEntryPoint
public class RegisterAdditionalDataActivity extends AppCompatActivity {

    public static final String INTENT_KEY_EMAIL = "EMAIL";
    public static final String INTENT_KEY_UID = "UID";
    public static final String INTENT_KEY_PASSWORD = "PASSWORD";

    private RegisterAdditionalDataActivityBinding binding;
    private Disposable registerUserDisposable;

    @Inject
    public AdditionalDataViewModel additionalDataViewModel; // TODO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RegisterAdditionalDataActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.next.setOnClickListener(v -> {
            Intent formIntent = getIntent();

            String email = formIntent.getStringExtra(INTENT_KEY_EMAIL);
            String uid = formIntent.getStringExtra(INTENT_KEY_UID);
            String password = formIntent.getStringExtra(INTENT_KEY_PASSWORD);
            String firstName = binding.firstName.getText().toString();
            String lastName = binding.lastName.getText().toString();
            String profession = binding.professiontxt.getText().toString();
            String weight = binding.weighttxt.getText().toString();
            String height = binding.height.getText().toString();
            String dailyStepGoal = binding.dailyStepGoal.getText().toString();

            AdditionalDataValidator additionalDataValidator = new AdditionalDataValidator.AdditionalDataValidatorBuilder()
                    .email(email)
                    .uid(uid)
                    .password(password)
                    .firstName(firstName)
                    .lastName(lastName)
                    .profession(profession)
                    .weight(weight)
                    .height(height)
                    .dailyStepGoal(dailyStepGoal)
                    .build();

            try {
                additionalDataValidator.validate();

                double weightDouble = Double.parseDouble(weight);
                double heightDouble = Double.parseDouble(height);
                int dailyStepGoalInteger = Integer.parseInt(dailyStepGoal);

                registerUserDisposable = additionalDataViewModel.register(uid, email, password, firstName, lastName, profession,
                        weightDouble, heightDouble, dailyStepGoalInteger)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            Intent startLoginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(startLoginIntent);
                        }, error -> binding.error.setText(error.getMessage()));
            } catch (InvalidFirstNameException e) {
                binding.error.setText(R.string.additional_data_invalid_first_name);
            } catch (InvalidLastNameException e) {
                binding.error.setText(R.string.additional_data_invalid_last_name);
            } catch (InvalidProfessionException e) {
                binding.error.setText(R.string.additional_data_invalid_profession);
            } catch (InvalidWeightException e) {
                binding.error.setText(R.string.additional_data_invalid_weight);
            } catch (InvalidHeightException e) {
                binding.error.setText(R.string.additional_data_invalid_height);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (registerUserDisposable != null) {
            registerUserDisposable.dispose();
        }
    }
}
