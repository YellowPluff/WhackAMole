package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GridLayout grid;
    private Drawable moleImage;
    private ImageView[] imageViews;
    private int moleLocation;
    private Random random;

    private Handler handler;
    private MoleHopper moleHopper;
    private CountDownTimer countDownTimer;

    private TextView countDownTimerTextView;
    private TextView clickCounterTextView;
    private int clickCount;

    private TextView instructions;
    private LinearLayout buttonLayout;

    private int timerVariableSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFields();
        for(int i=0; i<16; i++) {
            imageViews[i] = (ImageView) getLayoutInflater().inflate(R.layout.mole_view, null);
            imageViews[i].setMinimumWidth(270);
            imageViews[i].setMinimumHeight(270);
            grid.addView(imageViews[i]);
        }
    }

    private void initFields() {
        grid = findViewById(R.id.gridLayout);
        moleImage = getDrawable(R.drawable.mole);
        imageViews = new ImageView[16];
        random = new Random();
        moleHopper = new MoleHopper();
        countDownTimer = new CountDownTimer();
        moleLocation = random.nextInt(16);
        handler = new Handler();
        countDownTimerTextView = findViewById(R.id.timer_countdown);
        clickCounterTextView = findViewById(R.id.click_counter);
        instructions = findViewById(R.id.textView_instructions);
        buttonLayout = findViewById(R.id.linearLayout_buttons);
    }

    public void button30Seconds(View view) {
        timerVariableSeconds = 30;
        startGame();
    }

    public void button1Minutes(View view) {
        timerVariableSeconds = 60;
        startGame();
    }

    public void button2Minutes(View view) {
        timerVariableSeconds = 120;
        startGame();
    }

    private void startGame() {
        clickCount = 0;
        clickCounterTextView.setText("Moles: " + clickCount);
        handler.postDelayed(countDownTimer, 0);
        handler.postDelayed(moleHopper, 0);
        instructions.setVisibility(View.GONE);
        buttonLayout.setVisibility(View.GONE);
    }

    private void stopGame() {
        handler.removeCallbacks(countDownTimer);
        handler.removeCallbacks(moleHopper);
        instructions.setVisibility(View.VISIBLE);
        buttonLayout.setVisibility(View.VISIBLE);
    }

    private class CountDownTimer implements Runnable {
        @Override
        public void run() {
            if(timerVariableSeconds < 0) {
                stopGame();
            } else {
                countDownTimerTextView.setText("Timer: " + timerVariableSeconds);
                timerVariableSeconds--;
                handler.postDelayed(countDownTimer, 1000);
            }

        }
    }

    private class MoleHopper implements Runnable {
        @Override
        public void run() {
            imageViews[moleLocation].setImageDrawable(null);
            imageViews[moleLocation].setOnClickListener(null);
            moleLocation = random.nextInt(16);
            imageViews[moleLocation].setImageDrawable(moleImage);
            imageViews[moleLocation].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(timerVariableSeconds > 0) {
                        clickCount++;
                        clickCounterTextView.setText("Moles: " + clickCount);
                        handler.postDelayed(moleHopper, 0);
                    }
                }
            });
        }
    }

}