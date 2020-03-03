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
import com.example.servermatch.cecs445.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class FiltersFragment extends Fragment {

    private static final String TAG = "FiltersFragment";

    private View view;
    private ChipGroup mChipGroup;
    private List<String> filters;
    private Button mDone;
    private List<String> selectedTags;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_filters, container,false);
        mChipGroup = view.findViewById(R.id.chip_group_filters);
        mDone = view.findViewById(R.id.filter_done);

        initChipGroup();
        doneButtonListener();

        return view;
    }

    private void initChipGroup(){

        if(getArguments() != null) {

            Bundle bundle = getArguments();

            filters = bundle.getStringArrayList("tags");

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
    }

    private void doneButtonListener(){
        mDone.setOnClickListener(v->{

            selectedTags = new ArrayList<>();
            if(mChipGroup.getChildCount() > 0) {

                int chipsClicked = mChipGroup.getChildCount();
                Chip chip;

                for (int i = 0; i < chipsClicked; i++) {
                    chip = (Chip) mChipGroup.getChildAt(i);
                    if (chip.isChecked()) selectedTags.add(chip.getText().toString());
                }
            }
            Log.d(TAG, selectedTags.toString());
        });
    }

//        private String [] filter = {
//            "Vegan",
//            "Vegetarian",
//            "Dairy Free",
//            "Gluten Free",
//            "Soy Free",
//            "Low-Carb",
//            "High Protein",
//            "Spicy",
//            "Contains Eggs",
//            "Contains Dairy",
//            "Contains Nuts",
//            "Contains Fish",
//            "Contains Beef",
//            "Contains Poultry",
//            "Contains Pork"
//    };
}
