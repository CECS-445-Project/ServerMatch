/**
 * author: Howard Chen
 */
package com.example.servermatch.cecs445.ui.frequentcustomers;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.servermatch.cecs445.models.Customer;

import com.example.servermatch.cecs445.repositories.FrequentCustomerRepo;


import java.util.List;

public class FrequentCustomersViewModel extends ViewModel {

    private MutableLiveData<List<Customer>> mTopCustomers;
    private FrequentCustomerRepo fCRepo;


    public void init(){
        if(fCRepo != null){
            return;
        }
        fCRepo = FrequentCustomerRepo.getInstance();
        mTopCustomers = fCRepo.getCustomers();

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
        List<Customer> currentTopCustomers = mTopCustomers.getValue();
        mTopCustomers.postValue(currentTopCustomers);
    }


    public LiveData<List<Customer>> getTopCustomers() { return mTopCustomers; }
}