package com.example.servermatch.cecs445.ui.addrestaurant;

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


import com.example.servermatch.cecs445.R;
import com.example.servermatch.cecs445.models.Restaurant;
import com.google.android.material.textfield.TextInputLayout;

public class AddRestaurantFragment extends Fragment {

    private AddRestaurantViewModel addRestaurantViewModel;
    private TextInputLayout addRestaurantName;
    private TextInputLayout addRestaurantEmail;
    private TextInputLayout addRestaurantPhone;
    private Button btnAddRestaurant;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        addRestaurantViewModel = new ViewModelProvider(this).get(AddRestaurantViewModel.class);

        View root = inflater.inflate(R.layout.fragment_add_restaurant, container, false);

        addRestaurantName = root.findViewById(R.id.add_restaurant_name);
        addRestaurantEmail = root.findViewById(R.id.add_restaurant_email);
        addRestaurantPhone = root.findViewById(R.id.add_restaurant_phone);
        btnAddRestaurant = root.findViewById(R.id.add_restaurant_submit);

        btnAddRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInput(v);

                String restaurantName = addRestaurantName.getEditText().getText().toString();
                String restaurantEmail = addRestaurantEmail.getEditText().getText().toString().toLowerCase();
                String restaurantPhone = addRestaurantPhone.getEditText().getText().toString();

                Restaurant newRestaurant = new Restaurant(restaurantName, restaurantEmail, restaurantPhone);
                addRestaurantViewModel.addRestaurant(newRestaurant);
            }
        });

        return root;
    }

    private boolean validateName() {
        String nameInput = addRestaurantName.getEditText().getText().toString().trim();
        if(nameInput.isEmpty()) {
            addRestaurantName.setError("Field can't be empty");
            return false;
        } else {
            addRestaurantName.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String emailInput = addRestaurantEmail.getEditText().getText().toString().trim();
        if(emailInput.isEmpty()) {
            addRestaurantEmail.setError("Field can't be empty");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            addRestaurantEmail.setError("Invalid email address");
            return false;
        } else {
            addRestaurantEmail.setError(null);
            return true;
        }
    }

    private boolean validatePhone() {
        String regexPhone = "^[0-9]+$";
        String phoneInput = addRestaurantPhone.getEditText().getText().toString().trim();
        if (phoneInput.isEmpty()) {
            addRestaurantPhone.setError("Field can't be empty");
            return false;
        } else if (phoneInput.length() < 10 || !phoneInput.matches(regexPhone)) {
            addRestaurantPhone.setError("Invalid phone number");
            return false;
        } else {
            addRestaurantPhone.setError(null);
            return true;
        }
    }

        public void validateInput(View v) {
            if(!validateName() | !validateEmail() | !validatePhone()) {
                return;
            }

            Restaurant r1 = new Restaurant(
                    addRestaurantName.getEditText().getText().toString(),
                    addRestaurantEmail.getEditText().getText().toString(),
                    addRestaurantPhone.getEditText().getText().toString()
            );

            Log.i("add_restaurant", r1.toString());
            Toast.makeText(getContext(), "Restaurant Created", Toast.LENGTH_SHORT).show();
        }

}
