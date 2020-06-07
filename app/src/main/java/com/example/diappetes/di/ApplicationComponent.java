package com.example.diappetes.di;

import android.app.Application;

import com.example.diappetes.MainApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {ViewModelFactoryModule.class, ActivityBuildersModule.class, AndroidInjectionModule.class, DatabaseModule.class})
public interface ApplicationComponent extends AndroidInjector<MainApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }
}
