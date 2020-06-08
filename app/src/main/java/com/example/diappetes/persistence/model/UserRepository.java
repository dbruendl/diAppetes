package com.example.diappetes.persistence.model;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface UserRepository {
    Completable store(User user);
    Single<User> findByUid(String uid);
}
