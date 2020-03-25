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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


public class MenuViewModel extends ViewModel {

    public static final String TAG = "MenuViewModel";
    private MutableLiveData<List<MenuItem>> mMenuItems;
    private MenuItemRepo mRepo;

    public void init(){
        mRepo = MenuItemRepo.getInstance();
        mMenuItems = mRepo.getMenuItems();

        //sleep for firestore to catch up
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                List<MenuItem> currentItems = mMenuItems.getValue();
                Collections.sort(currentItems);
                mMenuItems.postValue(currentItems);

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

    public void clearMenuItems(){

        List<MenuItem> currentList = mMenuItems.getValue();
        for (MenuItem menuItem : currentList){
            menuItem.setmIntQuantity(0);
        }

        List<MenuItem> oldList = mRepo.getMenuItems().getValue();

        List<String> currentListTitles = new ArrayList<>();
        currentList.forEach(menuItem -> currentListTitles.add(menuItem.getItemName()));


        for(MenuItem m : oldList){
            if(!currentList.contains(m.getItemName())) currentList.add(m);
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
            currentList.addAll(Objects.requireNonNull(mRepo.getOriginalMenuItems()));
            mMenuItems.postValue(currentList);
        }
        else {
            List<MenuItem> currentList = new ArrayList<>(mRepo.getOriginalMenuItems());
            Iterator<MenuItem> iterator = currentList.iterator();

            Collections.sort(tags);

            while (iterator.hasNext()) {
               MenuItem menuItem = iterator.next();

               List<String> t = new ArrayList<>(menuItem.getTags());
               t.retainAll(tags);
               Collections.sort(t);

                    if (!t.equals(tags)) {
                        iterator.remove();
                    }
            }

            Log.d(TAG, currentList.toString());

            mMenuItems.postValue(currentList);
        }
    }

    public void checkForUpdate() {
        if(mMenuItems.getValue().size() != mRepo.getMenuItems().getValue().size()){
            mMenuItems.postValue(mRepo.getMenuItems().getValue());
        }
    }
}