package com.example.diappetes.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.diappetes.persistence.model.User;
import com.example.diappetes.persistence.model.UserDao;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
