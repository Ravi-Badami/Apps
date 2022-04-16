package com.example.rolldice;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        Button btnRoll=findViewById(R.id.btnRollhtedice);
        final MediaPlayer mp = MediaPlayer.create(this,R.raw.dice_sound);
        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("mydiceApp", "button is clicked");

                Random rndObject= new Random();
                int randomNumber= rndObject.nextInt(6);
                int randomNumber2= rndObject.nextInt(6);

                Log.i("randomCheck",randomNumber+"");
                Log.i("randomCheck2",randomNumber2+"");


                ImageView diceImage1= findViewById(R.id.imgDice1);
                ImageView diceImage2= findViewById(R.id.imgDice2);

                int[] DiceImages={R.drawable.dice1,R.drawable.dice2,R.drawable.dice3,R.drawable.dice4,R.drawable.dice5,R.drawable.dice6};
                diceImage1.setImageResource(DiceImages[randomNumber]);
                diceImage2.setImageResource(DiceImages[randomNumber2]);
                YoYo.with(Techniques.FadeIn)
                        .duration(400)
                        .repeat(0)
                        .playOn(diceImage1);
                YoYo.with(Techniques.Shake)
                        .duration(400)
                        .repeat(0)
                        .playOn(diceImage2);


                mp.start();



            }
        });
    }
}