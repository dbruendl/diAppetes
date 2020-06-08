package com.example.diappetes.info;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diappetes.main.MainActivity;
import com.example.diappetes.PetActivity;
import com.example.diappetes.R;
import com.example.diappetes.StatActivity;
import com.example.diappetes.ViewModelProviderFactory;
import com.example.diappetes.databinding.ActivityInfoBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class InfoActivity extends DaggerAppCompatActivity {
    RecyclerView.Adapter userAdapter;
    RecyclerView.LayoutManager userLayoutManager;

    ActivityInfoBinding activityInfoBinding;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    private InfoViewModel infoViewModel;
    private Disposable findAllUsersDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityInfoBinding = ActivityInfoBinding.inflate(getLayoutInflater());
        setContentView(activityInfoBinding.getRoot());

        infoViewModel = new ViewModelProvider(this, viewModelProviderFactory).get(InfoViewModel.class);

        activityInfoBinding.userRecyclerView.setHasFixedSize(true);

        userLayoutManager = new LinearLayoutManager(this);
        activityInfoBinding.userRecyclerView.setLayoutManager(userLayoutManager);

        findAllUsersDisposable = infoViewModel.findAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> {
                    userAdapter = new UserListAdapter(users);
                    activityInfoBinding.userRecyclerView.setAdapter(userAdapter);
                });

        //TIME
        DateFormat datetime = new SimpleDateFormat("yyyy MM HH:mm:ss");
        String date = datetime.format(Calendar.getInstance().getTime());

        TextView timetxt = findViewById(R.id.timetxt);
        timetxt.setText(date);
        //TIME

        Button homebtn2 = findViewById(R.id.homebtn);
        homebtn2.setOnClickListener(v -> {
            Intent starthomeintent2 = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(starthomeintent2);
        });

        Button petbtn2 = findViewById(R.id.petbtn);
        petbtn2.setOnClickListener(v -> {
            Intent startpetintent2 = new Intent(getApplicationContext(), PetActivity.class);
            startActivity(startpetintent2);
        });

        Button statbtn2 = findViewById(R.id.statbtn);
        statbtn2.setOnClickListener(v -> {
            Intent startstatintent2 = new Intent(getApplicationContext(), StatActivity.class);
            startActivity(startstatintent2);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(findAllUsersDisposable != null) {
            findAllUsersDisposable.dispose();
        }
    }
}
