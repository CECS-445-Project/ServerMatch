package com.example.servermatch.cecs445.repositories;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import com.example.servermatch.cecs445.models.Bill;
import com.example.servermatch.cecs445.models.Customer;
import com.example.servermatch.cecs445.models.MenuItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Howard Chen
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
        //listens for updates on Collection in realtime
        collectionListener("Customer");
    }

    public boolean addCustomer(Customer newCustomer, Context context){
        boolean newEmail = true;
        String newCustomerEmail = newCustomer.getEmail();
        //checks if email is in system already.
        for(Customer customer: dataSet){
            if(customer.getEmail().equals(newCustomerEmail)){
                newEmail = false;
            }
        }
        if(newEmail = true) {
            //stores new customer with email as document id.
            customerRef.document(newCustomer.getEmail()).set(newCustomer)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, newCustomerEmail +" registered!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onFailure: "+ e.toString() );
                }
            });
            return true;
        }else{
            Toast.makeText(context, newCustomerEmail +" is already registered!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean checkOutCustomer(Context context, Bill bill, String checkoutTime) {

        //increments visit
        String email  = bill.getCustomerID();
        incrementVisit(context, email);
        return storeBill(context, email, bill, checkoutTime);
    }

    private void incrementVisit(Context context, String email){

        customerRef.document(email)
                .update("visits", FieldValue.increment(1))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: " + e.toString() );
            }
        });

    }

    private boolean storeBill(Context context, String email, Bill bill, String checkoutTime){

        String collectionName;
        Map<String, Object> billInformation = new HashMap<>();
        billInformation.put("TransactionDateTime:", checkoutTime);
        List<MenuItem> billItems = bill.getBillItems();
        for (MenuItem menuItem : billItems) {
            String menuItemId =  menuItem.getItemName();
            Integer menuItemQty = menuItem.getmIntQuantity();
            billInformation.put(menuItemId, menuItemQty);
        }

        if(checkEmail(email)) {
            collectionName = "Customer";
        }else{
            collectionName = "Guest";
        }

        db.collection(collectionName).document(email).collection("Bills").document(checkoutTime)
                .set(billInformation).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Checkout Completed.", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: " + e.toString());

            }
        });
        //add to MenuItems List
        for(MenuItem menuItem : billItems) {
            db.collection(collectionName).document(email).collection("MenuItems")
                    .document(menuItem.getItemName())
                    .update("mIntQuantity", FieldValue.increment(menuItem.getmIntQuantity()))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    db.collection(collectionName).document(email)
                            .collection("MenuItems").document(menuItem.getItemName())
                            .set(menuItem);
                    Log.e(TAG, "onFailure: " + e.toString() );
                }
            });
        }

        return true;


    }

    private boolean checkEmail(String email){
        loadCustomers(); //loads dataset with new query
        for(Customer customer : dataSet){
            if(customer.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    private void collectionListener(String collection){

        db.collection(collection).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.w(TAG,"Listen Failed", e );
                    return;
                }

                db.collection(collection).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot documentSnapshot : list){
                                if(!dataSet.contains(documentSnapshot.toObject(Customer.class))) {
                                    dataSet.add(documentSnapshot.toObject(Customer.class));
                                }
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
        });
    }


}
