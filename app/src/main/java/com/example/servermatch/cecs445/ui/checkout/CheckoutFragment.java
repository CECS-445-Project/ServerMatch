package com.example.servermatch.cecs445.ui.checkout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.servermatch.cecs445.R;
import com.example.servermatch.cecs445.models.Bill;
import com.example.servermatch.cecs445.models.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class CheckoutFragment extends Fragment {

    private static String TAG = "CheckoutFragment";
    private View view;
    private Bill bill;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_checkout,container,false);

        setUpBill();

        Log.d(TAG, bill.toString());

        return view;
    }



    public void setUpBill(){
        Bundle bundle = getArguments();

        if(bundle != null) {

            ArrayList<String> itemNames;
            ArrayList<String> itemQuantity;
            ArrayList<String> itemCost;
            bill = new Bill();
            List<MenuItem> menuItems = new ArrayList<>();

            itemNames = (ArrayList) bundle.get("billItemNames");
            itemQuantity = (ArrayList) bundle.get("billItemQuantity");
            itemCost = (ArrayList) bundle.get("billItemCost");

            for (int i = 0; i < itemNames.size(); i++) {
                menuItems.add(new MenuItem(
                        itemNames.get(i),
                        Integer.parseInt(itemQuantity.get(i)),
                        Double.parseDouble(itemCost.get(i))));
            }

            bill.setMenuItems(menuItems);
        }
    }
}

//        Log.d(TAG, bundle.get("billItemNames").toString());
//        Log.d(TAG, bundle.get("billItemQuantity").toString());
//        Log.d(TAG, bundle.get("billItemCost").toString());
