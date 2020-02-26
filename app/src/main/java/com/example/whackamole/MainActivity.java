package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GridLayout grid;
    private Drawable moleImage;
    private int moleImageInt;
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
        moleImageInt = 1;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.help_menuitem) {
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
        } else if (id == R.id.mole_menuitem) {
            Intent intent = new Intent(this, MoleImageActivity.class);
            intent.putExtra("MOLE_IMAGE", moleImageInt);
            startActivityForResult(intent, 1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        moleImageInt = data.getIntExtra("MOLE_IMAGE", 1);
        if(moleImageInt == 1)
            this.moleImage = getDrawable(R.drawable.mole);
        else if(moleImageInt == 2)
            this.moleImage = getDrawable(R.drawable.mole2);
        else
            this.moleImage = getDrawable(R.drawable.mole3);
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