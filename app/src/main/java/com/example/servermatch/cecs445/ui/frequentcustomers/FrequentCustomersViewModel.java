package com.example.servermatch.cecs445.ui.frequentcustomers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FrequentCustomersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FrequentCustomersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is frequentcustomers fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}