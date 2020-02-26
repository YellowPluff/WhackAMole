package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.RadioButton;

public class MoleImageActivity extends AppCompatActivity {

    private RadioButton moleOneButton;
    private RadioButton moleTwoButton;
    private RadioButton moleThreeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mole_image);
        moleOneButton = findViewById(R.id.mole_one_button);
        moleTwoButton = findViewById(R.id.mole_two_button);
        moleThreeButton = findViewById(R.id.mole_three_button);
        Intent intent = getIntent();
        int moleImageInt = intent.getIntExtra("MOLE_IMAGE", 1);
        if(moleImageInt == 1) {
            moleOneButton.setChecked(true);
        } else if(moleImageInt == 2) {
            moleTwoButton.setChecked(true);
        } else {
            moleThreeButton.setChecked(true);
        }
    }



    @Override
    public void onBackPressed() {
        int moleImage;
        if(moleOneButton.isChecked()) {
            moleImage = 1;
        } else if(moleTwoButton.isChecked()) {
            moleImage = 2;
        } else {
            moleImage = 3;
        }
        Intent intent = new Intent();
        intent.putExtra("MOLE_IMAGE", moleImage);
        setResult(RESULT_OK, intent);
        finish();
    }
}
