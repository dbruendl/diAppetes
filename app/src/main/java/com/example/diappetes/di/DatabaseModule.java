package com.example.diappetes.di;

import android.app.Application;

import androidx.room.Room;

import com.example.diappetes.persistence.AppDatabase;
import com.example.diappetes.persistence.model.UserDao;
import com.example.diappetes.persistence.model.UserRepository;
import com.example.diappetes.persistence.model.UserRepositoryAsyncTaskImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    @Provides
    @Singleton
    AppDatabase appDatabase(Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, "database")
                .build();
    }

    @Singleton
    @Provides
    UserDao userDao(AppDatabase appDatabase) {
        return appDatabase.userDao();
    }

    @Singleton
    @Provides
    UserRepository userRepository(AppDatabase appDatabase) {
        return new UserRepositoryAsyncTaskImpl(appDatabase);
    }
}
