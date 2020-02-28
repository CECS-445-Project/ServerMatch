package com.example.servermatch.cecs445.ui.addcustomer;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.servermatch.cecs445.models.Customer;
import com.example.servermatch.cecs445.repositories.CustomerRepo;

import java.util.List;

public class AddCustomerViewModel extends ViewModel {

    private MutableLiveData<List<Customer>> mCustomers;
    private CustomerRepo cRepo;

    public void init(){
        if(cRepo != null){
            return;
        }
        cRepo = CustomerRepo.getInstance();
        mCustomers = cRepo.getCustomers();
        List<Customer> currentCustomers = mCustomers.getValue();
        mCustomers.postValue(currentCustomers);
    }

    public boolean addCustomer(Customer newCustomer, Context context){
        return cRepo.addCustomer(newCustomer, context);
    }
    public MutableLiveData<List<Customer>> getmCustomers() {
        return mCustomers;
    }

}