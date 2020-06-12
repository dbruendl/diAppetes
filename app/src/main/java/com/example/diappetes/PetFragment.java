package com.example.diappetes;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diappetes.databinding.PetFragmentBinding;

public class PetFragment extends Fragment {

    private static final String STRING_I_NEED = "STRING_I_NEED";
    int petstatus;
    int progress;

    PetFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PetFragmentBinding.inflate(inflater, container, false);

        /* PET CODE BELOW
             0 - neutral        nothing happens
             1 - happy          during exercise/ rest of the week after finishing exercise
             2 - super happy    exercise finished
            -1 - sad            deadline about to end
            -2 - super sad      didn't finish exercise during the week
        */

        String newString = "0";

        if (savedInstanceState != null) {
            newString = (String) savedInstanceState.getSerializable(STRING_I_NEED);
        }

        String Toasty = "Progress= " + newString;

        progress = Integer.parseInt(newString);
        petstatus=0;

        if(progress>0){
            if(progress<100){ //0 - 100  During exercise
                petstatus = 1;
            } else { //100 - Exercise is finished
                petstatus = 2;
            }
        } else { //0 Exercise not started
            petstatus = 0;
        }

        binding.petimage.setOnClickListener(v -> {
            petstatus++;
            if (petstatus>2)
                petstatus=2;

            switch (petstatus){
                case 1:
                    binding.petimage.setImageResource(R.drawable.happystatus);
                    break;
                case 2:
                    binding.petimage.setImageResource(R.drawable.superhappystatus);
                    break;
                case 0:
                default:
                    binding.petimage.setImageResource(R.drawable.neutralstatus);
                    break;
            }

            new CountDownTimer(700, 1000) { // 1000 = 1 sec

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    petstatus--;
                    switch (petstatus){
                        case 1:
                            binding.petimage.setImageResource(R.drawable.happystatus);
                            break;
                        case 2:
                            binding.petimage.setImageResource(R.drawable.superhappystatus);
                            break;
                        case 0:
                        default:
                            binding.petimage.setImageResource(R.drawable.neutralstatus);
                            break;
                    }
                }
            }.start();
        });

        switch (petstatus){
            case 1:
                binding.petimage.setImageResource(R.drawable.happystatus);
                break;
            case 2:
                binding.petimage.setImageResource(R.drawable.superhappystatus);
                break;
            case 0:
            default:
                binding.petimage.setImageResource(R.drawable.neutralstatus);
                break;
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
