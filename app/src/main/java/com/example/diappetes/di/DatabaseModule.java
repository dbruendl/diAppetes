package com.example.diappetes.di;

import android.app.Application;

import androidx.room.Room;
import androidx.room.migration.Migration;

import com.example.diappetes.persistence.AppDatabase;
import com.example.diappetes.persistence.Migrations;
import com.example.diappetes.persistence.model.UserDao;
import com.example.diappetes.persistence.model.UserRepository;
import com.example.diappetes.persistence.model.UserRepositoryRunnableImpl;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    @Provides
    @Singleton
    AppDatabase appDatabase(Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, "database")
                .addMigrations(Migrations.MIGRATIONS)
                .build();
    }

    @Singleton
    @Provides
    UserDao userDao(AppDatabase appDatabase) {
        return appDatabase.userDao();
    }

    @Singleton
    @Provides
    UserRepository userRepository(UserDao userDao) {
        return new UserRepositoryRunnableImpl(userDao);
    }
}
