package com.example.kuiz;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainScreen extends AppCompatActivity {

    Button play,End;
    TextView startQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        //the below method will initialize views
        initViews();

        //PlayGame button - it will take you to the MainGameActivity
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Quit button - This will quit the game
        End.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initViews() {
        //initialize views here
        play =(Button)findViewById(R.id.play);
        End = (Button) findViewById(R.id.End);
        startQuiz = (TextView)findViewById(R.id.startQuiz);

        //Typeface - this is for fonts style
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Amaranth.otf");
        play.setTypeface(typeface);
        End.setTypeface(typeface);
        startQuiz.setTypeface(typeface);
    }
}