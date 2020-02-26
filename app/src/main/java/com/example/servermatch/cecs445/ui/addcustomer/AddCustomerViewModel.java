package com.example.servermatch.cecs445.ui.addcustomer;

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

    public void addCustomer(Customer newCustomer){
        cRepo.addCustomer(newCustomer);
        //clears offline list to prevent duplicate data from firestore listener
        List<Customer> currentCustomers = null;
        mCustomers.postValue(currentCustomers);

    }
    public MutableLiveData<List<Customer>> getmCustomers() {
        return mCustomers;
    }

}