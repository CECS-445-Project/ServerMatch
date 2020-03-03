/**
 * Andrew Delgado
 */
package com.example.servermatch.cecs445.ui.filters;

import android.os.Bundle;
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

import com.example.servermatch.cecs445.R;
import com.example.servermatch.cecs445.ui.menu.MenuFragment;
import com.example.servermatch.cecs445.ui.menu.MenuViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FiltersFragment extends Fragment {

    private static final String TAG = "FiltersFragment";

    private View view;
    private ChipGroup mChipGroup;
    private List<String> filters;
    private Button mDone;
    private Boolean receivedTags = false;

    private MenuViewModel mMenuViewModel;
    //private List<String> selectedTags;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_filters, container,false);
        mChipGroup = view.findViewById(R.id.chip_group_filters);
        mDone = view.findViewById(R.id.filter_done);
        mMenuViewModel = new ViewModelProvider(this.getActivity()).get(MenuViewModel.class);


        initChipGroup();
        doneButtonListener();

        return view;
    }

    private void initChipGroup(){

        if(getArguments() != null && !receivedTags) {

            Bundle bundle = getArguments();

            filters = bundle.getStringArrayList("tags");
            Collections.sort(filters);

            if (filters != null) {
                for (String s : filters) {
                    Chip chip = new Chip(getContext());
                    ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(getContext(), null, 0, R.style.CustomChipChoice);
                    chip.setChipDrawable(chipDrawable);
                    chip.setText(s);
                    chip.setClickable(true);
                    mChipGroup.addView(chip);
                }
            }
        }
        receivedTags = true;
    }

    private void doneButtonListener(){
        mDone.setOnClickListener(v->{
            ArrayList<String> tags = getSelectedTags();
            mMenuViewModel.setItems(tags);
            Log.d(TAG, tags.toString());

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_down, R.anim.slide_in_up,R.anim.slide_out_down);
            transaction.replace(R.id.nav_host_fragment, new MenuFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }

    private ArrayList<String> getSelectedTags(){
        ArrayList<String> selectedTags = new ArrayList<>();
        if(mChipGroup.getChildCount() > 0) {

            int chipsClicked = mChipGroup.getChildCount();
            Chip chip;

            for (int i = 0; i < chipsClicked; i++) {
                chip = (Chip) mChipGroup.getChildAt(i);
                if (chip.isChecked()) selectedTags.add(chip.getText().toString());
            }
        }
        Log.d(TAG, selectedTags.toString());

        return selectedTags;
    }
}
