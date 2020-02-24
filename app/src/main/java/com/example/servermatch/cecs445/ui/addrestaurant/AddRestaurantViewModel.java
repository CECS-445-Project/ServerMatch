package com.example.servermatch.cecs445.ui.addrestaurant;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.servermatch.cecs445.models.Restaurant;

import java.util.List;

public class AddRestaurantViewModel extends ViewModel {
    private MutableLiveData<List<Restaurant>> mRestaurant;

    public void addRestaurant(Restaurant newRestaurant){
        List<Restaurant> currentRestaurant = mRestaurant.getValue();
        currentRestaurant.add(newRestaurant);
        mRestaurant.postValue(currentRestaurant);
    }

    public MutableLiveData<List<Restaurant>> getmRestaurant() {
        return mRestaurant;
    }
}
