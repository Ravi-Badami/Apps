package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final String SCORE_KEY="SCORE";
    private final String INDEX_KEY="INDEX";


    private TextView mtextQuestion;
    private Button btnTrue;
    private Button btnWrong;
    private int mQuestionIndex;
    private int mQuizQuestion;
    private ProgressBar mProgressBar;
    private TextView mQuizStatesTextView;
    private int mUserScore;




    private QuizModule[] myQuestionCollection = new QuizModule[]{

            new QuizModule(R.string.q1,true),
            new QuizModule(R.string.q2,true),
            new QuizModule(R.string.q3,true),
            new QuizModule(R.string.q4,false),
            new QuizModule(R.string.q5,false),
            new QuizModule(R.string.q6,true),
            new QuizModule(R.string.q7,true),
            new QuizModule(R.string.q8,true),
            new QuizModule(R.string.q9,true),
            new QuizModule(R.string.q10,true),
    };

    //progress bar inti
    final int USER_PROGRESS =100/myQuestionCollection.length;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(getApplicationContext(), "Hey,Welcome", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState !=null){
            mUserScore=savedInstanceState.getInt(SCORE_KEY);
            mQuestionIndex=savedInstanceState.getInt(INDEX_KEY);
        }
        else{
            mQuestionIndex=0;
            mUserScore=0;
        }

        mtextQuestion=findViewById(R.id.txtquestion); //accessing the control of txtquestion button
        QuizModule q1= myQuestionCollection[mQuestionIndex]; //creating object

        mQuizQuestion = q1.getmQuestion(); //setting  the question

        mtextQuestion.setText(mQuizQuestion); //passing the question

         btnTrue=findViewById(R.id.btnTrue); //accessing the control of btnTrue button

         mProgressBar=findViewById(R.id.QuixPB); //accessing the control of QuizPB button
         mQuizStatesTextView=findViewById(R.id.txtQuizStats); //accessing the control of QuizStats button

         btnWrong=findViewById(R.id.btnWrong); //accessing the control of btnWrong button

        // telling what will happen if u press btnTrue
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluvateUsersAnswer(true);
                ChangeQuestionOnButtonClick();

            }
        });

        // telling what will happen if u press btnWrong
        btnWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               // Log.i("my_app", "wrong is pressd ");
//                Toast.makeText(getApplicationContext(), "btnWrong is tapped", Toast.LENGTH_LONG).show();
            evaluvateUsersAnswer(false);
                ChangeQuestionOnButtonClick();
            }
        });




    }
    //this will change the quesiton after pressing btnTrue or btnWrong
    private void ChangeQuestionOnButtonClick(){
        mQuestionIndex =(mQuestionIndex+1)%10; //incresing the index of question array

        //popup after finishing all the question
        if(mQuestionIndex==0){
            AlertDialog.Builder quizAlert = new AlertDialog.Builder(this); //creating object of the class
            quizAlert.setCancelable(false); // bc of this the alert wont able to be closed
            quizAlert.setTitle("the quiz is finished");
            quizAlert.setMessage("Your sccore is "+ mUserScore );
            quizAlert.setPositiveButton("finsh the quiz", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            quizAlert.show(); //this is very important coz with this the popup wont show

        }
        mQuizQuestion = myQuestionCollection[mQuestionIndex].getmQuestion();
           mtextQuestion.setText(mQuizQuestion);
           mProgressBar.incrementProgressBy(USER_PROGRESS);
           mQuizStatesTextView.setText(mUserScore + "");



}

//to get the input from the user
private void evaluvateUsersAnswer(boolean userGuess){
        boolean currentQuestionAnswer= myQuestionCollection[mQuestionIndex].ismAnswer();
        if (currentQuestionAnswer==userGuess){ //if he guessess correctly
        Toast.makeText(getApplicationContext(), R.string.correct_text, Toast.LENGTH_SHORT).show();
        mUserScore=mUserScore+1; // increasing the score of the user if he got it true

    }else //if he guesses wrong
            Toast.makeText(getApplicationContext(), R.string.incorrect_text, Toast.LENGTH_SHORT).show();


}

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Toast.makeText(getApplicationContext(), "Onstart is created", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Toast.makeText(getApplicationContext(), "Onrestart is created", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Toast.makeText(getApplicationContext(), "Onresume is created", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Toast.makeText(getApplicationContext(), "Onstop is created", Toast.LENGTH_SHORT).show();
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Toast.makeText(getApplicationContext(), "OnDestroy is created", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Toast.makeText(getApplicationContext(), "OnPause is created", Toast.LENGTH_SHORT).show();
//    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.getInt(SCORE_KEY,mUserScore);
        outState.getInt(INDEX_KEY,mQuestionIndex);
    }
}