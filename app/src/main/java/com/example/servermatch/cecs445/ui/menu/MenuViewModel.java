/**
 * @author Andrew Delgado and Howard Chen
 */
package com.example.servermatch.cecs445.ui.menu;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.servermatch.cecs445.models.MenuItem;
import com.example.servermatch.cecs445.repositories.MenuItemRepo;

import java.util.Iterator;
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

        //sleep for firestore to catch up
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                //super.onPostExecute(aVoid);
                List<MenuItem> curentItems = mMenuItems.getValue();
                mMenuItems.postValue(curentItems);

            }

            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

        // We are getting double items because the repo keeps adding items
        Log.d(TAG + ":End of init", "size of list" + mMenuItems.getValue().size() + "");
    }

    public void addNewValue(final MenuItem menuItem){
        List<MenuItem> currentMenuItems = mMenuItems.getValue();
        currentMenuItems.add(menuItem);
        mMenuItems.postValue(currentMenuItems);

        // For Debugging
        Log.d(TAG + ":addNewValue", "size of list" + mMenuItems.getValue().size() + "");
        Log.d(TAG + ":addNewValue", "size of list" + mMenuItems.getValue().toString() + "");
    }

    public void removePizza(){
        List<MenuItem> currentMenuItems = mMenuItems.getValue();
        for(Iterator<MenuItem> j = currentMenuItems.iterator(); j.hasNext();){

            MenuItem temp = j.next();
            if(temp.getItemName().contains("Pizza")){
                j.remove();
            }
        }
        mMenuItems.postValue(currentMenuItems);
    }

    public LiveData<List<MenuItem>> getMenuItems(){
        return mMenuItems;
    }
}