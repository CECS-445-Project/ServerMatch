//Author: Juan Pasillas
package com.example.servermatch.cecs445.ui.addcustomer;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.servermatch.cecs445.R;
import com.example.servermatch.cecs445.models.Customer;
import com.google.android.material.textfield.TextInputLayout;

public class AddCustomerFragment extends Fragment {

    private AddCustomerViewModel addCustomerViewModel;
    private TextInputLayout addCustomerFname;
    private TextInputLayout addCustomerLname;
    private TextInputLayout addCustomerEmail;
    private TextInputLayout addCustomerPhone;
    private CheckBox addCustomerReceiptText;
    private CheckBox addCustomerReceiptEmail;
    private Button btnAddCustomer;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        addCustomerViewModel = new ViewModelProvider(this).get(AddCustomerViewModel.class);
        addCustomerViewModel.init();

        //need to fix observer feature.
//        addCustomerViewModel.getmCustomers().observe(getActivity(), new Observer<List<Customer>>() {
//            @Override
//            public void onChanged(List<Customer> customers) {
//
//            }
//        });

        View root = inflater.inflate(R.layout.fragment_add_customer, container, false);

        addCustomerFname = root.findViewById(R.id.add_customer_fname);
        addCustomerLname = root.findViewById(R.id.add_customer_lname);
        addCustomerEmail = root.findViewById(R.id.add_customer_email);
        addCustomerPhone = root.findViewById(R.id.add_customer_phone);
        addCustomerReceiptText = root.findViewById(R.id.add_customer_receipt_text);
        addCustomerReceiptEmail = root.findViewById(R.id.add_customer_receipt_email);
        btnAddCustomer = root.findViewById(R.id.add_customer_submit);

        btnAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: set up firebase here
                String customerFname = addCustomerFname.getEditText().getText().toString();
                String customerLname  = addCustomerLname.getEditText().getText().toString();
                String customerEmail  = addCustomerEmail.getEditText().getText().toString().toLowerCase().trim();
                String customerPhone =  addCustomerPhone.getEditText().getText().toString();

                validateInput(customerFname, customerLname, customerEmail, customerPhone);
                Customer newCustomer = new Customer(customerFname, customerLname, customerEmail,customerPhone,
                        addCustomerReceiptText.isChecked(), addCustomerReceiptEmail.isChecked());
                addCustomerViewModel.addCustomer(newCustomer);
            }
        });

        return root;
    }

    private boolean validateFname(String firstName) {
        if(firstName.isEmpty()) {
            addCustomerFname.setError("Field can't be empty");
            return false;
        } else {
            addCustomerFname.setError(null);
            return true;
        }
    }

    private boolean validateLname(String lastName) {
        if(lastName.isEmpty()) {
            addCustomerLname.setError("Field can't be empty");
            return false;
        } else {
            addCustomerLname.setError(null);
            return true;
        }
    }

    private boolean validateEmail(String email) {
        if(email.isEmpty()) {
            addCustomerEmail.setError("Field can't be empty");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            addCustomerEmail.setError("Invalid email address");
            return false;
        } else {
            addCustomerEmail.setError(null);
            return true;
        }
    }

    private boolean validatePhone(String phone) {
        String regexPhone = "^[0-9]+$";
        if (phone.isEmpty()) {
            addCustomerPhone.setError("Field can't be empty");
            return false;
        } else if (phone.length() < 10 || !phone.matches(regexPhone)) {
            addCustomerPhone.setError("Invalid phone number");
            return false;
        } else {
            addCustomerPhone.setError(null);
            return true;
        }
    }

    public void validateInput(String firstName, String lastName, String email, String phone) {
        if(!validateFname(firstName) | !validateLname(lastName) | !validateEmail(email) | !validatePhone(phone)) {
            return;
        }

        Customer c1 = new Customer(firstName, lastName, email, phone,
                addCustomerReceiptText.isChecked(), addCustomerReceiptEmail.isChecked());
        Log.d("add_customer", c1.toString());
        Toast.makeText(getContext(), "Customer Added", Toast.LENGTH_SHORT).show();
    }

}