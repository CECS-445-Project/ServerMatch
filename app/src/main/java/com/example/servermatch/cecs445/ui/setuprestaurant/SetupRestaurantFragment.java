package com.example.servermatch.cecs445.ui.setuprestaurant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.servermatch.cecs445.MainActivity;
import com.example.servermatch.cecs445.R;
import com.example.servermatch.cecs445.models.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.servermatch.cecs445.R.drawable.image1;
import static com.example.servermatch.cecs445.R.drawable.image2;
import static com.example.servermatch.cecs445.R.drawable.image3;

public class SetupRestaurantFragment extends Fragment {

    public static final String EXTRA_RESTAURANT_NAME = "com.example.servermatch.cecs445.ui.setuprestaurant.EXTRA_RESTAURANT_NAME";
    public static final String EXTRA_RESTAURANT_EMAIL = "com.example.servermatch.cecs445.ui.setuprestaurant.EXTRA_RESTAURANT_EMAIL";
    public static final String EXTRA_RESTAURANT_PHONE = "com.example.servermatch.cecs445.ui.setuprestaurant.EXTRA_RESTAURANT_PHONE";
    public static final String EXTRA_RESTAURANT_PASS = "com.example.servermatch.cecs445.ui.setuprestaurant.EXTRA_RESTAURANT_PASS";
    public static final String EXTRA_RESTAURANT_ICON = "com.example.servermatch.cecs445.ui.setuprestaurant.EXTRA_RESTAURANT_ICON";

    private static final String TAG = "SetupRestaurantFragment";

    private SetupRestaurantViewModel setupRestaurantViewModel;
    private TextInputLayout setupRestaurantName;
    private TextInputLayout setupRestaurantEmail;
    private TextInputLayout setupRestaurantPhone;
    private TextInputLayout setupRestaurantPass;
    private Integer setupRestaurantIcon;
    private Restaurant newRestaurant;

    private Button btnSetupRestaurant;
    private Button btnLoginRestaurant;

    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private ImageButton imageButton3;

    private ImageView imageView;
    private Integer imageID;

    private TextView mLoginAccount;
    SharedPreferences prefs;

    private FirebaseAuth mAuth;


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
        setupRestaurantViewModel.init();

        View root = inflater.inflate(R.layout.fragment_setup_restaurant, container, false);

        setupRestaurantName = root.findViewById(R.id.setup_restaurant_name);
        setupRestaurantEmail = root.findViewById(R.id.setup_restaurant_email);
        setupRestaurantPhone = root.findViewById(R.id.setup_restaurant_phone);
        setupRestaurantPass = root.findViewById(R.id.setup_restaurant_pass);

        btnSetupRestaurant = root.findViewById(R.id.setup_restaurant_submit);
        btnLoginRestaurant = root.findViewById(R.id.login_restaurant_submit);

        imageButton1 = root.findViewById((R.id.imageButton1));
        imageButton2 = root.findViewById((R.id.imageButton2));
        imageButton3 = root.findViewById((R.id.imageButton3));

        mLoginAccount = root.findViewById(R.id.login_click_here);
        mAuth = FirebaseAuth.getInstance();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.imageButton1:
                        setupRestaurantIcon = image1;
                        Log.d("selected_img", "Selected first profile icon.");
                        break;
                    case R.id.imageButton2:
                        setupRestaurantIcon = image2;
                        Log.d("selected_img", "Selected second profile icon.");
                        break;
                    case R.id.imageButton3:
                        setupRestaurantIcon = image3;
                        Log.d("selected_img", "Selected third profile icon.");
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + v.getId());
                }
            }
        };

        imageButton1.setOnClickListener(listener);
        imageButton2.setOnClickListener(listener);
        imageButton3.setOnClickListener(listener);

        btnSetupRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String restaurantName = setupRestaurantName.getEditText().getText().toString();
                String restaurantEmail = setupRestaurantEmail.getEditText().getText().toString().toLowerCase();
                String restaurantPhone = setupRestaurantPhone.getEditText().getText().toString();
                String restaurantPass = setupRestaurantPass.getEditText().getText().toString();
                Integer restaurantIcon = setupRestaurantIcon;

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(EXTRA_RESTAURANT_NAME, restaurantName);
                intent.putExtra(EXTRA_RESTAURANT_EMAIL, restaurantEmail);
                intent.putExtra(EXTRA_RESTAURANT_PHONE, restaurantPhone);
                intent.putExtra(EXTRA_RESTAURANT_PASS, restaurantPass);
                intent.putExtra(EXTRA_RESTAURANT_ICON, restaurantIcon);

                //SharedPreferences to save login and restaurant information
                prefs = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("loggedIn", true);
                editor.apply();


                //TODO: Implement the validation using database
                validateInput(restaurantName, restaurantEmail, restaurantPhone, restaurantPass);
                setupRestaurantViewModel.addRestaurant(getContext(), newRestaurant);
                startActivity(intent);
                //Restaurant newRestaurant = new Restaurant(restaurantName, restaurantEmail, restaurantPhone);
                //setupRestaurantViewModel.setupRestaurant(newRestaurant);
            }
        });

        mLoginAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(getActivity(), SetupRestaurant.class);
                startActivity(loginIntent);
                Log.d("setup_click_here", "Navigated to login from setup restaurant");
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

    private boolean validatePassword(String password) {
        if(password.isEmpty()) {
            setupRestaurantName.setError("Field can't be empty");
            return false;
        } else {
            setupRestaurantName.setError(null);
            return true;
        }
    }

    public void validateInput(String name, String email, String phone, String password) {
        if(!validateName(name) | !validateEmail(email) | !validatePhone(phone) | !validatePassword(password)) {
            return;
        }

        Log.d(TAG, "validateInput: " + setupRestaurantIcon);
        addRestaurant(email, password);
        newRestaurant = new Restaurant(name, phone, email, (Integer) setupRestaurantIcon);
        Log.d("setup_restaurant", newRestaurant.toString());
        Toast.makeText(getContext(), "Restaurant Created", Toast.LENGTH_SHORT).show();
    }

    public void addRestaurant(String email, String password) {
        Log.d(TAG, "addRestaurant: Registering Restaurant to Firebase");
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "addRestaurant: Added user: " + email);
                    } else {
                        Log.d(TAG, "addRestaurant: " + email + " user not added");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: User not created");
            }
        });
        while(mAuth.getCurrentUser() == null) {
            // wait till user object is initialized
        }
        Log.d(TAG, "addRestaurant: Signing in Restaurant to Firebase");
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "addRestaurant: Signed In: " + email);
                    } else {
                        Log.d(TAG, "addRestaurant: Not Signed In: " + email);
                    }
                }
            });
    }

        public void goToLoginAccount(){
        mLoginAccount.setOnClickListener(v -> {
            // setup switching between fragment
            openRestaurantLogin();
        });
    }

    public void openRestaurantLogin() {
        Intent intent = new Intent(getActivity(), SetupRestaurant.class);
        startActivity(intent);
        Log.d("setup_click_here", "Navigated to login from setup restaurant");

    }

}