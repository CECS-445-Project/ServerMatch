package com.example.servermatch.cecs445.ui.setuprestaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servermatch.cecs445.MainActivity;
import com.example.servermatch.cecs445.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Set;

public class SetupRestaurant extends AppCompatActivity {

    private Button btnLoginRestaurant;
    private Button btnSubmitRestaurant;
    private TextView mCreateAccount;
    private TextInputLayout textInputResName;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPhoneNumber;
    private TextInputLayout textInputPassword;
    private FirebaseAuth firebaseAuth;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_restaurant);

        btnLoginRestaurant = findViewById(R.id.login_restaurant_button);
        btnSubmitRestaurant = findViewById(R.id.setup_restaurant_submit);
        mCreateAccount = findViewById(R.id.setup_click_here);
        textInputResName = findViewById(R.id.setup_restaurant_name);
        textInputEmail = findViewById(R.id.setup_restaurant_email);
        textInputPhoneNumber = findViewById(R.id.setup_restaurant_phone);
        textInputPassword = findViewById(R.id.setup_restaurant_pass);

        firebaseAuth = FirebaseAuth.getInstance();

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

        btnSubmitRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateName() | !validateEmail() | !validatePhoneNumber() | !validatePassword()) {
                    return;
                }
                String emailInput = textInputEmail.getEditText().getText().toString().trim();
                String passwordInput = textInputPassword.getEditText().getText().toString().trim();

                firebaseAuth.createUserWithEmailAndPassword(emailInput, passwordInput)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    openRestaurantSetup();
                                }
                            }
                        });
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

    private boolean validateName() {
        String nameInput = textInputResName.getEditText().getText().toString().trim();

        if(nameInput.isEmpty()) {
            textInputResName.setError("Field can't be empty");
            return false;
        } else {
            textInputResName.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if(emailInput.isEmpty()) {
            textInputEmail.setError("Field can't be empty");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNumber() {
        String phoneNumberInput = textInputPhoneNumber.getEditText().getText().toString().trim();

        if(phoneNumberInput.isEmpty()) {
            textInputPhoneNumber.setError("Field can't be empty");
            return false;
        } else if (phoneNumberInput.length() > 10) {
            textInputPhoneNumber.setError("Description too long");
            return false;
        } else {
            textInputPhoneNumber.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if(passwordInput.isEmpty()) {
            textInputPassword.setError("Field can't be empty");
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }

}
