package com.example.servermatch.cecs445.ui.setuprestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.servermatch.cecs445.MainActivity;
import com.example.servermatch.cecs445.R;
import com.example.servermatch.cecs445.models.Restaurant;
import com.google.android.material.textfield.TextInputLayout;

public class SetupRestaurantFragment extends Fragment {

    public static final String EXTRA_RESTAURANT_NAME = "com.example.servermatch.cecs445.ui.setuprestaurant.EXTRA_RESTAURANT_NAME";
    public static final String EXTRA_RESTAURANT_EMAIL = "com.example.servermatch.cecs445.ui.setuprestaurant.EXTRA_RESTAURANT_EMAIL";
    public static final String EXTRA_RESTAURANT_PHONE = "com.example.servermatch.cecs445.ui.setuprestaurant.EXTRA_RESTAURANT_PHONE";
    public static final String EXTRA_RESTAURANT_PASS = "com.example.servermatch.cecs445.ui.setuprestaurant.EXTRA_RESTAURANT_PHONE";


    private SetupRestaurantViewModel setupRestaurantViewModel;
    private TextInputLayout setupRestaurantName;
    private TextInputLayout setupRestaurantEmail;
    private TextInputLayout setupRestaurantPhone;
    private TextInputLayout setupRestaurantPass;

    private Button btnSetupRestaurant;
    private Button btnLoginRestaurant;


    public SetupRestaurantFragment() {
        //blank constructor
    }

    public static SetupRestaurantFragment newInstance() {
        SetupRestaurantFragment fragment = new SetupRestaurantFragment();
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        setupRestaurantViewModel = new ViewModelProvider(this).get(SetupRestaurantViewModel.class);

        View root = inflater.inflate(R.layout.fragment_setup_restaurant, container, false);

        setupRestaurantName = root.findViewById(R.id.setup_restaurant_name);
        setupRestaurantEmail = root.findViewById(R.id.setup_restaurant_email);
        setupRestaurantPhone = root.findViewById(R.id.setup_restaurant_phone);
        setupRestaurantPass = root.findViewById(R.id.setup_restaurant_pass);

        btnSetupRestaurant = root.findViewById(R.id.setup_restaurant_submit);
        btnLoginRestaurant = root.findViewById(R.id.login_restaurant_submit);


        btnSetupRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String restaurantName = setupRestaurantName.getEditText().getText().toString();
                String restaurantEmail = setupRestaurantEmail.getEditText().getText().toString().toLowerCase();
                String restaurantPhone = setupRestaurantPhone.getEditText().getText().toString();
                String restaurantPass = setupRestaurantPass.getEditText().getText().toString();


                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(EXTRA_RESTAURANT_NAME, restaurantName);
                intent.putExtra(EXTRA_RESTAURANT_EMAIL, restaurantEmail);
                intent.putExtra(EXTRA_RESTAURANT_PHONE, restaurantPhone);
                intent.putExtra(EXTRA_RESTAURANT_PASS, restaurantPass);

                startActivity(intent);

                //TODO: Implement the validation using database
                //validateInput(restaurantName, restaurantEmail, restaurantPhone);
                //Restaurant newRestaurant = new Restaurant(restaurantName, restaurantEmail, restaurantPhone);
                //setupRestaurantViewModel.setupRestaurant(newRestaurant);
            }

        });

        return root;
    }

    private boolean validateName(String name) {
        if(name.isEmpty()) {
            setupRestaurantName.setError("Field can't be empty");
            return false;
        } else {
            setupRestaurantName.setError(null);
            return true;
        }
    }

    private boolean validateEmail(String email) {
        if(email.isEmpty()) {
            setupRestaurantEmail.setError("Field can't be empty");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            setupRestaurantEmail.setError("Invalid email setupress");
            return false;
        } else {
            setupRestaurantEmail.setError(null);
            return true;
        }
    }

    private boolean validatePhone(String phone) {
        String regexPhone = "^[0-9]+$";
        if (phone.isEmpty()) {
            setupRestaurantPhone.setError("Field can't be empty");
            return false;
        } else if (phone.length() < 10 || !phone.matches(regexPhone)) {
            setupRestaurantPhone.setError("Invalid phone number");
            return false;
        } else {
            setupRestaurantPhone.setError(null);
            return true;
        }
    }

    public void validateInput(String name, String email, String phone) {
        if(!validateName(name) | !validateEmail(email) | !validatePhone(phone)) {
            return;
        }

        Restaurant r1 = new Restaurant(name, phone, email);
        Log.d("setup_restaurant", r1.toString());
        Toast.makeText(getContext(), "Restaurant Created", Toast.LENGTH_SHORT).show();
    }

}