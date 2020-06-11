package com.example.diappetes.di;

import com.example.diappetes.info.InfoActivity;
import com.example.diappetes.login.LoginActivity;
import com.example.diappetes.register.AdditionalDataActivity;
import com.example.diappetes.register.RegisterActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(
            modules = {ViewModelModule.class}
    )
    abstract RegisterActivity contributeRegisterActivity();

    @ContributesAndroidInjector(
            modules = {ViewModelModule.class}
    )
    abstract LoginActivity contributeLoginActivity();

    @ContributesAndroidInjector(
            modules = {ViewModelModule.class}
    )
    abstract InfoActivity contributeInfoActivity();

    @ContributesAndroidInjector(
            modules = {ViewModelModule.class}
    )
    abstract AdditionalDataActivity contributeAdditionalDataActivity();


}