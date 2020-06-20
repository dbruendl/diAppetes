package com.example.diappetes.info;

import androidx.lifecycle.ViewModel;

import com.example.diappetes.persistence.model.User;
import com.example.diappetes.persistence.model.UserRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class InfoViewModel extends ViewModel {
    private final UserRepository userRepository;

    @Inject
    public InfoViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
