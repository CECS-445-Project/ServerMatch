package com.example.servermatch.cecs445.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.servermatch.cecs445.R;
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
        return instance;
    }

    public MutableLiveData<List<MenuItem>> getMenuItems(){
        setMenuItems();
        MutableLiveData<List<MenuItem>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setMenuItems(){
        dataSet.add(new MenuItem("1 Pizza",3.50, R.drawable.ic_customer_add));
        dataSet.add(new MenuItem("2 Fries",1.00, R.drawable.ic_menu_camera));
        dataSet.add(new MenuItem("3 Burger",2.01, R.drawable.ic_menu_send));
        dataSet.add(new MenuItem("4 Cheese",3.00, R.drawable.ic_customer_add));
        dataSet.add(new MenuItem("5 Fish",4.20, R.drawable.ic_menu_camera));
        dataSet.add(new MenuItem("6 Pizza",3.50, R.drawable.ic_menu_send));
        dataSet.add(new MenuItem("7 Fries",1.00, R.drawable.ic_customer_add));
        dataSet.add(new MenuItem("8 Burger",2.01, R.drawable.ic_menu_camera));
        dataSet.add(new MenuItem("9 Cheese",3.00, R.drawable.ic_menu_send));
        dataSet.add(new MenuItem("10 Fish",4.20, R.drawable.ic_customer_add));
        dataSet.add(new MenuItem("11 Pizza",3.50, R.drawable.ic_menu_camera));
        dataSet.add(new MenuItem("12 Fries",1.00, R.drawable.ic_menu_send));
        dataSet.add(new MenuItem("13 Burger",2.01, R.drawable.ic_customer_add));
        dataSet.add(new MenuItem("14 Cheese",3.00, R.drawable.ic_menu_camera));
        dataSet.add(new MenuItem("15 Fish",4.20, R.drawable.ic_menu_send));
        dataSet.add(new MenuItem("16 Pizza",3.50, R.drawable.ic_customer_add));
        dataSet.add(new MenuItem("17 Fries",1.00, R.drawable.ic_menu_camera));
        dataSet.add(new MenuItem("18 Pizza",3.50, R.drawable.ic_menu_send));
    }

}
