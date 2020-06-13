package com.example.diappetes.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

    int mStackLevel = 1;

    public InfoFragment(@IdRes int fragmentContainerId) {
        this.fragmentContainerId = fragmentContainerId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = InfoLinksBinding.inflate(inflater, container, false);
        binding.generalMore.setOnClickListener(v-> toGeneralInformation());
                /*v ->
                getParentFragmentManager().beginTransaction()
                .replace(fragmentContainerId, generalInfoFragment)
                .commit());*/

        binding.strechingMore.setOnClickListener(v ->toStretchInformation()
                /*getParentFragmentManager().beginTransaction()
                        .replace(fragmentContainerId, stretchingInfoFragment)
                        .commit()*/);

        return binding.getRoot();
    }

    private void toGeneralInformation() {

        // Add the fragment to the activity, pushing this transaction
        // on to the back stack.
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.animator.fragment_slide_left_enter,
                R.animator.fragment_slide_left_exit,
                R.animator.fragment_slide_right_enter,
                R.animator.fragment_slide_right_exit);
        ft.replace(fragmentContainerId, stretchingInfoFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void toStretchInformation() {

        // Add the fragment to the activity, pushing this transaction
        // on to the back stack.
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.animator.fragment_slide_left_enter,
                R.animator.fragment_slide_left_exit,
                R.animator.fragment_slide_right_enter,
                R.animator.fragment_slide_right_exit);
        ft.replace(fragmentContainerId, generalInfoFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
