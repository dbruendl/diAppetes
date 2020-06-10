package com.example.diappetes.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.diappetes.persistence.model.Report;
import com.example.diappetes.persistence.model.User;
import com.example.diappetes.persistence.model.UserDao;

@Database(entities = {User.class, Report.class}, version = 1)
@TypeConverters(DateTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
