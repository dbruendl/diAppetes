package com.example.diappetes.di;

import android.app.Application;

import androidx.room.Room;

import com.example.diappetes.persistence.AppDatabase;
import com.example.diappetes.persistence.Migrations;
import com.example.diappetes.persistence.model.UserDao;
import com.example.diappetes.persistence.model.UserRepository;
import com.example.diappetes.persistence.model.UserRepositoryRunnableImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DatabaseModule {

    @Provides
    public static AppDatabase appDatabase(Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, "database")
                .addMigrations(Migrations.MIGRATIONS)
                .build();
    }

    @Provides
    @Singleton
    public static UserDao userDao(AppDatabase appDatabase) {
        return appDatabase.userDao();
    }

    @Provides
    @Singleton
    public static UserRepository userRepository(UserDao userDao) {
        return new UserRepositoryRunnableImpl(userDao);
    }
}
