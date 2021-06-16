package com.example.circularprogressbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircularSeekbar circularSeekbar = findViewById(R.id.seekBar);
        circularSeekbar.setOnProgress(50);
        circularSeekbar.setState("red");
        circularSeekbar.setBiggerTxt("50");
        circularSeekbar.setSmallerTxt("گیگ");

//        Progressing progressing = new Progressing();
//        progressing.setProgressListener(circularSeekbar::setOnProgress);
//        progressing.start();
    }


}