package com.example.diappetes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.diappetes.info.InfoActivity;

public class PetActivity extends AppCompatActivity {
    int petstatus;
    int progress;

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
        Button infobtn3 = (Button)findViewById(R.id.infobtn);
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
        //Toast.makeText(getApplicationContext(),Toasty,Toast.LENGTH_SHORT).show();

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

        String Toasty2="";
        switch (petstatus){
            case -2:
                Toasty2 = "Super Sad";
                break;
            case -1:
                Toasty2 = "Sad";
                break;
            case 0:
                Toasty2 = "Neutral";
                break;
            case 1:
                Toasty2 = "Happy";
                break;
            case 2:
                Toasty2 = "Super Happy";
                break;

        }
        Toast.makeText(getApplicationContext(),Toasty2,Toast.LENGTH_SHORT).show();

        ImageView petimage = findViewById(R.id.petimage);
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
