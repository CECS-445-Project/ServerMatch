package com.example.servermatch.cecs445.repositories;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.servermatch.cecs445.models.Restaurant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;



/**
 * @author Howard Chen
 */
public class RestaurantRepo {

    private static final String TAG = "RestaurantRepo";
    private List<Restaurant> dataSet = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private  final CollectionReference restaurantRef =
            db.collection("Restaurant");
    private static RestaurantRepo instance;

    public static RestaurantRepo getInstance(){
        if(instance == null){
            instance = new RestaurantRepo();
        }
        return instance;
    }

    public MutableLiveData<List<Restaurant>> getRestaurants(){
        if(dataSet.isEmpty()) loadRestaurants();
        MutableLiveData<List<Restaurant>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void loadRestaurants(){
        //listens for updates on Collection in realtime
        collectionListener();
    }

    public boolean addRestaurant(Context context, Restaurant newRestaurant){
        boolean newEmail = true;
        String newRestaurantEmail = newRestaurant.getEmail();

        //checks if email is in system already.
        for(Restaurant restaurant: dataSet){
            if(restaurant.getEmail().equals(newRestaurantEmail)){
                newEmail = false;
            }
        }
        if(newEmail = true) {
            //stores new customer with email as document id.
            restaurantRef.document(newRestaurantEmail).set(newRestaurant)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, newRestaurantEmail +" registered!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(context, newRestaurantEmail +" is already registered!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private void collectionListener(){

        restaurantRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.w(TAG,"Listen Failed", e );
                    return;
                }

                restaurantRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        dataSet.clear();
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot documentSnapshot : list){
                                if(!dataSet.contains(documentSnapshot.toObject(Restaurant.class))) {
                                    dataSet.add(documentSnapshot.toObject(Restaurant.class));
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

