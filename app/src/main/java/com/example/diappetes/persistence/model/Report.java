package com.example.diappetes.persistence.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.diappetes.DateUtils;

import java.util.Date;

@Entity
public class Report {
    @PrimaryKey
    public Integer id;
    public Date created;
    public Integer steps;
    public String userId;

    public Report(String userId) {
        this.userId = userId;
        this.created = new Date();
        this.steps = 0;
    }

    boolean isFromToday() {
        return DateUtils.isToday(created);
    }

    public float progress(int stepGoal) {
        return (float) steps / stepGoal;
    }
}
