package com.example.servermatch.cecs445.ui.menu;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

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
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();

    public void init(){
        if(mMenuItems != null){
            Log.d(TAG, "size of list" + mMenuItems.getValue().size() + "");
            return;
        }
        mRepo = MenuItemRepo.getInstance();
        mMenuItems = mRepo.getMenuItems();
        Log.d(TAG, "size of list" + mMenuItems.getValue().size() + "");
    }

    //todo: add the asynctask
    public void addNewValue(final MenuItem menuItem){

        Log.d(TAG, menuItem.toString());
        List<MenuItem> currentMenuItems = mMenuItems.getValue();
        currentMenuItems.add(menuItem);
        mMenuItems.postValue(currentMenuItems);
        mIsUpdating.postValue(false);

        Log.d(TAG, "size of list" + mMenuItems.getValue().size() + "");
    }

    public LiveData<List<MenuItem>> getMenuItems(){
        return mMenuItems;
    }
}