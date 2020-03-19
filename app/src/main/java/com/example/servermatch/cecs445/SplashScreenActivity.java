//Author: Juan Pasillas
package com.example.servermatch.cecs445;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.servermatch.cecs445.ui.setuprestaurant.SetupRestaurant;

public class SplashScreenActivity extends AppCompatActivity {

    private int SPLASH_TIME = 3000;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Display the splash screen for 3 seconds and then direct to the correct activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //SharedPreferences to direct to correct activity based on login flag
                prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
                boolean loggedIn = prefs.getBoolean("loggedIn", false);
                if(loggedIn) {
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, SetupRestaurant.class));
                }
                finish();
            }
        }, SPLASH_TIME);
    }
}
