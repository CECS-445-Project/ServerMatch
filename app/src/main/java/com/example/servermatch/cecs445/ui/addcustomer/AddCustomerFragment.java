package com.example.servermatch.cecs445.ui.addcustomer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addCustomerViewModel =
                ViewModelProviders.of(this).get(AddCustomerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_customer, container, false);
        addCustomerFname = root.findViewById(R.id.add_customer_fname);
        addCustomerLname = root.findViewById(R.id.add_customer_lname);
        addCustomerEmail = root.findViewById(R.id.add_customer_email);
        addCustomerPhone = root.findViewById(R.id.add_customer_phone);

        return root;
    }


}