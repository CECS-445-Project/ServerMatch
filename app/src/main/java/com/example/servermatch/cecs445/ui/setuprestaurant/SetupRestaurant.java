package com.example.servermatch.cecs445.ui.setuprestaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.servermatch.cecs445.R;

import java.util.Set;

public class SetupRestaurant extends AppCompatActivity /*implements View.OnClickListener*/ {

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

        /*
        ImageButton imageButton1 = findViewById(R.id.imageButton1);
        ImageButton imageButton2 = findViewById(R.id.imageButton2);
        ImageButton imageButton3 = findViewById(R.id.imageButton3);

        imageButton1.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        imageButton3.setOnClickListener(this);
        */

    }


    public void openRestaurantSetup() {
        SetupRestaurantFragment restaurantFragment = SetupRestaurantFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.setup_restaurant_container, restaurantFragment, "SETUP RESTAURANT FRAGMENT").commit();
    }

    /*
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.imageButton1:
                Toast.makeText(this, "Red Image Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageButton2:
                Toast.makeText(this, "Blue Image Selected", Toast.LENGTH_SHORT).show();
                break ;
            case R.id.imageButton3:
                Toast.makeText(this, "Yellow Image Selected", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    */
}
