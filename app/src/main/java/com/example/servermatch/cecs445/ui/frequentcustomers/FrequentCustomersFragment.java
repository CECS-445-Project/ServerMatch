package com.example.servermatch.cecs445.ui.frequentcustomers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.servermatch.cecs445.R;

public class FrequentCustomersFragment extends Fragment {

    private FrequentCustomersViewModel frequentCustomersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        frequentCustomersViewModel =
                ViewModelProviders.of(this).get(FrequentCustomersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_frequent_customers, container, false);
        final TextView textView = root.findViewById(R.id.text_frequent_customers);
        frequentCustomersViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}