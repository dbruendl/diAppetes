package com.example.diappetes.persistence.model;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class UserRepositoryRunnableImpl implements UserRepository {

    private final UserDao userDao;

    @Inject
    public UserRepositoryRunnableImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Completable store(User user) {
        return userDao.insert(user);
    }

    @Override
    public Single<User> findByUid(String uid) {
        return userDao.findByUid(uid);
    }

    @Override
    public Single<List<User>> findAll() {
        return Single.create((emitter) -> {
            emitter.onSuccess(userDao.getAll());
        });
    }
}
