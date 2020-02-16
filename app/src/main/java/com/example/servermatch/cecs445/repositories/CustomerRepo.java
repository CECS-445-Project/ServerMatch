package com.example.servermatch.cecs445.repositories;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.servermatch.cecs445.models.Customer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Howard
 */
public class CustomerRepo {

    private static CustomerRepo instance;
    private ArrayList<Customer> dataSet = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "CustomerRepo";

    public static CustomerRepo getInstance(){
        if(instance == null){
            instance = new CustomerRepo();
        }
        return instance;
    }

    public MutableLiveData<List<Customer>> getCustomers(){
        if(dataSet.isEmpty()) loadCustomers();
        MutableLiveData<List<Customer>> data = new MutableLiveData<>();
        data.setValue(dataSet);

        return data;
    }

    private void loadCustomers(){
        db.collection("Customer").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                    for(DocumentSnapshot documentSnapshot : list){
                        dataSet.add(documentSnapshot.toObject(Customer.class));
                    }
                }

                Log.e(TAG, "onSuccess: added" );

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: " + e.toString());
            }
        });
    }

    public void addCustomer(Customer newCustomer){
        db.collection("Customer").add(newCustomer).addOnSuccessListener
                (new OnSuccessListener<DocumentReference>() {
                    @Override
            public void onSuccess(DocumentReference documentReference) {
                //Where Toast would go for success
                        //still needs to figure out how to Toast from Repo Class.
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: " + e.toString());
            }
        });
    }


}
