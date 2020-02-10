package com.example.servermatch.cecs445.ui.menu;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.servermatch.cecs445.models.MenuItem;
import com.example.servermatch.cecs445.repositories.MenuItemRepo;

import java.util.List;

public class MenuViewModel extends ViewModel {

    private MutableLiveData<List<MenuItem>> mMenuItems;
    private MenuItemRepo mRepo;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();

    public void init(){
        if(mMenuItems != null){
            return;
        }
        mRepo = MenuItemRepo.getInstance();
        mMenuItems = mRepo.getMenuItems();
    }

    //todo: add the asynctask
    public void addNewValue(final MenuItem menuItem){
                List<MenuItem> currentMenuItems = mMenuItems.getValue();
                currentMenuItems.add(menuItem);
                mMenuItems.postValue(currentMenuItems);
    }

    public LiveData<List<MenuItem>> getMenuItems(){
        return mMenuItems;
    }
}