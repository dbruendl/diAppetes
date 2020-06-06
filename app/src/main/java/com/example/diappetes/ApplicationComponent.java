package com.example.diappetes;

import android.app.Application;

import com.example.diappetes.persistence.AppDatabase;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RoomModule.class, AppModule.class})
public interface ApplicationComponent {
    void inject(LoginActivity loginActivity);
    void inject(RegisterActivity registerActivity);

    AppDatabase appDatabase();

    Application application();
}
