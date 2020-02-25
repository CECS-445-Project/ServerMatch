package com.example.servermatch.cecs445.ui.checkout;
/*
@author - Howard Chen
 */
import android.content.Context;
import android.os.AsyncTask;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.servermatch.cecs445.models.Bill;
import com.example.servermatch.cecs445.models.Customer;
import com.example.servermatch.cecs445.repositories.CustomerRepo;
import java.util.List;

public class CheckoutViewModel extends ViewModel {

    private MutableLiveData<List<Customer>> mCustomers;
    private CustomerRepo cRepo;

    public void init(){
        if(cRepo != null){
            return;
        }
        cRepo = CustomerRepo.getInstance();
        mCustomers = cRepo.getCustomers();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                List<Customer> currentTopCustomers = mCustomers.getValue();
                mCustomers.postValue(currentTopCustomers);
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

    public void checkOutCustomer(Context context, Bill bill, String checkOutTime){

        cRepo.checkOutCustomer(context, bill, checkOutTime);

    }

    public MutableLiveData<List<Customer>> getmCustomers() {
        return mCustomers;
    }

}
