/**
 * @author Andrew Delgado
 */

package com.example.servermatch.cecs445.ui.checkout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.servermatch.cecs445.R;
import com.example.servermatch.cecs445.Utils.CheckoutRecyclerAdapter;
import com.example.servermatch.cecs445.models.Bill;
import com.example.servermatch.cecs445.models.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class CheckoutFragment extends Fragment {

    private static String TAG = "CheckoutFragment";
    private View view;
    private Bill bill;
    private RecyclerView recyclerView;
    private TextView totalCost;
    private EditText mEmail;
    private Button mCheckoutButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_checkout2,container,false);
        recyclerView = view.findViewById(R.id.recycler_view_checkout);
        totalCost = view.findViewById(R.id.checkout_total_cost);
        mEmail = view.findViewById(R.id.checkout_email);
        mCheckoutButton = view.findViewById(R.id.checkout_button);

        checkoutButtonListener();
        setUpBill();
        initRecyclerView();


        return view;
    }

    public void checkoutButtonListener(){
        mCheckoutButton.setOnClickListener(v -> {

            bill.setCustomerID(mEmail.getText().toString());

            Log.d(TAG, mEmail.getText().toString());
            Log.d(TAG, bill.toString());
        });
    }


    public void initRecyclerView(){
        CheckoutRecyclerAdapter checkoutRecyclerAdapter = new CheckoutRecyclerAdapter(bill);
        recyclerView.setAdapter(checkoutRecyclerAdapter);
        RecyclerView.LayoutManager checkoutLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(checkoutLayoutManager);
    }


    public void setUpBill(){
        Bundle bundle = getArguments();

        if(bundle != null) {

            ArrayList<String> itemNames;
            ArrayList<String> itemQuantity;
            ArrayList<String> itemCost;
//            bill = new Bill();
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

//            bill.setMenuItems(menuItems);
//
//            bill.setTotalCost((double)bundle.get("billTotal"));

            bill = new Bill((double)bundle.get("billTotal"),menuItems);
            totalCost.setText("$" + String.format("%.2f",bundle.get("billTotal")));
        }
    }
}

//        Log.d(TAG, bundle.get("billItemNames").toString());
//        Log.d(TAG, bundle.get("billItemQuantity").toString());
//        Log.d(TAG, bundle.get("billItemCost").toString());
