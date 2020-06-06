package com.example.diappetes.persistence.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "password")
    public String password;
}
