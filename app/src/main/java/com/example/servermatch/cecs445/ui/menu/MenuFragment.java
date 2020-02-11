/**
 * @author Andrew Delgado
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
import com.example.servermatch.cecs445.utils.BillListAdapter;
import com.example.servermatch.cecs445.utils.RecyclerAdapter;
import com.example.servermatch.cecs445.models.MenuItem;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MenuFragment extends Fragment {

    private MenuViewModel mMenuViewModel;
    private BottomSheetBehavior mBottomSheetBehavior;
    private RecyclerAdapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewBill;
    private FloatingActionButton mFab;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu,container,false);

        recyclerView = view.findViewById(R.id.recycler_view_menu);
        recyclerViewBill = view.findViewById(R.id.recycler_view_bill);
        mFab = view.findViewById(R.id.floatingActionButton);

        mMenuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);

        mMenuViewModel.init();

        mMenuViewModel.getMenuItems().observe(getViewLifecycleOwner(), new Observer<List<MenuItem>>() {
            @Override
            public void onChanged(List<MenuItem> menuItems) {
                mAdapter.notifyDataSetChanged();
            }
        });

        fabActionListener();

        initRecyclerViews();

        return view;
    }

    private void fabActionListener(){
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuViewModel.addNewValue(new MenuItem("DOGS", 5.55, R.drawable.ic_menu_share));
                recyclerView.smoothScrollToPosition(mMenuViewModel.getMenuItems().getValue().size()-1);
            }
        });
    }

    private void initRecyclerViews(){
        //Menu Items
        mAdapter = new RecyclerAdapter(getActivity(), mMenuViewModel.getMenuItems().getValue());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);

        //Bill Items
        BillListAdapter billListAdapter = new BillListAdapter();
        recyclerViewBill.setAdapter(billListAdapter);
        RecyclerView.LayoutManager billLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewBill.setLayoutManager(billLayoutManager);
    }
}