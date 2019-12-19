package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controllerButton;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void resetTimer() {
        timerSeekBar.setEnabled(true);
        controllerButton.setText("GO!");
        countDownTimer.cancel();
        counterIsActive = false;
    }

    public void updateTimer(int secondsLeft) {
        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - minutes*60;

        String secondString;
        if (seconds < 10) {
            secondString = "0" + Integer.toString(seconds);
        } else {
            secondString = Integer.toString(seconds);
        }
        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);
        timerSeekBar.setProgress(minutes * 60 + seconds);
    }

    public void controlTimer(View view) {
        if (counterIsActive == false) {

            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controllerButton.setText("STOP");

//        Log.i("Button is clicked.", "Pressed");
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    Log.i("finished", "Timer is done!");
                    resetTimer();
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.air_horne);
                    mplayer.start();
                }
            }.start();
        } else {
            resetTimer();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar  = (SeekBar)  findViewById(R.id.timerSeekBar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        controllerButton = (Button) findViewById(R.id.controllerButton);

        int maxSeconds = 900;
        timerSeekBar.setMax(maxSeconds);

        int mySeconds = 5;
        timerSeekBar.setProgress(mySeconds);
        String secondString;
        if (mySeconds - mySeconds/60 < 10) {
            secondString = "0" + Integer.toString(mySeconds - mySeconds/60);
        } else {
            secondString = Integer.toString(mySeconds - mySeconds/60);
        }

        timerTextView.setText(Integer.toString(mySeconds/60) + ":" + secondString);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
