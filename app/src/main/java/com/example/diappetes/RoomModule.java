package com.example.diappetes;

import android.app.Application;

import androidx.room.Room;

import com.example.diappetes.persistence.AppDatabase;
import com.example.diappetes.persistence.model.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {
    private AppDatabase appDatabase;

    public RoomModule(Application application) {
        appDatabase = Room.databaseBuilder(application, AppDatabase.class, "database").build();
    }

    @Singleton
    @Provides
    AppDatabase appDatabase() {
        return appDatabase;
    }

    @Singleton
    @Provides
    UserDao userDao(AppDatabase appDatabase) {
        return appDatabase.userDao();
    }
}
