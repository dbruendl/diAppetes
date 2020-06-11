package com.example.diappetes.observer;

import androidx.lifecycle.Observer;

import com.example.diappetes.persistence.model.Report;

public class TestObserver implements Observer<Report> {
    @Override
    public void onChanged(Report report) {
        return;
    }
}
