package com.example.diappetes.register;

import androidx.lifecycle.ViewModel;

import com.example.diappetes.persistence.model.UserRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.CompletableSubject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class RegisterViewModel extends ViewModel {

    private final UserRepository userRepository;

    private Disposable usernameAlreadyTakenDisposable;

    Completable usernameAlreadyTaken(String uid) {
        CompletableSubject completableSubject = CompletableSubject.create();

        usernameAlreadyTakenDisposable = userRepository.findByUid(uid)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        user -> completableSubject.onError(new UsernameAlreadyTakenException()),
                        error -> completableSubject.onComplete());

        return completableSubject;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if(usernameAlreadyTakenDisposable != null) {
            usernameAlreadyTakenDisposable.dispose();
        }
    }
}
