package com.example.diappetes.login;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.Completable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class LoginViewModel extends ViewModel {
    private final LoginService loginService;

    Completable login(String uid, String password) {
        return loginService.login(uid, password);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
