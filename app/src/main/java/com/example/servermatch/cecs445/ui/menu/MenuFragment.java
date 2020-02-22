/**
 * @author Andrew Delgado and Howard Chen
 */
package com.example.servermatch.cecs445.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.servermatch.cecs445.R;
import com.example.servermatch.cecs445.Utils.BillRecyclerAdapter;
import com.example.servermatch.cecs445.Utils.MenuRecyclerAdapter;
import com.example.servermatch.cecs445.models.MenuItem;
import com.example.servermatch.cecs445.ui.checkout.CheckoutFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    private MenuViewModel mMenuViewModel;
    private BillViewModel mBillViewModel;
    private MenuRecyclerAdapter mAdapter;
    private BillRecyclerAdapter billRecyclerAdapter;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewBill;
    private FloatingActionButton mFab;
    private View view;
    private Button checkoutButton;
    private double totalBill;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_menu,container,false);
        mMenuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);
        mBillViewModel = new ViewModelProvider(this).get(BillViewModel.class);
        mMenuViewModel.init();
        mBillViewModel.init();

        recyclerView = view.findViewById(R.id.recycler_view_menu);
        recyclerViewBill = view.findViewById(R.id.recycler_view_bill);
        mFab = view.findViewById(R.id.floatingActionButton);
        checkoutButton = view.findViewById(R.id.checkout);

        // This works without the recycler view. Time to add that.
        checkoutButtonListener();

        viewModelObserver();
        billViewModelObserver();
        fabActionListener();
        initRecyclerViews();

        return view;
    }

    private void viewModelObserver(){
        mMenuViewModel.getMenuItems().observe(getViewLifecycleOwner(), menuItems -> mAdapter.notifyDataSetChanged());
    }

    private void billViewModelObserver(){
        mBillViewModel.getBillItems().observe(getViewLifecycleOwner(), menuItems -> {
            billRecyclerAdapter.notifyDataSetChanged();
            totalBill = billRecyclerAdapter.updateBill();
        });
    }

    private void checkoutButtonListener(){
        checkoutButton.setOnClickListener(v -> {

            CheckoutFragment checkoutFragment = new CheckoutFragment();
            Bundle bundle = new Bundle();

            List<MenuItem> currentList = mBillViewModel.getBillItems().getValue();

            ArrayList<String> billItemNames = new ArrayList<>();
            ArrayList<String> billItemQuantity = new ArrayList<>();
            ArrayList<String> billItemCost = new ArrayList<>();

            // GetNames
            for(MenuItem m:currentList){
                billItemNames.add(m.getItemName());
            }

            // GetQuantity
            for(MenuItem m:currentList){
                billItemQuantity.add(String.valueOf(m.getQuantity()));
            }

            //Get Cost
            for(MenuItem m:currentList){
                billItemCost.add(String.valueOf(m.getItemCost()));
            }


            bundle.putStringArrayList("billItemNames", billItemNames);
            bundle.putStringArrayList("billItemQuantity", billItemQuantity);
            bundle.putStringArrayList("billItemCost", billItemCost);
            bundle.putDouble("billTotal", totalBill);
            checkoutFragment.setArguments(bundle);


            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, checkoutFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }


    private void fabActionListener(){
        mFab.setOnClickListener(v -> {
            //mMenuViewModel.addNewValue(new MenuItem("DOGS", 5.55, R.drawable.ic_menu_share));
            mMenuViewModel.removePizza();
            recyclerView.smoothScrollToPosition(mMenuViewModel.getMenuItems().getValue().size()-1);
        });
    }

    private void initRecyclerViews(){
        //Menu Items
        mAdapter = new MenuRecyclerAdapter(mMenuViewModel, mBillViewModel, getActivity(), mMenuViewModel.getMenuItems().getValue());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);

//        Bill Items
        billRecyclerAdapter = new BillRecyclerAdapter(view, mBillViewModel, mBillViewModel.getBillItems().getValue());
        recyclerViewBill.setAdapter(billRecyclerAdapter);
        RecyclerView.LayoutManager billLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewBill.setLayoutManager(billLayoutManager);
    }
}