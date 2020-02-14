package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GridLayout grid;
    private Drawable moleImage;
    private ImageView[] imageViews;
    private int moleLocation;
    private Random random;

    private Handler handler;
    private MoleHopper moleHopper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grid = findViewById(R.id.gridLayout);
        moleImage = getDrawable(R.drawable.mole);
        imageViews = new ImageView[16];
        random = new Random();
        moleHopper = new MoleHopper();
        moleLocation = random.nextInt(16);
        handler = new Handler();
        for(int i=0; i<16; i++) {
            imageViews[i] = (ImageView) getLayoutInflater().inflate(R.layout.mole_view, null);
            imageViews[i].setMinimumWidth(270);
            imageViews[i].setMinimumHeight(270);
            if(i == moleLocation) {
                imageViews[i].setImageDrawable(moleImage);
            }
            grid.addView(imageViews[i]);
        }
    }

    public void startPressed(View v) {
        if(handler.hasCallbacks(moleHopper))
        {
            handler.removeCallbacks(moleHopper);
        } else {
            handler.postDelayed(moleHopper, 0);
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
                    //TODO This is where I want to increment the 0
                    Log.w("fatal", "I clicked on imageView");
                }
            });
            handler.postDelayed(moleHopper, 5000);
        }
    }

}