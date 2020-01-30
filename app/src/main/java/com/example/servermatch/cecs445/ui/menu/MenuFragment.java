package com.example.servermatch.cecs445.ui.menu;

import android.database.DataSetObserver;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.servermatch.cecs445.R;
import com.example.servermatch.cecs445.Utils.ListAdapter;

public class MenuFragment extends Fragment {

    private MenuViewModel menuViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu,container,false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_menu);

        ListAdapter listAdapter = new ListAdapter();
        recyclerView.setAdapter(listAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }
}

//menuViewModel =
//        ViewModelProviders.of(this).get(MenuViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_menu, container, false);
//final TextView textView = root.findViewById(R.id.text_menu);
//        menuViewModel.getText().observe(this, new Observer<String>() {
//@Override
//public void onChanged(@Nullable String s) {
//        textView.setText(s);
//        }
//        });
//        return root;