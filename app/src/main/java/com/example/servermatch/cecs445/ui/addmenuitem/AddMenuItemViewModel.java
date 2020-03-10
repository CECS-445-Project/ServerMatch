package com.example.servermatch.cecs445.ui.addmenuitem;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.servermatch.cecs445.models.MenuItem;
import com.example.servermatch.cecs445.repositories.MenuItemRepo;

import java.util.List;

public class AddMenuItemViewModel extends ViewModel {

    private MutableLiveData<List<MenuItem>> mMenuItem;
    private MenuItemRepo mRepo;

    public void init(){
        if(mRepo != null){
            return;
        }
        mRepo = MenuItemRepo.getInstance();
        mMenuItem = mRepo.getMenuItems();
        List<MenuItem> currentItems = mMenuItem.getValue();
        mMenuItem.postValue(currentItems);
    }

    public void addMenuItem(MenuItem newItem, Context context){
        mRepo.addMenuItem(newItem, context);
    }
    public LiveData<List<MenuItem>> getMenuItems() { return mMenuItem; }

}