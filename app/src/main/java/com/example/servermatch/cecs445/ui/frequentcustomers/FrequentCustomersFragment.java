/*
  author: Howard Chen
 */
package com.example.servermatch.cecs445.ui.frequentcustomers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.servermatch.cecs445.R;
import com.example.servermatch.cecs445.models.Customer;
import com.example.servermatch.cecs445.models.MenuItem;
import com.example.servermatch.cecs445.ui.menu.BillViewModel;
import com.example.servermatch.cecs445.ui.menu.MenuViewModel;

import java.util.List;


public class FrequentCustomersFragment extends Fragment {

    private FrequentCustomersViewModel frequentCustomersViewModel;
    private FrequentCustomersAdapter frequentCustomersAdapter;
    private TopItemsAdapter topItemsAdapter;
    private RecyclerView recyclerViewFrequentCustomers;
    private RecyclerView recyclerViewTopItems;
    private static final String TAG = "FrequentCustomersFragment";
    private View view;
    private Context context;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_frequent_customers, container, false);
        frequentCustomersViewModel = new ViewModelProvider(this)
                .get(FrequentCustomersViewModel.class);
        frequentCustomersViewModel.init();
        recyclerViewFrequentCustomers =  view.findViewById(R.id.recycle_view_customers);
        recyclerViewTopItems =  view.findViewById(R.id.recycle_view_top_items);
        initRecyclerViews();

        frequentCustomersViewModel.getTopCustomers().observe(getViewLifecycleOwner(), new Observer<List<Customer>>() {

            @Override
            public void onChanged(List<Customer> customers) {
                frequentCustomersAdapter.notifyDataSetChanged();
            }
        });

        frequentCustomersViewModel.getCustomerEmail().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                topItemsAdapter.notifyDataSetChanged();
            }

        });

        frequentCustomersViewModel.getTopMenuItems().observe(getViewLifecycleOwner(), new Observer<List<MenuItem>>() {
            @Override
            public void onChanged(List<MenuItem> menuItems) {
                topItemsAdapter.notifyDataSetChanged();
            }

        });

        context = this.getContext();

        return view;
    }


    private void initRecyclerViews(){
        //Frequent Customers
       frequentCustomersAdapter = new FrequentCustomersAdapter(frequentCustomersViewModel, getActivity(), frequentCustomersViewModel.getTopCustomers().getValue());
        recyclerViewFrequentCustomers.setAdapter(frequentCustomersAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewFrequentCustomers.setLayoutManager(layoutManager);

//        TopMenuItems
        topItemsAdapter = new TopItemsAdapter(new ViewModelProvider(this.getActivity()).get(BillViewModel.class) , frequentCustomersViewModel, getActivity(), frequentCustomersViewModel.getTopMenuItems().getValue());
        RecyclerView.LayoutManager topItemsLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewTopItems.setLayoutManager(topItemsLayoutManager);
        recyclerViewTopItems.setAdapter(topItemsAdapter);

    }


}