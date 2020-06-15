package com.example.diappetes.login;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.Completable;

public class LoginViewModel extends ViewModel {
    private final LoginService loginService;

    @ViewModelInject
    public LoginViewModel(LoginService loginService) {
        this.loginService = loginService;
    }

    Completable login(String uid, String password) {
        return loginService.login(uid, password);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
