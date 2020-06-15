package com.example.diappetes.login;

import io.reactivex.Completable;

public interface LoginService {
    Completable login(String uid, String password);

    Completable logout();

    boolean isLoggedIn();

    String getLoggedInUID();
}
