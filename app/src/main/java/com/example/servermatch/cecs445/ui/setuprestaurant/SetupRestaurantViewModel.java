package com.example.servermatch.cecs445.ui.setuprestaurant;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.servermatch.cecs445.models.Restaurant;
import com.example.servermatch.cecs445.repositories.RestaurantRepo;

import java.util.List;

public class SetupRestaurantViewModel extends ViewModel {
    private static final String TAG = "SetupRestaurantViewMode";
    private MutableLiveData<List<Restaurant>> mRestaurants;
    private RestaurantRepo rRepo;

    public void init(){
        if(rRepo != null) {
            return;
        }

        rRepo = RestaurantRepo.getInstance();
        mRestaurants = rRepo.getRestaurants();
        List<Restaurant> currentRestaurants = mRestaurants.getValue();
        mRestaurants.postValue(currentRestaurants);
    }

    public MutableLiveData<List<Restaurant>> getmRestaurants() {
        return mRestaurants;
    }

    public void addRestaurant(Context context, Restaurant restaurant){
        rRepo.addRestaurant(context, restaurant);
    }
}
