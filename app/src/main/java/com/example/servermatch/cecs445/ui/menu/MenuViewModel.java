package com.example.servermatch.cecs445.ui.menu;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.servermatch.cecs445.models.MenuItem;
import com.example.servermatch.cecs445.repositories.MenuItemRepo;

import java.util.List;

public class MenuViewModel extends ViewModel {

    public static final String TAG = "MenuViewModel";
    private MutableLiveData<List<MenuItem>> mMenuItems;
    private MenuItemRepo mRepo;

    public void init(){
        if(mMenuItems != null){
            return;
        }
        mRepo = MenuItemRepo.getInstance();
        mMenuItems = mRepo.getMenuItems();
        // We are getting double items because the repo keeps adding items
        Log.d(TAG + ":End of init", "size of list" + mMenuItems.getValue().size() + "");
    }

    //todo: add the asynctask
    public void addNewValue(final MenuItem menuItem){
        List<MenuItem> currentMenuItems = mMenuItems.getValue();
        currentMenuItems.add(menuItem);
        mMenuItems.postValue(currentMenuItems);

        // For Debugging
        Log.d(TAG + ":addNewValue", "size of list" + mMenuItems.getValue().size() + "");
        Log.d(TAG + ":addNewValue", "size of list" + mMenuItems.getValue().toString() + "");
    }

    public LiveData<List<MenuItem>> getMenuItems(){
        return mMenuItems;
    }
}