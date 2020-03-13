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
        boolean found = false;

        assert currentBillMenuItems != null;
        if(currentBillMenuItems.size() == 0){
            menuItem.incrementQuantity();
            currentBillMenuItems.add(menuItem);
        }
        else{
            for (MenuItem currentBillMenuItem : currentBillMenuItems) {
                if (currentBillMenuItem.getItemName().equals(menuItem.getItemName()))
                    {
                        currentBillMenuItem.incrementQuantity();
                        found = true;
                        break;
                    }
            }
            if(!found) {
                menuItem.incrementQuantity();
                currentBillMenuItems.add(menuItem);
            }
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

    public void clearBillItems(){
        List<MenuItem> currentBillMenuItems = mMenuBillItems.getValue();
        Iterator it = currentBillMenuItems.iterator();

        while(it.hasNext()){
            ((MenuItem)it.next()).setmIntQuantity(0);
            it.remove();
        }

        mMenuBillItems.postValue(currentBillMenuItems);
    }

    public LiveData<List<MenuItem>> getBillItems(){
        return mMenuBillItems;
    }
}