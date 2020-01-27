package com.example.servermatch.cecs445.ui.addcustomer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddCustomerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddCustomerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is addcustomer fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}