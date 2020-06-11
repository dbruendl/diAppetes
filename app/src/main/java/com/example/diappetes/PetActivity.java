package com.example.diappetes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.diappetes.databinding.ActivityPetBinding;
import com.example.diappetes.info.InfoActivity;
import com.example.diappetes.main.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

public class PetActivity extends AppCompatActivity {
    int petstatus;
    int progress;
    ImageView petimage;

    ActivityPetBinding activityPetBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPetBinding = ActivityPetBinding.inflate(getLayoutInflater());
        setContentView(activityPetBinding.getRoot());

        activityPetBinding.navigationView.setSelectedItemId(R.id.tab_pet);
        activityPetBinding.navigationView.setOnNavigationItemSelectedListener(item -> {
            Intent startIntent;

            switch (item.getItemId()) {
                case R.id.tab_info:
                    startIntent = new Intent(getApplicationContext(), InfoActivity.class);
                    break;
                case R.id.tab_home:
                    startIntent = new Intent(getApplicationContext(), MainActivity.class);
                    break;
                case R.id.tab_stat:
                    startIntent = new Intent(getApplicationContext(), StatActivity.class);
                    break;
                default:
                    return false;
            }

            startActivity(startIntent);

            return true;
        });

        /* PET CODE BELOW
             0 - neutral        nothing happens
             1 - happy          during exercise/ rest of the week after finishing exercise
             2 - super happy    exercise finished
            -1 - sad            deadline about to end
            -2 - super sad      didn't finish exercise during the week
        */

        String newString="0";

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= "0";
            } else {
                newString= extras.getString("STRING_I_NEED");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("STRING_I_NEED");
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

        petimage = findViewById(R.id.petimage);

        petimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                petstatus++;
                if (petstatus>2)
                    petstatus=2;

                switch (petstatus){
                    case 1:
                        petimage.setImageResource(R.drawable.happystatus);
                        break;
                    case 2:
                        petimage.setImageResource(R.drawable.superhappystatus);
                        break;
                    case 0:
                    default:
                        petimage.setImageResource(R.drawable.neutralstatus);
                        break;
                }

                new CountDownTimer(700, 1000) { // 1000 = 1 sec

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        petstatus--;
                        switch (petstatus){
                            case 1:
                                petimage.setImageResource(R.drawable.happystatus);
                                break;
                            case 2:
                                petimage.setImageResource(R.drawable.superhappystatus);
                                break;
                            case 0:
                            default:
                                petimage.setImageResource(R.drawable.neutralstatus);
                                break;
                        }
                    }
                }.start();

                //Toast.makeText(getApplicationContext(),"Toasty",Toast.LENGTH_SHORT).show();
            }
        });

        switch (petstatus){
            case 1:
                petimage.setImageResource(R.drawable.happystatus);
                break;
            case 2:
                petimage.setImageResource(R.drawable.superhappystatus);
                break;
            case 0:
            default:
                petimage.setImageResource(R.drawable.neutralstatus);
                break;
        }

    }
}
