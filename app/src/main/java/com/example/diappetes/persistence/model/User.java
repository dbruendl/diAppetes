package com.example.diappetes.persistence.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @NonNull
    @PrimaryKey
    public String uid;

    @NonNull
    public String email;

    @NonNull
    public String password;

    @NonNull
    @ColumnInfo(name = "first_name")
    public String firstName;

    @NonNull
    @ColumnInfo(name = "last_name")
    public String lastName;

    @NonNull
    public String profession;

    @NonNull
    public Double weight;

    @NonNull
    public Double height;

    @NonNull
    @ColumnInfo(name = "daily_step_goal")
    public int dailyStepGoal;

    public User(@NonNull String uid,
                @NonNull String email,
                @NonNull String password,
                @NonNull String firstName,
                @NonNull String lastName,
                @NonNull String profession,
                @NonNull Double weight,
                @NonNull Double height,
                int dailyStepGoal) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profession = profession;
        this.weight = weight;
        this.height = height;
        this.dailyStepGoal = dailyStepGoal;
    }
}
