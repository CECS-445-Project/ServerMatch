/**
 * @author Andrew Delgado and Howard Chen
 */
package com.example.servermatch.cecs445.ui.menu;

import android.os.AsyncTask;
import android.util.Log;
import android.view.Menu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.servermatch.cecs445.models.MenuItem;
import com.example.servermatch.cecs445.repositories.MenuItemRepo;

import java.util.ArrayList;
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

        Log.d(TAG + ":End of init", "size of list" + mMenuItems.getValue().size() + "");
    }

    //Probably Won't Need
    public void addNewValue(final MenuItem menuItem){
        List<MenuItem> currentMenuItems = mMenuItems.getValue();
        currentMenuItems.add(menuItem);
        mMenuItems.postValue(currentMenuItems);

        // For Debugging
        Log.d(TAG + ":addNewValue", "size of list" + mMenuItems.getValue().size() + "");
        Log.d(TAG + ":addNewValue", "size of list" + mMenuItems.getValue().toString() + "");
    }

    // Probably won't need
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

    public void clearMenuItems(){
        List<MenuItem> currentList = mMenuItems.getValue();
        for (MenuItem menuItem : currentList){
            menuItem.setmIntQuantity(0);
        }
        mMenuItems.postValue(currentList);
    }

    public LiveData<List<MenuItem>> getMenuItems(){
        return mMenuItems;
    }

    public void setItems(ArrayList<String> tags) {

        if (tags.size() == 0) {
            List<MenuItem> currentList = mMenuItems.getValue();
            currentList.clear();
            currentList.addAll(mRepo.getMenuItems().getValue());
            mMenuItems.postValue(currentList);
        }
        else {
            List<MenuItem> currentList = mMenuItems.getValue();
            Iterator<MenuItem> iterator = currentList.iterator();

            boolean found = false;

            while (iterator.hasNext()) {
                found = false;
                MenuItem menuItem = iterator.next();
                for (String s : tags) {
                    if (menuItem.getTags().contains(s)) {
                        found = true;
                    }
                }
                if (!found) iterator.remove();
            }

            Log.d(TAG, currentList.toString());

            mMenuItems.postValue(currentList);
        }
    }
}