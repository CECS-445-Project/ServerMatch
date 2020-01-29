package com.example.servermatch.cecs445.ui.frequentcustomers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.servermatch.cecs445.R;

import java.util.ArrayList;

public class FrequentCustomersFragment extends Fragment {

    private FrequentCustomersViewModel frequentCustomersViewModel;
    private static final String TAG = "FrequentCustomersFragment";
    private ArrayList<String> mCustomerNames = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        frequentCustomersViewModel =
                ViewModelProviders.of(this).get(FrequentCustomersViewModel.class);

        View root = inflater.inflate(R.layout.fragment_frequent_customers, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycle_view_customers);

        //hardcoded frequent customers for now
        mCustomerNames.add("Jane Doe");
        mCustomerNames.add("Kevin Lee");
        mCustomerNames.add("Ryan Brown");
        mCustomerNames.add("Sam Garcia");
        mCustomerNames.add("Cynthia Ryan");
        mCustomerNames.add("John Kim");
        FrequentCustomersAdapter mAdapter = new FrequentCustomersAdapter(mCustomerNames, this.getContext());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;
    }


}