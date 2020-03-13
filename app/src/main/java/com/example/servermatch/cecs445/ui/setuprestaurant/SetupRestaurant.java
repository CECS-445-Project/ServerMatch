package com.example.servermatch.cecs445.ui.setuprestaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.servermatch.cecs445.MainActivity;
import com.example.servermatch.cecs445.R;

public class SetupRestaurant extends AppCompatActivity {

    private Button btnLoginRestaurant;
    private TextView mCreateAccount;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_restaurant);

        btnLoginRestaurant = findViewById(R.id.login_restaurant_button);
        mCreateAccount = findViewById(R.id.setup_click_here);

        //SharedPreferences to save login information
        prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean loggedIn = prefs.getBoolean("loggedIn", false);
        if(loggedIn) {
            startActivity(new Intent(SetupRestaurant.this, MainActivity.class));
        }

        btnLoginRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("loggedIn", true);
                editor.apply();
                startActivity(new Intent(SetupRestaurant.this, MainActivity.class));
            }
        });

        goToSetupAccount();
    }

    public void goToSetupAccount(){
        mCreateAccount.setOnClickListener(v -> {
            // setup switching between activity and fragment
            openRestaurantSetup();
        });
    }

    public void openRestaurantSetup() {
        SetupRestaurantFragment restaurantFragment = SetupRestaurantFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.setup_restaurant_container, restaurantFragment, "SETUP RESTAURANT FRAGMENT").commit();
        Log.d("login_click_here", "Navigated to setup restaurant from login");
    }
}
