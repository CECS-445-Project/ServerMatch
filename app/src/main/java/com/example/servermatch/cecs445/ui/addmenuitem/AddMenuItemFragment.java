package com.example.servermatch.cecs445.ui.addmenuitem;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.servermatch.cecs445.R;
import com.example.servermatch.cecs445.models.MenuItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AddMenuItemFragment extends Fragment {

    private TextInputLayout textInputItemName;
    private TextInputLayout textInputItemCost;
    private TextInputLayout textInputItemDesc;
    private MaterialButton btnAddNewItem;
    private MaterialButton btnAddPhoto;
    private ProgressBar progressBar;
    private static final String TAG = "AddMenuItemFragment";
    private ChipGroup chipGroup;
    private List<String> tags = new ArrayList<String>();
    private AddMenuItemViewModel addMenuItemViewModel;
    private static final int PICK_IMAGE_REQUEST = 1;
    private StorageReference mStorageRef;
    private boolean imageSelected;
    private Uri imguri;
    private StorageTask uploadTask;

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
        progressBar = root.findViewById((R.id.add_item_progress_bar));
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");
        imageSelected = false;

        addMenuItemViewModel = new ViewModelProvider(this).get(AddMenuItemViewModel.class);
        addMenuItemViewModel.init();


        // TODO: add selected chips to list of strings
        btnAddNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(v);
            }
        });

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Add Photo Button Pressed");
                choosePhoto();
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

    private void choosePhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        imageSelected = true;
        Log.d(TAG, "Image Selected from Gallery");
    }

    //ToDO:
    private String getExtension(Uri uri) {
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void imageUploader() {
        Log.d(TAG, "Image Upload IN PROGRESS");
        progressBar.setVisibility(getView().VISIBLE);
        btnAddNewItem.setEnabled(false);
        StorageReference ref = mStorageRef.child(System.currentTimeMillis()+"."+getExtension(imguri));
        ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
//                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Log.d(TAG, "Image upload SUCCESS");
                        progressBar.setVisibility(getView().GONE);
                        btnAddNewItem.setEnabled(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Log.d(TAG, "Image upload FAILED");
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imguri = data.getData();
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
        String chip_msg = "Selected chips are: ";
        boolean checkedChips = false;
        int chipCount = chipGroup.getChildCount();
        if(chipCount == 0) {
            chip_msg += "None.";
            return false;
        } else {
            int i = 0;
            while (i < chipCount) {
                Chip chip = (Chip) chipGroup.getChildAt(i);
                if (chip.isChecked()) {
                    if(!tags.contains(chip.getText().toString())) {
                        tags.add(chip.getText().toString());
                        Log.d(TAG, "Added " + chip.getText().toString());
                        chip_msg += chip.getText().toString() + ", ";
                    }
                    checkedChips = true;
                } else {
                    if(tags.contains(chip.getText().toString())) {
                        Log.d(TAG, "Removed " + chip.getText().toString());
                        tags.remove(chip.getText().toString());
                    }
                }
                i++;
            }
        }
        if(checkedChips) {
            Log.d(TAG, chip_msg);
            return true;
        } else {
            Log.d(TAG, "No filters selected");
            Toast.makeText(getContext(), "No filters selected", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean validateImage() {
        if(imageSelected == false) {
            Log.d(TAG, "Add Item Button Pressed - No Image Selected");
            Toast.makeText(getContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
            return false;
        } else if(uploadTask != null && uploadTask.isInProgress()) {
            Log.d(TAG, "Add Item Pressed while UPLOAD IN PROGRESS");
            Toast.makeText(getContext(), "Upload in Progress", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    //TODO: add new menu item here
    public void addItem(View v) {
        if(!validateName() | !validateCost() | !validateDesc() | !validateFilters() | !validateImage()) {
            return;
        }
        imageUploader();
        String itemName = textInputItemName.getEditText().getText().toString();
        Double itemCost = Double.parseDouble(textInputItemCost.getEditText().getText().toString());
        String itemDescription = textInputItemDesc.getEditText().getText().toString();
        MenuItem newItem = new MenuItem(itemName, itemDescription, itemCost, 2, tags);
        addMenuItemViewModel.addMenuItem(newItem, getContext());
        clearFields();
    }

    private void clearFields(){
        textInputItemName.getEditText().setText("");
        textInputItemDesc.getEditText().setText("");
        textInputItemCost.getEditText().setText("");
        chipGroup.clearCheck();
        imageSelected = false;
    }
}