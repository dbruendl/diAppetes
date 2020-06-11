package com.example.diappetes.observer;

import android.widget.ImageView;

import androidx.lifecycle.Observer;

import com.example.diappetes.R;
import com.example.diappetes.persistence.model.Report;

import lombok.Builder;

/**
 * Observes a percentage float value and updates a given images view with one of the following drawables
 * depending on the percentage
 * - progress between 0 - 99    ={@literal >} {@link R.drawable#happystatus}
 * - progress {@literal >}= 100 ={@literal >} {@link R.drawable#superhappystatus}
 * - progress {@literal <} 0    ={@literal >} {@link R.drawable#neutralstatus}
 *
 * Default image is {@link R.drawable#neutralstatus}
 */
@Builder
public class PetStepGoalObserver implements Observer<Report> {

    private final ImageView imageView;
    private final int stepGoal;

    public PetStepGoalObserver(ImageView imageView, int stepGoal) {
        this.imageView = imageView;
        this.stepGoal = stepGoal;

        imageView.setImageResource(R.drawable.neutralstatus);
    }

    @Override
    public void onChanged(Report report) {
        if(report == null) return;

        float progress = report.progress(stepGoal);
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
