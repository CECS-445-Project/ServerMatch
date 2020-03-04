/**
 * author: Howard Chen
 */
package com.example.servermatch.cecs445.ui.frequentcustomers;


import android.os.AsyncTask;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.servermatch.cecs445.models.Customer;
import com.example.servermatch.cecs445.models.MenuItem;
import com.example.servermatch.cecs445.repositories.FrequentCustomerRepo;
import com.example.servermatch.cecs445.repositories.TopMenuItemRepo;
import java.util.List;



/*
@author - Howard Chen
 */
public class FrequentCustomersViewModel extends ViewModel {
    private static final String TAG = "FrequentCustomersViewMo";
    private MutableLiveData<List<Customer>> mTopCustomers;
    private MutableLiveData<List<MenuItem>> mTopMenuItems;
    private MutableLiveData<String> mEmail;
    private FrequentCustomerRepo fCRepo;
    private TopMenuItemRepo tMIRepo;

    public void init(){
        if(fCRepo != null){
            return;
        }
        if( tMIRepo != null){
            return;
        }

        fCRepo = FrequentCustomerRepo.getInstance();
        tMIRepo = TopMenuItemRepo.getInstance();
        mTopCustomers = fCRepo.getCustomers();
        mEmail = new MutableLiveData<>();
        mEmail.setValue("howardshowered@gmail.com");

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                List<Customer> currentTopCustomers = mTopCustomers.getValue();
                mTopCustomers.postValue(currentTopCustomers);


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
                mTopMenuItems = tMIRepo.getTopItems(mEmail.getValue());
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                List<MenuItem> currentTopMenuItems = mTopMenuItems.getValue();;
                mTopMenuItems.postValue(currentTopMenuItems);


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

    }

    public void updateTopMenuItems(String email){
        List<MenuItem> currentMenuItems = tMIRepo.loadMenuItems(email);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.d(TAG, "updateTopMenuItems: "+ currentMenuItems.toString());
                mTopMenuItems.postValue(currentMenuItems);


            }

            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

    }


    public LiveData<List<MenuItem>> getTopMenuItems() { return mTopMenuItems; }

    public LiveData<List<Customer>> getTopCustomers() { return mTopCustomers; }

    public LiveData<String> getCustomerEmail() { return mEmail; }

    public void setTopCustomerEmail(String email){
        mEmail.setValue(email);
    }
}