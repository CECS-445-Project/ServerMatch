//Author: Juan Pasillas
package com.example.servermatch.cecs445;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.servermatch.cecs445.ui.setuprestaurant.SetupRestaurant;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withSplashTimeOut(3000)
                .withBackgroundResource(R.drawable.servermatch_startup_background)
                .withLogo(R.drawable.servermatch_icon);

        Log.d("logoTest", config.getLogo().getScaleType().toString());
        config.getLogo().setScaleType(ImageView.ScaleType.CENTER);
        Log.d("logoTest", config.getLogo().getScaleType().toString());

        //SharedPreferences to direct to correct activity based on login flag
        prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean loggedIn = prefs.getBoolean("loggedIn", false);
        if(loggedIn) {
            config.withTargetActivity(MainActivity.class);
        } else {
            config.withTargetActivity(SetupRestaurant.class);
        }

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}
