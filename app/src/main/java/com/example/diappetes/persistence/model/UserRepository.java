package com.example.diappetes.persistence.model;

import androidx.lifecycle.LiveData;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface UserRepository {
    Completable store(User user);
    Maybe<Boolean> exists(String uid);
    Maybe<User> findById(String uid);
}
