package com.example.diappetes.di;

import android.content.Context;

import dagger.BindsInstance;
import dagger.hilt.DefineComponent;
import dagger.hilt.android.components.ApplicationComponent;

@DefineComponent(parent = ApplicationComponent.class)
public interface AppComponent {

    @DefineComponent.Builder
    interface Builder {
        @BindsInstance
        Builder create(Context context);

        AppComponent build();
    }
}
