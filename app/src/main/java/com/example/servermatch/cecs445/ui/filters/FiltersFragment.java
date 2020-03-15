/**
 * Andrew Delgado
 */
package com.example.servermatch.cecs445.ui.filters;

import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.SortedList;

import com.example.servermatch.cecs445.R;
import com.example.servermatch.cecs445.models.MenuItem;
import com.example.servermatch.cecs445.repositories.MenuItemRepo;
import com.example.servermatch.cecs445.ui.menu.MenuFragment;
import com.example.servermatch.cecs445.ui.menu.MenuViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.core.utilities.Tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class FiltersFragment extends Fragment {

    private static final String TAG = "FiltersFragment";

    private View view;
    private ChipGroup mChipGroup;
    private List<String> filters;
    private Button mDone;
    private Boolean receivedTags = false;

    private MenuViewModel mMenuViewModel;
    private final List<MenuItem> menuItems = MenuItemRepo.getInstance().getOriginalMenuItems();
    private List<String> selectedTags = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_filters, container,false);
        mChipGroup = view.findViewById(R.id.chip_group_filters);
        mDone = view.findViewById(R.id.filter_done);
        mMenuViewModel = new ViewModelProvider(this.getActivity()).get(MenuViewModel.class);

        //setTags();
        initChipGroup();
        doneButtonListener();

        return view;
    }

    private void initChipGroup(){
            SortedSet<String> tags = new TreeSet<>();

            for(MenuItem m : menuItems){
                tags.addAll(m.getTags());
            }

            Log.d(TAG, tags.toString());


                for (String s : tags) {
                    Chip chip = new Chip(getContext());
                    ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(getContext(), null, 0, R.style.CustomChipChoice);
                    chip.setChipDrawable(chipDrawable);
                    chip.setText(s);

                    if(selectedTags != null){
                        if(selectedTags.contains(s)) chip.setChecked(true);
                    }

                    chip.setClickable(true);
                    mChipGroup.addView(chip);
                }
    }

    private void doneButtonListener(){
        mDone.setOnClickListener(v->{
            ArrayList<String> tags = getSelectedTags();
            mMenuViewModel.setItems(tags);

            Log.d(TAG + " Selected Tags::", tags.toString());
            MenuFragment menuFragment = new MenuFragment();

// Work on for keeping tags highlighted
//            Bundle bundle = new Bundle();
//            bundle.putStringArrayList("tags", (ArrayList<String>) selectedTags);
//            menuFragment.setArguments(bundle);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_down, R.anim.slide_in_up,R.anim.slide_out_down);
            transaction.replace(R.id.nav_host_fragment, menuFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }

    private ArrayList<String> getSelectedTags(){
        if(mChipGroup.getChildCount() > 0) {

            int chipsClicked = mChipGroup.getChildCount();
            Chip chip;

            for (int i = 0; i < chipsClicked; i++) {
                chip = (Chip) mChipGroup.getChildAt(i);
                if (chip.isChecked()) selectedTags.add(chip.getText().toString());
            }
        }
        Log.d(TAG, selectedTags.toString());

        return (ArrayList<String>) selectedTags;
    }

// Work on for keeping tags highlighted
//    private void setTags(){
//        Bundle bundle = getArguments();
//        if(bundle != null){
//            selectedTags = bundle.getStringArrayList("tags");
//        }
//    }
}
