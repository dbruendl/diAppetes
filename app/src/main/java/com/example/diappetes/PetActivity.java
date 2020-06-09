package com.example.diappetes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.diappetes.info.InfoActivity;
import com.example.diappetes.main.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

public class PetActivity extends AppCompatActivity {
    int petstatus;
    int progress;
    ImageView petimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);
        //Menu
        Button homebtn3 = findViewById(R.id.homebtn);
        homebtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent starthomeintent3 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(starthomeintent3);
            }
        });
        Button infobtn3 = findViewById(R.id.infobtn);
        infobtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startinfointent3 = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(startinfointent3);
            }
        });
        Button statbtn3 = findViewById(R.id.statbtn);
        statbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startstatintent3 = new Intent(getApplicationContext(), StatActivity.class);
                startActivity(startstatintent3);
            }
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
