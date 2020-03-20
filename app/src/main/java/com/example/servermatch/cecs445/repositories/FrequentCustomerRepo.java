package com.example.servermatch.cecs445.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import com.example.servermatch.cecs445.models.Customer;
import com.example.servermatch.cecs445.models.MenuItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Howard
 */
public class FrequentCustomerRepo {
    private static final String TAG = "FrequentCustomerRepo";
    private static FrequentCustomerRepo instance;
    private String currentUserEmail;
    private ArrayList<Customer> dataSet = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = mAuth.getCurrentUser();
    private DocumentReference restaurantRef = db.collection("Restaurant").document(currentUser.getEmail());
    private CollectionReference collRef = restaurantRef.collection("Customer");

    public static FrequentCustomerRepo getInstance(){
        if(instance == null){
            instance = new FrequentCustomerRepo();
        }
        return instance;
    }

    public MutableLiveData<List<Customer>> getCustomers(){
        currentUser = mAuth.getCurrentUser();
        currentUserEmail = currentUser.getEmail();
        restaurantRef = db.collection("Restaurant").document(currentUserEmail);
        dataSet.clear();
        loadCustomers();
        MutableLiveData<List<Customer>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void loadCustomers(){
        collRef.orderBy("visits", Query.Direction.DESCENDING ).limit(5).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            dataSet.clear();
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot documentSnapshot : list) {
                                dataSet.add(documentSnapshot.toObject(Customer.class));
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: " + e.toString());
            }
        });
    }



}


