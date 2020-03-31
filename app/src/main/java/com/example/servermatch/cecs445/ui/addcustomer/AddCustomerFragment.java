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
import androidx.appcompat.app.AppCompatActivity;
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

        addCustomerViewModelObserver();

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
                validateInput(v);
                String customerFname = addCustomerFname.getEditText().getText().toString();
                String customerLname  = addCustomerLname.getEditText().getText().toString();
                String customerEmail  = addCustomerEmail.getEditText().getText().toString().toLowerCase();
                String customerPhone =  addCustomerPhone.getEditText().getText().toString();

                Customer newCustomer = new Customer(customerFname, customerLname, customerEmail,customerPhone,
                        addCustomerReceiptText.isChecked(), addCustomerReceiptEmail.isChecked());
                boolean addCustomerCompleted = addCustomerViewModel.addCustomer(newCustomer, getContext());

                if(addCustomerCompleted){
                    clearFields();
                }else{
                    Toast.makeText(getContext(), "Please re-enter a valid email!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Set action bar title
        if(getActivity() != null) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add Customer");
        }
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

        Customer c1 = new Customer(
                addCustomerFname.getEditText().getText().toString(),
                addCustomerLname.getEditText().getText().toString(),
                addCustomerEmail.getEditText().getText().toString(),
                addCustomerEmail.getEditText().getText().toString(),
                addCustomerReceiptText.isChecked(),
                addCustomerReceiptEmail.isChecked()
        );

        Log.i("add_customer", c1.toString());

    }


    private void addCustomerViewModelObserver(){
        addCustomerViewModel.getmCustomers().observe(getViewLifecycleOwner(), menuItems -> {

        });
    }

    private void clearFields(){
                addCustomerFname.getEditText().setText("");
                addCustomerLname.getEditText().setText("");
                addCustomerEmail.getEditText().setText("");
                addCustomerPhone.getEditText().setText("");
                addCustomerReceiptText.setChecked(false);
                addCustomerReceiptEmail.setChecked(false);
    }
}