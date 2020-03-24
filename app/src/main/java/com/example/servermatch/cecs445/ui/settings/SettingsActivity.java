package com.example.servermatch.cecs445.ui.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.servermatch.cecs445.R;
import com.example.servermatch.cecs445.ui.settings.SettingsFragment;
import com.example.servermatch.cecs445.ui.setuprestaurant.SetupRestaurantFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SettingsFragment settingsFragment = SettingsFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.nav_settings, settingsFragment, "SETTINGS FRAGMENT").commit();    }
}
