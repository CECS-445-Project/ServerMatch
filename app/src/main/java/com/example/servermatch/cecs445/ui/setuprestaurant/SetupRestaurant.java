package com.example.servermatch.cecs445.ui.setuprestaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.servermatch.cecs445.MainActivity;
import com.example.servermatch.cecs445.R;
import com.example.servermatch.cecs445.models.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class SetupRestaurant extends AppCompatActivity {

    public static final String EXTRA_RESTAURANT_NAME = "com.example.servermatch.cecs445.ui.setuprestaurant.EXTRA_RESTAURANT_NAME";
    public static final String EXTRA_RESTAURANT_EMAIL = "com.example.servermatch.cecs445.ui.setuprestaurant.EXTRA_RESTAURANT_EMAIL";
    public static final String EXTRA_RESTAURANT_PHONE = "com.example.servermatch.cecs445.ui.setuprestaurant.EXTRA_RESTAURANT_PHONE";
    public static final String EXTRA_RESTAURANT_PASS = "com.example.servermatch.cecs445.ui.setuprestaurant.EXTRA_RESTAURANT_PASS";
    public static final String EXTRA_RESTAURANT_ICON = "com.example.servermatch.cecs445.ui.setuprestaurant.EXTRA_RESTAURANT_ICON";
    private static final String TAG = "SetupRestaurant";
    private SetupRestaurantViewModel setupRestaurantViewModel;
    private Button btnLoginRestaurant;
    private TextView mCreateAccount;
    private EditText emailEditText;
    private EditText pwEditText;
    private List<Restaurant> restaurantlist;
    SharedPreferences prefs;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_restaurant);
        setupRestaurantViewModel = new ViewModelProvider(this).get(SetupRestaurantViewModel.class);
        setupRestaurantViewModel.init();

        setupRestaurantViewModel.getmRestaurants().observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {
                restaurantlist = restaurants;
            }
        });

        mAuth = FirebaseAuth.getInstance();
        btnLoginRestaurant = findViewById(R.id.login_restaurant_button);
        mCreateAccount = findViewById(R.id.setup_click_here);
        emailEditText = findViewById(R.id.et_login_email);
        pwEditText =  findViewById(R.id.et_login_pw);


        //SharedPreferences to save login information
        prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean loggedIn = prefs.getBoolean("loggedIn", false);
        if(loggedIn) {
            startActivity(new Intent(SetupRestaurant.this, MainActivity.class));
        }

        btnLoginRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String pw = pwEditText.getText().toString();
                if( !(email.isEmpty() || pw.isEmpty())) {
                    mAuth.signInWithEmailAndPassword(email, pw)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);

                                        Intent intent = new Intent(SetupRestaurant.this, MainActivity.class);
                                        Log.d(TAG, restaurantlist.toString());
                                        for (Restaurant r : restaurantlist) {
                                            if (r.getEmail().equals(email)) {
                                                Log.d(TAG, "onComplete: IN LOOP wit intent stuff");
                                                intent.putExtra(EXTRA_RESTAURANT_NAME, r.getName());
                                                intent.putExtra(EXTRA_RESTAURANT_EMAIL, r.getEmail());
                                                intent.putExtra(EXTRA_RESTAURANT_PHONE, r.getPhoneNum());
                                                intent.putExtra(EXTRA_RESTAURANT_ICON, r.getIcon());
                                                prefs = SetupRestaurant.this.getSharedPreferences("prefs", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = prefs.edit();
                                                editor.putBoolean("loggedIn", true);
                                                editor.apply();
                                            }
                                        }

                                        startActivity(intent);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(SetupRestaurant.this,
                                                "Incorrect Username/Pw! Try again.", Toast.LENGTH_LONG).show();
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        updateUI(null);
                                    }
                                }
                            });
                }else{
                    Toast.makeText(SetupRestaurant.this, "Incorrect Username/Pw! Try again. ", Toast.LENGTH_SHORT).show();
                }
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

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }
}
