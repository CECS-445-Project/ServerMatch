package com.example.servermatch.cecs445.ui.addmenuitem;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddMenuItemViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddMenuItemViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is addmenuitem fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}