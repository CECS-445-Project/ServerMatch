package com.example.servermatch.cecs445.ui.addcustomer;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.servermatch.cecs445.R;
import com.google.android.material.textfield.TextInputLayout;

public class AddCustomerFragment extends Fragment {

    private AddCustomerViewModel addCustomerViewModel;
    private TextInputLayout addCustomerFname;
    private TextInputLayout addCustomerLname;
    private TextInputLayout addCustomerEmail;
    private TextInputLayout addCustomerPhone;
    private CheckBox addCustomerNotifyText;
    private CheckBox addCustomerNotifyEmail;
    private Button btnAddCustomer;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addCustomerViewModel =
                ViewModelProviders.of(this).get(AddCustomerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_customer, container, false);

        addCustomerFname = root.findViewById(R.id.add_customer_fname);
        addCustomerLname = root.findViewById(R.id.add_customer_lname);
        addCustomerEmail = root.findViewById(R.id.add_customer_email);
        addCustomerPhone = root.findViewById(R.id.add_customer_phone);
        addCustomerNotifyText = root.findViewById(R.id.add_customer_notify_text);
        addCustomerNotifyEmail = root.findViewById(R.id.add_customer_notify_email);
        btnAddCustomer = root.findViewById(R.id.add_customer_submit);

        btnAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInput(v);
            }
        });

        return root;
    }

    private boolean validateFname() {
        String FnameInput = addCustomerFname.getEditText().getText().toString().trim();
        if(FnameInput.isEmpty()) {
            addCustomerFname.setError("Field can't be empty");
            return false;
        } else {
            addCustomerFname.setError(null);
            return true;
        }
    }

    private boolean validateLname() {
        String LnameInput = addCustomerLname.getEditText().getText().toString().trim();
        if(LnameInput.isEmpty()) {
            addCustomerLname.setError("Field can't be empty");
            return false;
        } else {
            addCustomerLname.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String emailInput = addCustomerEmail.getEditText().getText().toString().trim();
        if(emailInput.isEmpty()) {
            addCustomerEmail.setError("Field can't be empty");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            addCustomerEmail.setError("Invalid email address");
            return false;
        } else {
            addCustomerEmail.setError(null);
            return true;
        }
    }

    private boolean validatePhone() {
        String regexPhone = "^[0-9]+$";
        String phoneInput = addCustomerPhone.getEditText().getText().toString().trim();
        if (phoneInput.isEmpty()) {
            addCustomerPhone.setError("Field can't be empty");
            return false;
        } else if (phoneInput.length() < 10 || !phoneInput.matches(regexPhone)) {
            addCustomerPhone.setError("Invalid phone number");
            return false;
        } else {
            addCustomerPhone.setError(null);
            return true;
        }
    }

    public void validateInput(View v) {
        if(!validateFname() | !validateLname() | !validateEmail() | !validatePhone()) {
            return;
        }
        String input = "\nCUSTOMER ADDED:";
        input += "\nFirst Name: " + addCustomerFname.getEditText().getText().toString();
        input += "\nLast Name: " + addCustomerLname.getEditText().getText().toString();
        input += "\nEmail: " + addCustomerEmail.getEditText().getText().toString();
        input += "\nPhone: " + addCustomerPhone.getEditText().getText().toString();
        input += "\nText Notifications: " + addCustomerNotifyText.isChecked();
        input += "\nEmail Notifications: " + addCustomerNotifyEmail.isChecked();
        Log.i("add_customer", input);
        Toast.makeText(getContext(), "Customer Added", Toast.LENGTH_SHORT).show();
    }

}