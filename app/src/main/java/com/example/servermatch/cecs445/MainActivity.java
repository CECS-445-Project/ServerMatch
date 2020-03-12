package com.example.servermatch.cecs445;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import com.example.servermatch.cecs445.ui.setuprestaurant.SetupRestaurant;
import com.example.servermatch.cecs445.ui.setuprestaurant.SetupRestaurantFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.servermatch.cecs445.ui.setuprestaurant.SetupRestaurantFragment.*;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView navHeaderRestaurantName;
    private TextView navHeaderRestaurantEmail;
    private TextView navHeaderRestaurantPhone;
    private ImageView navHeaderRestaurantIcon;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //I deleted the fab

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_menu, R.id.nav_frequent_customers, R.id.nav_add_customer, R.id.nav_add_menu_item)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //headerView to set Text for restaurant nav header
        View headerView = navigationView.getHeaderView(0);
        navHeaderRestaurantName = headerView.findViewById(R.id.restaurant_name);
        navHeaderRestaurantEmail = headerView.findViewById(R.id.restaurant_email);
        navHeaderRestaurantPhone = headerView.findViewById(R.id.restaurant_phone);
        navHeaderRestaurantIcon = headerView.findViewById(R.id.restaurant_icon);

        Intent intent = getIntent();
        String setup_restaurant_name = intent.getStringExtra(EXTRA_RESTAURANT_NAME);
        String setup_restaurant_email = intent.getStringExtra(EXTRA_RESTAURANT_EMAIL);
        String setup_restaurant_phone = intent.getStringExtra(EXTRA_RESTAURANT_PHONE);
        Integer setup_restaurant_icon = intent.getIntExtra(EXTRA_RESTAURANT_ICON, R.mipmap.ic_launcher); // default

        navHeaderRestaurantName.setText(setup_restaurant_name);
        navHeaderRestaurantEmail.setText(setup_restaurant_email);
        navHeaderRestaurantPhone.setText(setup_restaurant_phone);
        navHeaderRestaurantIcon.setImageResource(setup_restaurant_icon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}