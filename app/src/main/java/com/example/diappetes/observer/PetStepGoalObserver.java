package com.example.diappetes.observer;

import android.widget.ImageView;

import androidx.lifecycle.Observer;

import com.example.diappetes.R;

import lombok.Builder;

@Builder
public class PetStepGoalObserver implements Observer<Float> {

    private final ImageView imageView;

    public PetStepGoalObserver(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void onChanged(Float progress) {
        float progressInPercentage = progress * 100;

        if(progressInPercentage > 0 && progressInPercentage < 100) {
            imageView.setImageResource(R.drawable.happystatus);
        } else if(progressInPercentage >= 100) {
            imageView.setImageResource(R.drawable.superhappystatus);
        } else {
            imageView.setImageResource(R.drawable.neutralstatus);
        }
    }
}
