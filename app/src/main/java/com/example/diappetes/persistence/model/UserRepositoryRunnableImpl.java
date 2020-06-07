package com.example.diappetes.persistence.model;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.subjects.CompletableSubject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UserRepositoryRunnableImpl implements UserRepository {

    private final UserDao userDao;

    @Inject
    public UserRepositoryRunnableImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Completable store(User user) {
        InsertUserRunnable insertUserRunnable = new InsertUserRunnable(userDao, user);

        new Thread(insertUserRunnable).start();

        return insertUserRunnable.completableSubject;
    }

    @Override
    public Maybe<User> findByEmail(String email) {
        return userDao.findByEmail(email);
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
