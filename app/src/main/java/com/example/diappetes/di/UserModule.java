package com.example.diappetes.di;

import com.example.diappetes.login.LoginService;
import com.example.diappetes.login.LoginServiceImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public abstract class UserModule {
    @Binds
    @Singleton
    public abstract LoginService bindLoginService(LoginServiceImpl loginService);
}
