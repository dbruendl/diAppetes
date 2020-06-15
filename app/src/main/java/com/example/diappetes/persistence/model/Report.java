package com.example.diappetes.persistence.model;

import androidx.room.Entity;
import androidx.room.Ignore;
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
        this.steps = 0;
        this.userId = userId;
        this.created = new Date();
    }

    boolean isFromToday() {
        return DateUtils.isToday(created);
    }

    public float progress(int stepGoal) {
        return (float) steps / stepGoal;
    }
}
