package com.example.servermatch.cecs445.repositories;

import android.content.Context;
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
    private boolean incrementedVisit;

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

    public void addCustomer(Customer newCustomer){
        //stores new customer with email as document id.
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

    }

    public void checkOutCustomer(Context context, Bill bill, String checkoutTime) {

        //increments visit
        String email  = bill.getCustomerID();
        incrementVisit(context, email);
        storeBill(context, email, bill, checkoutTime);

    }

    private void incrementVisit(Context context, String email){

        customerRef.document(email)
                .update("visits", FieldValue.increment(1))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                incrementedVisit = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: " + e.toString() );
                incrementedVisit = false;
            }
        });

    }

    private void storeBill(Context context, String email, Bill bill, String checkoutTime){

        String collectionName;
        String toastMsg;
        Map<String, Object> billInformation = new HashMap<>();
        billInformation.put("TransactionDateTime:", checkoutTime);
        List<MenuItem> billItems = bill.getBillItems();
        for (MenuItem menuItem : billItems) {
            String menuItemName = menuItem.getItemName();
            Integer menuItemQty = menuItem.getmIntQuantity();
            billInformation.put(menuItemName, menuItemQty);
        }

        if(checkEmail(email)) {
            collectionName = "Customer";
            toastMsg = "as a register customer";
        }else{
            collectionName = "Guest";
            toastMsg = "as a guest";
        }

        db.collection(collectionName).document(email).collection("Bills").document(checkoutTime)
                .set(billInformation).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Checkout Completed for " + email + "\n@" + checkoutTime + toastMsg, Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: " + e.toString());
            }
        });
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
        });
    }

}
