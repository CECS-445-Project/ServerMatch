/**
 * @author Andrew Delgado
 */
package com.example.servermatch.cecs445.ui.menu;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.servermatch.cecs445.models.MenuItem;
import com.example.servermatch.cecs445.repositories.MenuItemRepo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BillViewModel extends ViewModel {

    public static final String TAG = "BillViewModel";
    private MutableLiveData<List<MenuItem>> mMenuBillItems;

    public void init(){
        if(mMenuBillItems != null){
            //Log.d(TAG + ":End of init", "size of list" + mMenuBillItems.getValue().size() + "");
            return;
        }
        mMenuBillItems = new MutableLiveData<>();
        mMenuBillItems.setValue(new ArrayList<MenuItem>());
        //Log.d(TAG + ":End of init", "size of list" + mMenuBillItems.getValue().size() + "");
    }

    public void addNewValue(final MenuItem menuItem){
        List<MenuItem> currentBillMenuItems = mMenuBillItems.getValue();
        if(currentBillMenuItems.contains(menuItem)) menuItem.incrementQuantity();
        else {
            menuItem.incrementQuantity();
            currentBillMenuItems.add(menuItem);
        }
        mMenuBillItems.postValue(currentBillMenuItems);

        // For Debugging
        Log.d(TAG + ":addNewValue", "size of list" + mMenuBillItems.getValue().size() + "");
        Log.d(TAG + ":addNewValue", "size of list" + mMenuBillItems.getValue().toString() + "");
    }

    public void removeValue(final MenuItem menuItem) {
        List<MenuItem> currentBillMenuItems = mMenuBillItems.getValue();
        if(currentBillMenuItems.contains(menuItem)){
            menuItem.decrementQuantity();
            if(menuItem.getQuantity() == 0) currentBillMenuItems.remove(menuItem);
        }
        mMenuBillItems.postValue(currentBillMenuItems);
    }

    // Edit this so it removes the items from the list.
    public void clearBillItems(){
        List<MenuItem> currentBillMenuItems = mMenuBillItems.getValue();
        for(MenuItem billItem : currentBillMenuItems){
            billItem.setmIntQuantity(0);
        }
        mMenuBillItems.postValue(currentBillMenuItems);
    }

    public LiveData<List<MenuItem>> getBillItems(){
        return mMenuBillItems;
    }
}