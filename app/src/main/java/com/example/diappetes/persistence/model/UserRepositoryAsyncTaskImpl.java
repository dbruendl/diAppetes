package com.example.diappetes.persistence.model;

import com.example.diappetes.persistence.AppDatabase;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.subjects.CompletableSubject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UserRepositoryAsyncTaskImpl implements UserRepository {

    private final UserDao userDao;
    private final AppDatabase appDatabase;

    @Inject
    public UserRepositoryAsyncTaskImpl(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
        this.userDao = appDatabase.userDao();
    }

    @Override
    public Maybe<Boolean> exists(String uid) {
        return userDao.findByUid(uid)
                .map(user -> Maybe.just(true))
                .cast(Boolean.class)
                .defaultIfEmpty(false);
    }

    @Override
    public Maybe<User> findById(String uid) {
        return userDao.findByUid(uid);
    }

    @Override
    public Completable store(User user) {
        InsertUserRunnable insertUserRunnable = new InsertUserRunnable(userDao, user);

        new Thread(insertUserRunnable).start();

        return insertUserRunnable.completableSubject;
    }

    @RequiredArgsConstructor
    private static class InsertUserRunnable implements Runnable {

        private final UserDao userDao;
        private final User user;

        @Getter
        private final CompletableSubject completableSubject = CompletableSubject.create();

        @Override
        public void run() {
            try {
                userDao.insert(user);
            } catch (Exception e) {
                completableSubject.onError(e);
            }
            completableSubject.onComplete();
        }
    }
}
