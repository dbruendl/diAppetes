package com.example.diappetes.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diappetes.databinding.StretchingInfoBinding;

public class StretchingInfoFragment extends Fragment {

    StretchingInfoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = StretchingInfoBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}
