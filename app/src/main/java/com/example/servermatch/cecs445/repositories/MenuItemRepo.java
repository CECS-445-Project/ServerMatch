/**
 * @author Andrew Delgado
 */
package com.example.servermatch.cecs445.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import com.example.servermatch.cecs445.models.MenuItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

/**
 * SingleTon Pattern
 */
public class MenuItemRepo {

    private static MenuItemRepo instance;
    private ArrayList<MenuItem> dataSet = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "MenuItemRepo";
    private boolean menuItemInitialized = false;
    private List<MenuItem> originalItems;

    public static MenuItemRepo getInstance(){
        if(instance == null){
            instance = new MenuItemRepo();
            return instance;
        }
        return instance;
    }

    public MutableLiveData<List<MenuItem>> getMenuItems(){
        if(dataSet.isEmpty()) loadMenuItems();
        MutableLiveData<List<MenuItem>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void loadMenuItems(){
        collectionListener("MenuItem");
    }

    public void addMenuItem(MenuItem newItem, final Context context){
        final String itemName = newItem.getItemName();
        db.collection("MenuItem").add(newItem).addOnSuccessListener
                (new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(context, itemName +" Added!", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed to add " + itemName +"\n Please check mobile connection!", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + e.toString());
            }
        });

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
                            dataSet.clear();
                            for (DocumentSnapshot documentSnapshot : list) {
                                if(dataSet.contains(dataSet.add(documentSnapshot.toObject(MenuItem.class)))) {
                                    dataSet.add(documentSnapshot.toObject(MenuItem.class));
                                }
                            }
                            menuItemInitialized = true;
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


    public List<MenuItem> getOriginalMenuItems() {
        if(originalItems == null) originalItems = new ArrayList<>(dataSet);
        return originalItems;
    }
}
