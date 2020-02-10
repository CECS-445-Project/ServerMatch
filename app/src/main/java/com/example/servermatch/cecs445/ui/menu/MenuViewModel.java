package com.example.servermatch.cecs445.ui.menu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.servermatch.cecs445.models.MenuItem;

import java.util.List;

public class MenuViewModel extends ViewModel {

    private MutableLiveData<List<MenuItem>> mMenuItems;

    public void init(){

    }

    public LiveData<List<MenuItem>> getMenuItems(){
        return mMenuItems;
    }
}