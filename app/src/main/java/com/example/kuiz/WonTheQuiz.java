package com.example.kuiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WonTheQuiz extends AppCompatActivity {
Button playag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won_the_quiz);
        playag = findViewById(R.id.playag);


        //This is onclick listener for button
        //it will navigate from this activity to MainGameActivity
        playag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WonTheQuiz.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}