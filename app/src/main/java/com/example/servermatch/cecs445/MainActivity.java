package com.example.servermatch.cecs445;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//    private TextInputLayout textInputItemName;
//    private TextInputLayout textInputItemCost;
//    private TextInputLayout textInputItemDesc;

    private AppBarConfiguration mAppBarConfiguration;

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
//        textInputItemName = findViewById(R.id.text_input_item_name);
//        textInputItemCost = findViewById(R.id.text_input_item_cost);
//        textInputItemDesc = findViewById(R.id.text_input_item_desc);

    }

//    private boolean validateName() {
//        String nameInput = textInputItemName.getEditText().getText().toString().trim();
//
//        if(nameInput.isEmpty()) {
//            textInputItemName.setError("Field can't be empty");
//            return false;
//        } else {
//            textInputItemName.setError(null);
//            return true;
//        }
//    }
//
//    private boolean validateCost() {
//        String costInput = textInputItemCost.getEditText().getText().toString().trim();
//
//        if(costInput.isEmpty()) {
//            textInputItemCost.setError("Field can't be empty");
//            return false;
//        } else {
//            textInputItemCost.setError(null);
//            return true;
//        }
//    }
//
//    private boolean validateDesc() {
//        String descInput = textInputItemDesc.getEditText().getText().toString().trim();
//
//        if(descInput.isEmpty()) {
//            textInputItemDesc.setError("Field can't be empty");
//            return false;
//        } else if (descInput.length() > 200) {
//            textInputItemDesc.setError("Description too long");
//            return false;
//        } else {
//            textInputItemDesc.setError(null);
//            return true;
//        }
//    }
//
//    public void addItem(View v) {
//        if(!validateName() | !validateCost() | !validateDesc()) {
//            return;
//        }
//        String input = "Name: " + textInputItemName.getEditText().getText().toString();
//        input += "\n";
//        input += "Cost: " + textInputItemCost.getEditText().getText().toString();
//        input += "\n";
//        input += "Description: " + textInputItemDesc.getEditText().getText().toString();
//
//        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
//    }

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
