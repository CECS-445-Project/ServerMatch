package com.example.servermatch.cecs445.repositories;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.servermatch.cecs445.models.Bill;
import com.example.servermatch.cecs445.models.Customer;
import com.example.servermatch.cecs445.models.MenuItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Howard
 */
public class CustomerRepo {

    private static final String TAG = "CustomerRepo";
    private static CustomerRepo instance;
    private ArrayList<Customer> dataSet = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private  final CollectionReference customerRef =
            db.collection("Customer");
    private  final CollectionReference billRef =
            db.collection("Bill");

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
        customerRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
//        customerRef.add(newCustomer).addOnSuccessListener
//                (new OnSuccessListener<DocumentReference>() {
//                    @Override
//            public void onSuccess(DocumentReference documentReference) {
//                //Where Toast would go for success
//                        //still needs to figure out how to Toast from Repo Class.
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.e(TAG, "onFailure: " + e.toString());
//            }
//        });

        customerRef.document(newCustomer.getEmail()).set(newCustomer)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        HashMap<String, Integer> itemMap = new HashMap<>();
        MenuItemRepo mRepo = MenuItemRepo.getInstance();
        List<MenuItem> menuItemList = mRepo.getMenuItems().getValue();
        for(MenuItem menuItem: menuItemList){
            itemMap.put(menuItem.getDocumentId(), 0);
        }
        billRef.document(newCustomer.getEmail()).set(itemMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: " + e.toString());
            }
        });
    }

    public void checkOutCustomer(Context context, Customer customer,
                                 List<MenuItem> billItems) {

        //increments visit
        String email  = customer.getEmail();
        customerRef.document(email)
                .set(customer).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Visit updated!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: " + e.toString());
            }
        });

        for(MenuItem item: billItems){
            //need to check if menuItem has been ordered before or not.
            billRef.document(email).update(item.getDocumentId(), FieldValue.increment(item.getmIntQuantity()));
        }

    }
}
