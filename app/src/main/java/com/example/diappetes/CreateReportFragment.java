package com.example.diappetes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diappetes.databinding.CreateReportBinding;
import com.example.diappetes.persistence.model.Report;
import com.example.diappetes.persistence.model.UserRepository;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@AndroidEntryPoint
public class CreateReportFragment extends Fragment {

    @Inject
    public UserRepository userRepository;

    private final String loggedInUID;

    private Disposable reportDisposable;

    CreateReportBinding binding;

    public CreateReportFragment(String loggedInUID) {
        this.loggedInUID = loggedInUID;
    }

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

            reportDisposable = userRepository.findReportFor(loggedInUID, date)
                    .subscribeOn(Schedulers.io())
                    .subscribe(report -> {
                        report.steps = steps;

                        userRepository.updateReport(report);
                    }, throwable -> {
                        Report report = new Report(loggedInUID);

                        report.created = date;
                        report.steps = steps;

                        userRepository.insertReport(report);
                    });
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (reportDisposable != null) {
            reportDisposable.dispose();
        }
    }
}
