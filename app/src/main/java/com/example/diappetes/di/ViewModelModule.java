package com.example.diappetes.di;

import androidx.lifecycle.ViewModel;

import com.example.diappetes.info.InfoViewModel;
import com.example.diappetes.login.LoginViewModel;
import com.example.diappetes.register.AdditionalDataViewModel;
import com.example.diappetes.register.RegisterViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel.class)
    public abstract ViewModel bindRegisterViewModel(RegisterViewModel registerViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    public abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(InfoViewModel.class)
    public abstract ViewModel bindInfoViewModel(InfoViewModel infoViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AdditionalDataViewModel.class)
    public abstract ViewModel bindAdditionalDataViewModel(AdditionalDataViewModel infoViewModel);


}
