package com.example.fingerspeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView timertextView;
    private TextView AThousandTextView;
    private Button TapTapButton;
    private CountDownTimer countDownTimer;
    private long initalCountDownInMilli=60000;
    private int timerInterval=1000;
    private int remaningTime=60;
    private int aThousand=1000;

    private final String REMAINING_TIME_KEY="remaining time key";
    private final String A_THOUSAND_KEY="a thousand key";



    @Override
    protected void onDestroy() {
        super.onDestroy();
        showToast("On destory");

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        showToast("on save instance ");
        outState.putInt(REMAINING_TIME_KEY,remaningTime);
        outState.putInt(A_THOUSAND_KEY,aThousand);
        countDownTimer.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showToast("on create");
        setContentView(R.layout.activity_main);
        timertextView= findViewById(R.id.txtTimer);
        AThousandTextView=findViewById(R.id.txtAThousand);
        TapTapButton=findViewById(R.id.btnTap);

        AThousandTextView.setText(aThousand+"");

        if(savedInstanceState !=null){
            remaningTime=savedInstanceState.getInt(REMAINING_TIME_KEY);
            aThousand=savedInstanceState.getInt(A_THOUSAND_KEY);
            reStoreTheGame();
        }


        TapTapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aThousand--;
                AThousandTextView.setText(aThousand+"");
                if (remaningTime>0 && aThousand<=0){
                    Toast.makeText(MainActivity.this, "Congratulation", Toast.LENGTH_SHORT).show();
                    showAlert("Congratulation","Please reset the game ");

                }


            }
        });

        /**
         * for the first time
         */
        countDownTimer=new CountDownTimer(initalCountDownInMilli,timerInterval) {
            @Override
            public void onTick(long milliUntilFinished) {
                remaningTime=(int) milliUntilFinished/1000;
                timertextView.setText(remaningTime+" ");
                AThousandTextView.setText(aThousand+"");
                if (remaningTime>0 && aThousand<=0){
                    Toast.makeText(MainActivity.this, "Congratulation", Toast.LENGTH_SHORT).show();
                }
            }

            //when the timer reaches to 0
            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "countDown finish", Toast.LENGTH_SHORT).show();


                    showAlert("NOT intresting","would you like to play again?                                      ");



            }
        };
        countDownTimer.start();
    }

    private void reStoreTheGame() {
        int reStoreRemainingTime=remaningTime;
        int reStoreathousand=aThousand;

        timertextView.setText(reStoreRemainingTime+"");
        AThousandTextView.setText(reStoreathousand+"");

        countDownTimer= new CountDownTimer((long)remaningTime*1000,timerInterval) {
            @Override
            public void onTick(long l) {
                remaningTime=remaningTime/1000;
                timertextView.setText(remaningTime);



            }

            @Override
            public void onFinish() {
                showAlert("Finished","Would you like to reset the game");

            }
        };
        countDownTimer.start();

    }

    private void resetTheGame(){
        if(countDownTimer!=null){
            countDownTimer.cancel();
            countDownTimer=null;




        }

        aThousand=1000;
        remaningTime=60;
        AThousandTextView.setText(aThousand+"");
        timertextView.setText(remaningTime+"");


        countDownTimer= new CountDownTimer(initalCountDownInMilli,timerInterval) {
            @Override
            public void onTick(long millisToFinish) {
                remaningTime=(int)millisToFinish/1000;
                timertextView.setText(remaningTime+"");

            }

            @Override
            public void onFinish() {
                showAlert("Finished","Would you like to reset the game");

            }
        };
        countDownTimer.start();
    }


    private void showAlert(String title,String message){
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle( title)
                    .setMessage(message)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //set what would happen when positive button is clicked
                            resetTheGame();

                        }
                    }).show();
            alertDialog.setCancelable(false);

    }
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param menu this is for menu button on navigational bar
     *
     * @return true means ready to show on app
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.info_item)
            showToast(BuildConfig.VERSION_NAME);
        return true;
    }

}
