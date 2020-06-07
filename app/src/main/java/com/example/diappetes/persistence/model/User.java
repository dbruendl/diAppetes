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

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "password")
    public String password;

    public User(@NonNull String uid, String email, String password) {
        this.uid = uid;
        this.email = email;
        this.password = password;
    }
}
