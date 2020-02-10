package com.example.servermatch.cecs445.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.servermatch.cecs445.models.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * SingleTon Pattern
 */
public class MenuItemRepo {

    private static MenuItemRepo instance;
    private ArrayList<MenuItem> dataSet = new ArrayList<>();

    public static MenuItemRepo getInstance(){
        if(instance == null){
            instance = new MenuItemRepo();
            return instance;
        }
        else return instance;
    }

    public MutableLiveData<List<MenuItem>> getMenuItems(){
        setMenuItems();
        MutableLiveData<List<MenuItem>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setMenuItems(){
        dataSet.add(new MenuItem("Pizza",3.50));
        dataSet.add(new MenuItem("Fries",1.00));
        dataSet.add(new MenuItem("Burger",2.01));
        dataSet.add(new MenuItem("Cheese",3.00));
        dataSet.add(new MenuItem("Fish",4.20));
        dataSet.add(new MenuItem("Pizza",3.50));
        dataSet.add(new MenuItem("Fries",1.00));
        dataSet.add(new MenuItem("Burger",2.01));
        dataSet.add(new MenuItem("Cheese",3.00));
        dataSet.add(new MenuItem("Fish",4.20));
        dataSet.add(new MenuItem("Pizza",3.50));
        dataSet.add(new MenuItem("Fries",1.00));
        dataSet.add(new MenuItem("Burger",2.01));
        dataSet.add(new MenuItem("Cheese",3.00));
        dataSet.add(new MenuItem("Fish",4.20));
        dataSet.add(new MenuItem("Pizza",3.50));
        dataSet.add(new MenuItem("Fries",1.00));
        dataSet.add(new MenuItem("Pizza",3.50));
    }

}
