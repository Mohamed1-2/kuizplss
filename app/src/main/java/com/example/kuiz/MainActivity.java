package com.example.kuiz;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ////////
    MediaPlayer wrongsong,rightsong,wonsong;
    Timer timer;
    ///////
    Button bA, bB, bC, bD;
    TextView questionText, Quiz_Text, time_Text, coin_Text,textviewQuestioncount;
    KuaizQuestionsHelper KuizHelper;
    KuaizQuestions currentQuestion;
    List<KuaizQuestions> list;


    int questioncount;
    int questioncounttotal;
private boolean answered;
    ///////////
    int question_id = 0;
    int time = 30;
    int coin_value = 0;
    //////
    private ColorStateList textcolordefultbt;
    private ColorStateList textcolordefultcd;
    ////
    CountDownTimer countTimer;
    ////
    Typeface tb, sb;















    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing variables
        questionText = (TextView) findViewById(R.id.quizeQuestion);
        bA = (Button) findViewById(R.id.bt1);
        bB = (Button) findViewById(R.id.bt2);
        bC = (Button) findViewById(R.id.bt3);
        bD = (Button) findViewById(R.id.bt4);
        textviewQuestioncount=findViewById(R.id.textviewQcount);
        Quiz_Text = (TextView) findViewById(R.id.appname);
        time_Text = (TextView) findViewById(R.id.timeText);
        coin_Text = (TextView) findViewById(R.id.coinText);



        tb = Typeface.createFromAsset(getAssets(), "fonts/Amaranth.otf");
        sb = Typeface.createFromAsset(getAssets(), "fonts/Amaranth.otf");
        Quiz_Text.setTypeface(sb);
        questionText.setTypeface(tb);
        bA.setTypeface(tb);
        bB.setTypeface(tb);
        bC.setTypeface(tb);
        bD.setTypeface(tb);
        time_Text.setTypeface(tb);
        coin_Text.setTypeface(tb);
        textcolordefultcd= time_Text.getTextColors(); //get timetext defult color
        //Our database helper class
        KuizHelper = new KuaizQuestionsHelper(this);
        //Make db writable
        KuizHelper.getWritableDatabase();




        //It will check if the ques,options are already added in table or not
        //If they are not added then the getAllOfTheQuestions() will return a list of size zero
        if (KuizHelper.getAllOfTheQuestions().size() == 0) {
            //If not added then add the ques,options in table
            KuizHelper.allQuestion();
        }

        //This will return us a list of data type TriviaQuestion
        list = KuizHelper.getAllOfTheQuestions();
        questioncounttotal = list.size();

        //Now we gonna shuffle the elements of the list so that we will get questions randomly
        Collections.shuffle(list);

        //currentQuestion for  the questions and 4 option and answers for spacific id
        currentQuestion = list.get(question_id);



        //countDownTimer
        countTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                //here you can have your logic to set text to timeText


              time_Text.setText(String.valueOf(time) + "\"");
                 time -= 1;
                if (time<10){
                    time_Text.setTextColor(Color.RED);



                    if (time == -1) {



                        //Since user is out of time setText as time up

                        //Since user is out of time he won't be able to click any buttons
                        //therefore we will disable all four options buttons using this method
                        disableButton();
                    }



                }else {


//set the time text to defult color
                    time_Text.setTextColor(textcolordefultcd);
                }
                //This means the user is out of time so onFinished will called after this iteration

            }

            //Now user is out of time
            public void onFinish() {

                //We will navigate him to the time up activity using below method

                        Intent intent = new Intent(MainActivity.this,
                                TimeUp.class);
                        startActivity(intent);
                        finish();



            }
        }.start();


        //This method will set the Questions and four options
        updateQueAndOptions();

    }




    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
    }



    public void updateQueAndOptions() {

        //This method will setText for que and options
        questionText.setText(currentQuestion.getQuestion());
        bA.setText(currentQuestion.getOptionA());
        bB.setText(currentQuestion.getOptionB());
        bC.setText(currentQuestion.getOptionC());
        bD.setText(currentQuestion.getOptionD());


        time = 30;

        //////////
        questioncount++;
        textviewQuestioncount.setText(+questioncount+"/"+questioncounttotal);

        //Now since the user has ans correct just reset timer back for another que- by cancel and start
        countTimer.cancel();
        countTimer.start();

        //set the value of coin text
        coin_Text.setText(String.valueOf(coin_value));
        //Now since user has ans correct increment the coinvalue
        coin_value++;

    }

    //Onclick listener for first button
    public void buttonA(View view) {
        //compare the option with the ans if yes then make button color green
        if (currentQuestion.getOptionA().equals(currentQuestion.getAnswer())) {
            bA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.lightGreen));
            //Check if user has not exceeds the que limit
            if (question_id < list.size() - 1) {

                //Now disable all the option button since user ans is correct so
                //user won't be able to press another option button after pressing one button
                disableButton();

                //Show the dialog that ans is correct
                correctDialog();
            }
            //If user has exceeds the questions limit then take him to gameWon activity
            else {

                gameWon();

            }
        }
        //if User answer is wrong then just take him to the PlayAgain activity
        else {
            bA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.red));

            PlayAgaingameEnd();

        }
    }

    //Onclick listener for sec button
    public void buttonB(View view) {
        if (currentQuestion.getOptionB().equals(currentQuestion.getAnswer())) {
            bB.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.lightGreen));
            if (question_id < list.size() - 1) {
                disableButton();
                correctDialog();
            } else {
                gameWon();
            }
        } else {
            bB.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.red));

            PlayAgaingameEnd();
        }
    }

    //Onclick listener for third button
    public void buttonC(View view) {
        if (currentQuestion.getOptionC().equals(currentQuestion.getAnswer())) {
            bC.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.lightGreen));
            if (question_id < list.size() - 1) {
                disableButton();
                correctDialog();
            } else {
                gameWon();
            }
        } else {
            bC.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.red));

            PlayAgaingameEnd();
        }
    }

    //Onclick listener for fourth button
    public void buttonD(View view) {
        if (currentQuestion.getOptionD().equals(currentQuestion.getAnswer())) {
            bD.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.lightGreen));
            if (question_id < list.size() - 1) {
                disableButton();
                correctDialog();
            } else {
                gameWon();
            }
        } else {
            bD.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.red));

            PlayAgaingameEnd();
        }
    }

    //This method will navigate from current activity to GameWon
    public void gameWon() {
wonsong = MediaPlayer.create(MainActivity.this,R.raw.won);
        wonsong.start();
        timer = new Timer();
        Intent intent = new Intent(MainActivity.this, WonTheQuiz.class);
        startActivity(intent);
        finish();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (wonsong != null){
                    wonsong.stop();

                }

            }
        },4000);


    }

    //This method is called when user ans is wrong
    //this method will take the user to the  PlayAgain activity
    public void PlayAgaingameEnd() {
        wrongsong= MediaPlayer.create(MainActivity.this,R.raw.buz);
        wrongsong.start();



        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if (wrongsong != null) {
                    wrongsong.stop();
                }

            }
        },2000);

        Intent intent = new Intent(MainActivity.this, wrongAnswer_play_again.class);
        startActivity(intent);
        finish();


    }


    //If user press home button and come in the game from memory then this
    //method will continue the timer from the previous time it left
    @Override
    protected void onRestart() {
        super.onRestart();
        countTimer.start();

    }

    //if activity is destroyed then this will cancel the timer
    @Override
    protected void onStop() {
        super.onStop();
        countTimer.cancel();
    }

    //This will pause the time
    @Override
    protected void onPause() {
        super.onPause();

        countTimer.cancel();

    }

    //On BackPressed
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this,MainScreen.class);
        startActivity(intent);
        finish();
    }

    //This dialog is show to the user after he ans correct
    public void correctDialog() {

        final Dialog dialogCorrect = new Dialog(MainActivity.this);
        dialogCorrect.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogCorrect.getWindow() != null) {
            ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
            dialogCorrect.getWindow().setBackgroundDrawable(colorDrawable);

        }

        if ( rightsong == null ){
            rightsong= MediaPlayer.create(MainActivity.this,R.raw.answcorr);


        }

        rightsong.start();
        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (rightsong != null){
                    rightsong.stop();

                }

            }
        },2000);


        dialogCorrect.setContentView(R.layout.answer_correct);
        dialogCorrect.setCancelable(false);
        dialogCorrect.show();



        onPause();


        TextView correctans = (TextView) dialogCorrect.findViewById(R.id.yescorrect);
        Button Next = (Button) dialogCorrect.findViewById(R.id.Next);

        //Setting type faces
        correctans.setTypeface(sb);
        Next.setTypeface(sb);

        //OnCLick listener to go next que
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCorrect.dismiss();
                //it will increment the question number
                question_id++;
                //get the que and 4 option and store in the currentQuestion
                currentQuestion = list.get(question_id);
                //Now this method will set the new que and 4 options
                updateQueAndOptions();
                //reset the color of buttons back to white
                resetColor();
                //Enable button - remember we had disable them when user ans was correct in there particular button methods
                enableButton();

            }
        });
    }


    //This method will make button color white again since our one button color was turned green
    public void resetColor() {
        bA.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttom_cos));
        bB.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttom_cos));
        bC.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttom_cos));
        bD.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttom_cos));
    }

    //This method will disable all the option button
  public void disableButton() {
        bA.setEnabled(false);
        bB.setEnabled(false);
        bC.setEnabled(false);
        bD.setEnabled(false);
    }

    //This method will all enable the option buttons
    public void enableButton() {
        bA.setEnabled(true);
        bB.setEnabled(true);
        bC.setEnabled(true);
        bD.setEnabled(true);
    }


}
