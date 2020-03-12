package com.example.servermatch.cecs445;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.servermatch.cecs445.Utils.DialogLogout;
import com.example.servermatch.cecs445.ui.addcustomer.AddCustomerFragment;
import com.example.servermatch.cecs445.ui.addmenuitem.AddMenuItemFragment;
import com.example.servermatch.cecs445.ui.frequentcustomers.FrequentCustomersFragment;
import com.example.servermatch.cecs445.ui.menu.MenuFragment;
import com.example.servermatch.cecs445.ui.setuprestaurant.SetupRestaurant;
import com.example.servermatch.cecs445.ui.setuprestaurant.SetupRestaurantFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawer;

    private TextView navHeaderRestaurantName;
    private TextView navHeaderRestaurantEmail;
    private TextView navHeaderRestaurantPhone;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //I deleted the fab

        mDrawer = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_menu, R.id.nav_frequent_customers, R.id.nav_add_customer, R.id.nav_add_menu_item, R.id.nav_logout)
                .setDrawerLayout(mDrawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(mNavigationView, navController);
        // Setting the listener for the navigation
        mNavigationView.setNavigationItemSelectedListener(this);

        //headerView to set Text for restaurant nav header
        View headerView = mNavigationView.getHeaderView(0);
        navHeaderRestaurantName = headerView.findViewById(R.id.restaurant_name);
        navHeaderRestaurantEmail = headerView.findViewById(R.id.restaurant_email);
        navHeaderRestaurantPhone = headerView.findViewById(R.id.restaurant_phone);

        setNavHeaderStrings();
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

    /* Sets the restaurant name, email, and phone for the navigation header */
    public void setNavHeaderStrings() {
        prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean setupNavHeader = prefs.getBoolean("setupNavHeader", true);
        if(setupNavHeader) {
            Intent intent = getIntent();
            String setup_restaurant_name = intent.getStringExtra(SetupRestaurantFragment.EXTRA_RESTAURANT_NAME);
            String setup_restaurant_email = intent.getStringExtra(SetupRestaurantFragment.EXTRA_RESTAURANT_EMAIL);
            String setup_restaurant_phone = intent.getStringExtra(SetupRestaurantFragment.EXTRA_RESTAURANT_PHONE);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("setupNavHeader", false);
            editor.putString("restaurantName", setup_restaurant_name);
            editor.putString("restaurantEmail", setup_restaurant_email);
            editor.putString("restaurantPhone", setup_restaurant_phone);
            editor.apply();
        }
        navHeaderRestaurantName.setText(prefs.getString("restaurantName", "null"));
        navHeaderRestaurantEmail.setText(prefs.getString("restaurantEmail", "null"));
        navHeaderRestaurantPhone.setText(prefs.getString("restaurantPhone", "null"));
    }

    /* Function for an onClick of the items in the navigation drawer */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_right,R.anim.slide_in_right,R.anim.slide_out_right);
        if(id == R.id.nav_menu) {
            Log.d("navMenu", "Menu");
            MenuFragment menuFragment = new MenuFragment();
            transaction.replace(R.id.nav_host_fragment, menuFragment);
        }
        if(id == R.id.nav_frequent_customers) {
            Log.d("navMenu", "Frequent Customers");
            FrequentCustomersFragment frequentCustomersFragment = new FrequentCustomersFragment();
            transaction.replace(R.id.nav_host_fragment, frequentCustomersFragment);
        }
        if(id == R.id.nav_add_customer) {
            Log.d("navMenu", "Add Customer");
            AddCustomerFragment addCustomerFragment = new AddCustomerFragment();
            transaction.replace(R.id.nav_host_fragment, addCustomerFragment);
        }
        if(id == R.id.nav_add_menu_item) {
            Log.d("navMenu", "Add Menu Item");
            AddMenuItemFragment addMenuItemFragment = new AddMenuItemFragment();
            transaction.replace(R.id.nav_host_fragment, addMenuItemFragment);
        }
        if(id == R.id.nav_logout) {
            Log.d("navMenu", "Logout");
            DialogLogout dialog = new DialogLogout();
            dialog.show(getSupportFragmentManager(), "DialogLogout");
        }
        transaction.addToBackStack(null);
        transaction.commit();
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
