package com.example.diappetes.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diappetes.R;
import com.example.diappetes.databinding.InfoLinksBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class InfoFragment extends Fragment {
    @Inject
    public InfoViewModel infoViewModel;

    private final int fragmentContainerId;

    InfoLinksBinding binding;
    GeneralInfoFragment generalInfoFragment = new GeneralInfoFragment();
    StretchingInfoFragment stretchingInfoFragment = new StretchingInfoFragment();

    public InfoFragment(@IdRes int fragmentContainerId) {
        this.fragmentContainerId = fragmentContainerId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = InfoLinksBinding.inflate(inflater, container, false);

        binding.generalMore.setOnClickListener(v ->
                getParentFragmentManager().beginTransaction()
                .replace(fragmentContainerId, generalInfoFragment)
                .commit());

        binding.strechingMore.setOnClickListener(v ->
                getParentFragmentManager().beginTransaction()
                        .replace(fragmentContainerId, stretchingInfoFragment)
                        .commit());

        return binding.getRoot();
    }
}
