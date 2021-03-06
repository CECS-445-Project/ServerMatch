package com.example.servermatch.cecs445.repositories;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.example.servermatch.cecs445.models.MenuItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Howard
 */
public class TopMenuItemRepo {
    private static final String TAG = "TopMenuItemRepo";
    private static TopMenuItemRepo instance;
    private ArrayList<MenuItem> dataSet = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String currentUserEmail;
    private FirebaseUser currentUser = mAuth.getCurrentUser();
    private DocumentReference restaurantRef = db.collection("Restaurant").document(currentUser.getEmail());
    private CollectionReference collRef = restaurantRef.collection("Customer");

    public static TopMenuItemRepo getInstance(){
        if(instance == null){
            instance = new TopMenuItemRepo();
        }
        return instance;
    }

    public MutableLiveData<List<MenuItem>> getTopItems(String email){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        currentUserEmail = currentUser.getEmail();
        restaurantRef = db.collection("Restaurant").document(currentUserEmail);
        collRef = restaurantRef.collection("Customer");
        dataSet.clear();
        loadMenuItems( email);
        MutableLiveData<List<MenuItem>> data = new MutableLiveData<>();
        data.setValue(dataSet);

        return data;
    }

    public ArrayList<MenuItem> loadMenuItems(String email){

        collRef.document(email).collection("MenuItems")
        .orderBy("mIntQuantity", Query.Direction.DESCENDING ).limit(5).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            dataSet.add(documentSnapshot.toObject(MenuItem.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: "+ e.toString() );
            }
        });


        return dataSet;
    }



}
