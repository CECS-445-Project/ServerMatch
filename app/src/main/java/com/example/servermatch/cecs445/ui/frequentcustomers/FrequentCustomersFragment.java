/*
  author: Howard Chen
 */
package com.example.servermatch.cecs445.ui.frequentcustomers;

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
import java.util.List;

public class FrequentCustomersFragment extends Fragment {

    private FrequentCustomersViewModel frequentCustomersViewModel;
    private FrequentCustomersAdapter frequentCustomersAdapter;
    private RecyclerView recyclerViewFrequentCustomers;
    private RecyclerView recyclerViewTopItems;
    private static final String TAG = "FrequentCustomersFragment";
    private View view;


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

        return view;
    }


    private void initRecyclerViews(){
        //Menu Items
       frequentCustomersAdapter = new FrequentCustomersAdapter(frequentCustomersViewModel, getActivity(), frequentCustomersViewModel.getTopCustomers().getValue());
       RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewFrequentCustomers.setAdapter(frequentCustomersAdapter);
        recyclerViewFrequentCustomers.setLayoutManager(layoutManager);

    }


}