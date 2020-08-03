package com.example.jaroslav.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;


public class Options extends AppCompatActivity {

    SharedPreferences sharedPref;
    SeekBar sliderSpeedSnake;
    TextView textSpeedSnake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_options);
        sharedPref = getSharedPreferences(getString(R.string.speed_snake), Context.MODE_PRIVATE);
        int defaultSpeed = getResources().getInteger(R.integer.default_speed);
        final int speed = sharedPref.getInt("speed", defaultSpeed);

        sliderSpeedSnake = findViewById(R.id.slider_speed_snake);
        textSpeedSnake = findViewById(R.id.text_speed_snake);
        sliderSpeedSnake.setProgress(speed);
        textSpeedSnake.setText(String.valueOf(speed+1));

        sliderSpeedSnake.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textSpeedSnake.setText(String.valueOf(i+1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.speed_snake), sliderSpeedSnake.getProgress());
        editor.apply();
    }
}