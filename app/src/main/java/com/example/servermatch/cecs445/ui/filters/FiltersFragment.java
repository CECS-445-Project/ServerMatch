package com.example.servermatch.cecs445.ui.filters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.servermatch.cecs445.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class FiltersFragment extends Fragment {

    private View view;
    private ChipGroup mChipGroup;
    private List<String> filters;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_filters, container,false);
        mChipGroup = view.findViewById(R.id.chip_group_filters);

        Chip c = (Chip) mChipGroup.getCheckedChipId();

        initChipGroup();
        return view;
    }

    private void initChipGroup(){

        for(String s: filters){
            Chip chip = new Chip(getContext());
            ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(getContext(), null, 0, R.style.CustomChipChoice);
            chip.setChipDrawable(chipDrawable);
            chip.setText(s);
            chip.setClickable(true);
            mChipGroup.addView(chip);
        }
    }
}

//    private String [] filter = {
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
