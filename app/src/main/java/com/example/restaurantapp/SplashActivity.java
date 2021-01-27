package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class SplashActivity extends AppCompatActivity {
MediaPlayer music;
SharedPreferences preferences;
    int orderId;
    int SPLASH_TIME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preferences = getSharedPreferences("data", MODE_PRIVATE);
       orderId= preferences.getInt("orderId",0);
         SPLASH_TIME = 3000;
        YoYo.with(Techniques.Bounce)
                .duration(700)
                .repeat(3)
                .playOn(findViewById(R.id.logo));
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .repeat(3)
                .playOn(findViewById(R.id.textViewMagdy));
        music= MediaPlayer.create(SplashActivity.this, R.raw.sound);
        music.start();
        new Handler().postDelayed(() -> {

            Intent in = new Intent(SplashActivity.this, MainActivity.class);
            in.putExtra("orderId",orderId);
            startActivity(in);
            finish();
        }, SPLASH_TIME);
    }

    @Override
    protected void onDestroy() {
        music.stop();
        music.release();
        super.onDestroy();
    }
}