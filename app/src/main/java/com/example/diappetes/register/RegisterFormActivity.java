package com.example.diappetes.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.diappetes.databinding.ActivityRegisterFormBinding;
import com.example.diappetes.login.LoginActivity;
import com.example.diappetes.R;

public class RegisterFormActivity extends AppCompatActivity {

    private ActivityRegisterFormBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nextbtn.setOnClickListener(v -> {
            Intent startLoginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(startLoginIntent);
        });
    }
}
