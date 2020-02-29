package com.example.servermatch.cecs445.ui.setuprestaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.servermatch.cecs445.R;

import java.util.Set;

public class SetupRestaurant extends AppCompatActivity {

    Button btnLoginRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_restaurant);

        btnLoginRestaurant = findViewById(R.id.login_restaurant_button);
        btnLoginRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(SetupRestaurant.this, MainActivity.class));
                openRestaurantSetup();
            }
        });
    }

    public void openRestaurantSetup() {
        SetupRestaurantFragment restaurantFragment = SetupRestaurantFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.setup_restaurant_container, restaurantFragment, "SETUP RESTAURANT FRAGMENT").commit();
    }
}
