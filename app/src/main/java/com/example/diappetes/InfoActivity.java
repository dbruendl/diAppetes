package com.example.diappetes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diappetes.databinding.ActivityInfoBinding;
import com.example.diappetes.info.InfoViewModel;
import com.example.diappetes.info.UserListAdapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityInfoBinding = ActivityInfoBinding.inflate(getLayoutInflater());
        setContentView(activityInfoBinding.getRoot());

        infoViewModel = new ViewModelProvider(this, viewModelProviderFactory).get(InfoViewModel.class);

        activityInfoBinding.userRecyclerView.setHasFixedSize(true);

        userLayoutManager = new LinearLayoutManager(this);
        activityInfoBinding.userRecyclerView.setLayoutManager(userLayoutManager);

        Disposable disposable = infoViewModel.findAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> {
                    userAdapter = new UserListAdapter(users);
                    activityInfoBinding.userRecyclerView.setAdapter(userAdapter);
                });
//        dataBaseHelper = new DatabaseSLite(InfoActivity.this);
//        UpdateList(dataBaseHelper);

        //TIME
        DateFormat datetime = new SimpleDateFormat("yyyy MM HH:mm:ss");
        String date = datetime.format(Calendar.getInstance().getTime());

        TextView timetxt = findViewById(R.id.timetxt);
        timetxt.setText(date);
        //TIME

        Button homebtn2 = findViewById(R.id.homebtn);
        homebtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent starthomeintent2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(starthomeintent2);
            }
        });

        Button petbtn2 = findViewById(R.id.petbtn);
        petbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startpetintent2 = new Intent(getApplicationContext(), PetActivity.class);
                startActivity(startpetintent2);
            }
        });

        Button statbtn2 = findViewById(R.id.statbtn);
        statbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startstatintent2 = new Intent(getApplicationContext(), StatActivity.class);
                startActivity(startstatintent2);
            }
        });
    }
}
