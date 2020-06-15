package com.example.diappetes.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.diappetes.PetFragment;
import com.example.diappetes.R;
import com.example.diappetes.StatisticsFragment;
import com.example.diappetes.databinding.BottomNavigationSheetBinding;
import com.example.diappetes.info.InfoFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class NavigationDrawerReplaceInContainerFragment extends BottomSheetDialogFragment {
    private BottomNavigationSheetBinding binding;

    private final int fragmentContainerId;
    private final PetFragment petFragment;
    private final InfoFragment infoFragment;
    private final StepTrackingFragment stepTrackingFragment;
    private final StatisticsFragment statisticsFragment;

    public NavigationDrawerReplaceInContainerFragment(@IdRes int fragmentContainerId,
                                                      PetFragment petFragment,
                                                      InfoFragment infoFragment,
                                                      StepTrackingFragment stepTrackingFragment,
                                                      StatisticsFragment statisticsFragment) {
        this.fragmentContainerId = fragmentContainerId;
        this.petFragment = petFragment;
        this.infoFragment = infoFragment;
        this.stepTrackingFragment = stepTrackingFragment;
        this.statisticsFragment = statisticsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomNavigationSheetBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        binding.navigationView.setNavigationItemSelectedListener(item -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            switch (item.getItemId()) {
                case R.id.tab_home:
                    fragmentManager.beginTransaction()
                            .replace(fragmentContainerId, stepTrackingFragment)
                            .commit();
                    return true;
                case R.id.tab_info:
                    fragmentManager.beginTransaction()
                            .replace(fragmentContainerId, infoFragment)
                            .commit();
                    return true;
                case R.id.tab_pet:
                    fragmentManager.beginTransaction()
                            .replace(fragmentContainerId, petFragment)
                            .commit();
                    return true;
                case R.id.tab_stat:
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, statisticsFragment)
                            .commit();
                    return true;
                default:
                    return false;
            }
        });
        super.onActivityCreated(savedInstanceState);
    }


}
