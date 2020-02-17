/**
 * @author Andrew Delgado and Howard Chen
 */
package com.example.servermatch.cecs445.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.servermatch.cecs445.R;
import com.example.servermatch.cecs445.Utils.BillRecyclerAdapter;
import com.example.servermatch.cecs445.Utils.MenuRecyclerAdapter;
import com.example.servermatch.cecs445.models.MenuItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

        viewModelObserver();
        billViewModelObserver();
        fabActionListener();
        initRecyclerViews();

        return view;
    }

    private void viewModelObserver(){
        mMenuViewModel.getMenuItems().observe(getViewLifecycleOwner(), new Observer<List<MenuItem>>() {
            @Override
            public void onChanged(List<MenuItem> menuItems) {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void billViewModelObserver(){
        mBillViewModel.getBillItems().observe(getViewLifecycleOwner(), new Observer<List<MenuItem>>() {
            @Override
            public void onChanged(List<MenuItem> menuItems) {
                billRecyclerAdapter.notifyDataSetChanged();
                billRecyclerAdapter.updateBill();
            }
        });
    }


    private void fabActionListener(){
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mMenuViewModel.addNewValue(new MenuItem("DOGS", 5.55, R.drawable.ic_menu_share));
                mMenuViewModel.removePizza();
                recyclerView.smoothScrollToPosition(mMenuViewModel.getMenuItems().getValue().size()-1);
            }
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