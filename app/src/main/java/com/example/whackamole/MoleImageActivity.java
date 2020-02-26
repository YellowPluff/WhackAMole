package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.RadioButton;

public class MoleImageActivity extends AppCompatActivity {

    private RadioButton moleOneButton;
    private RadioButton moleTwoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mole_image);
        moleOneButton = findViewById(R.id.mole_one_button);
        moleTwoButton = findViewById(R.id.mole_two_button);
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
