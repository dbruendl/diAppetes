package com.example.diappetes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diappetes.databinding.CreateReportBinding;
import com.example.diappetes.main.MainActivity;
import com.example.diappetes.main.MainViewModel;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@AndroidEntryPoint
public class CreateReportFragment extends Fragment {

    @Inject
    public MainViewModel mainViewModel;

    CreateReportBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CreateReportBinding.inflate(inflater, container, false);

        binding.saveReport.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            calendar.set(Calendar.DAY_OF_MONTH, binding.created.getDayOfMonth());
            calendar.set(Calendar.MONTH, binding.created.getMonth());
            calendar.set(Calendar.YEAR, binding.created.getYear());

            Date date = calendar.getTime();
            Integer steps = Integer.parseInt(binding.steps.getText().toString());

            Disposable disposable = mainViewModel.createReport(MainActivity.LOGGED_IN_USER_ID, date, steps)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> Log.i(getClass().getSimpleName(), "Report saved"), error -> Log.e(getClass().getSimpleName(), "Could not save report", error));
        });

        return binding.getRoot();
    }
}
