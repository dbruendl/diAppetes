package com.example.diappetes.persistence.model;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public interface UserRepository {
    Completable store(User user);
    Maybe<User> findByEmail(String email);
}
