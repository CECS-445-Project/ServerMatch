package com.example.servermatch.cecs445.ui.addmenuitem;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.servermatch.cecs445.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputLayout;

public class AddMenuItemFragment extends Fragment {

    private TextInputLayout textInputItemName;
    private TextInputLayout textInputItemCost;
    private TextInputLayout textInputItemDesc;
    private MaterialButton btnAddNewItem;
    private MaterialButton btnAddPhoto;
    private static final String TAG = "AddMenuItemFragment";
    private ChipGroup chipGroup;
    String [] filters = {
            "Vegan",
            "Vegetarian",
            "Dairy Free",
            "Gluten Free",
            "Soy Free",
            "Low-Carb",
            "High Protein",
            "Spicy",
            "Contains Eggs",
            "Contains Dairy",
            "Contains Nuts",
            "Contains Fish",
            "Contains Beef",
            "Contains Poultry",
            "Contains Pork"
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_menu_item, container, false);
        textInputItemName = root.findViewById(R.id.text_input_item_name);
        textInputItemCost = root.findViewById(R.id.text_input_item_cost);
        textInputItemDesc = root.findViewById(R.id.text_input_item_desc);
        btnAddNewItem = root.findViewById((R.id.add_item_button));
        btnAddPhoto = root.findViewById((R.id.add_photo_button));



        // TODO: add selected chips to list of strings
        btnAddNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chip_msg = "Selected chips are: ";
                int chipCount = chipGroup.getChildCount();
                if(chipCount == 0) {
                    chip_msg += "None.";
                } else {
                    int i = 0;
                    while(i < chipCount) {
                        Chip chip = (Chip) chipGroup.getChildAt(i);
                        if(chip.isChecked()) {
                            chip_msg += chip.getText().toString() + ", ";
                        }
                        i++;
                    }
                }
                Log.d(TAG, chip_msg);
                addItem(v);
            }
        });

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhoto(v);
            }
        });

        chipGroup = root.findViewById(R.id.chip_group_filter);
        for(String filter : filters) {
            Chip chip = new Chip(getContext());
            ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(getContext(), null, 0, R.style.CustomChipChoice);
            chip.setChipDrawable(chipDrawable);
            chip.setText(filter);
            chip.setClickable(true);
            chipGroup.addView(chip);
        }

        return root;
    }

    private boolean validateName() {
        String nameInput = textInputItemName.getEditText().getText().toString().trim();

        if(nameInput.isEmpty()) {
            textInputItemName.setError("Field can't be empty");
            Log.d(TAG, "Item name is empty");
            return false;
        } else {
            textInputItemName.setError(null);
            return true;
        }
    }

    private boolean validateCost() {
        String costInput = textInputItemCost.getEditText().getText().toString().trim();

        if(costInput.isEmpty()) {
            textInputItemCost.setError("Field can't be empty");
            Log.d(TAG, "Item cost is empty");
            return false;
        } else {
            textInputItemCost.setError(null);
            return true;
        }
    }

    private boolean validateDesc() {
        String descInput = textInputItemDesc.getEditText().getText().toString().trim();

        if(descInput.isEmpty()) {
            textInputItemDesc.setError("Field can't be empty");
            Log.d(TAG, "Item description is empty");
            return false;
        } else if (descInput.length() > 200) {
            textInputItemDesc.setError("Description too long");
            Log.d(TAG, "Item description is too long");
            return false;
        } else {
            textInputItemDesc.setError(null);
            return true;
        }
    }

    private boolean validateFilters() {
        int chipCount = chipGroup.getChildCount();
        if(chipCount == 0) {
            return false;
        } else {
            int i = 0;
            while (i < chipCount) {
                Chip chip = (Chip) chipGroup.getChildAt(i);
                if (chip.isChecked()) {
                    return true;
                }
                i++;
            }
        }
        Log.d(TAG, "No filters selected");
        Toast.makeText(getContext(), "No filters selected", Toast.LENGTH_SHORT).show();
        return false;
    }

    //TODO: add new menu item here
    public void addItem(View v) {
        if(!validateName() | !validateCost() | !validateDesc() | !validateFilters()) {
            return;
        }
        String input = "Name: " + textInputItemName.getEditText().getText().toString();
        input += " ";
        input += "Cost: " + textInputItemCost.getEditText().getText().toString();
        input += " ";
        input += "Description: " + textInputItemDesc.getEditText().getText().toString();
        Log.d(TAG, "Menu Item Added " + input);
        Toast.makeText(getContext(), "Menu Item Added", Toast.LENGTH_SHORT).show();
    }

    //TODO: photo permissions
    public void addPhoto(View v) {
        Log.d(TAG, "Photo Button Pressed");
        Toast.makeText(getContext(), "Add Photo", Toast.LENGTH_SHORT).show();
    }
}