package com.example.servermatch.cecs445.ui.addmenuitem;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AddMenuItemFragment extends Fragment {

    private TextInputLayout textInputItemName;
    private TextInputLayout textInputItemCost;
    private TextInputLayout textInputItemDesc;
    private MaterialButton btnAddNewItem;
    private MaterialButton btnAddPhoto;
    private ProgressDialog progressDialog;
    private static final String TAG = "AddMenuItemFragment";
    private ChipGroup chipGroup;
    private List<String> tags = new ArrayList<String>();
    private AddMenuItemViewModel addMenuItemViewModel;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 100;
    private StorageReference mStorageRef;
    private boolean imageSelected;
    private Uri imguri;
    private StorageTask uploadTask;
    private String downloadURL;
    private boolean usedCamera;
    private byte[] dataBAOS;
    private static final int STORAGE_PERMISSION_CODE = 10;
    private static final int CAMERA_PERMISSION_CODE = 20;

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
        progressDialog = new ProgressDialog(getContext());
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");
        imageSelected = false;
        addMenuItemViewModel = new ViewModelProvider(this).get(AddMenuItemViewModel.class);
        addMenuItemViewModel.init();
        usedCamera = false;

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
                openDialog();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        } else if(requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        builder.setTitle("Image Upload");
        builder.setMessage("How would you like to upload your image?");
        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "openDialog: Open Camera Pressed");
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Log.d(TAG, "Camera Permission Requested");
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                }
            }
        });
        builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "openDialog: Open Gallery Pressed");
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    choosePhoto();
                } else {
                    Log.d(TAG, "Storage Permission Requested");
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);;
                }
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "openDialog: Pressed Cancel");
            }
        });
        builder.create().show();
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
        imageSelected = true;
        usedCamera = true;
        Log.d(TAG, "openCamera: Camera Photo Taken");
    }

    private void choosePhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        imageSelected = true;
        Log.d(TAG, "Image Selected from Gallery");
    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void imageUploader(View v) {
        Log.d(TAG, "Image Upload IN PROGRESS");
        progressDialog.setMessage("Uploading Image ...");
        progressDialog.show();
        btnAddNewItem.setEnabled(false);
        if(usedCamera) {
            StorageReference imagesRef = mStorageRef.child(new Date().getTime() + ".jpg");
            UploadTask uploadTask = imagesRef.putBytes(dataBAOS);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "imageUploader: onFailure: Direct camera upload failed");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadURL = uri.toString();
                            Log.d(TAG, "imageUploader: onSuccess: Direct camera upload success " + downloadURL);
                        }
                    });
                }
            });
        } else {
            String childName = System.currentTimeMillis() + "." + getExtension(imguri);
            final StorageReference ref = mStorageRef.child(childName);

            // uploads file to Firebase Cloud Storage
            ref.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadURL = uri.toString();
                            Log.d(TAG, "onSuccess: UPLOAD SUCCESS : "+ downloadURL);
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imguri = data.getData();
        }
        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
            dataBAOS = baos.toByteArray();
        }
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

    public void addItem(View v) {
        if(!validateName() | !validateCost() | !validateDesc() | !validateFilters() | !validateImage()) {
            return;
        }

        Log.d(TAG, "addItem: started ");
        String itemName = textInputItemName.getEditText().getText().toString();
        Double itemCost = Double.parseDouble(textInputItemCost.getEditText().getText().toString());
        String itemDescription = textInputItemDesc.getEditText().getText().toString();
        imageUploader(v);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                MenuItem newItem = new MenuItem(itemName, itemDescription, itemCost, downloadURL, tags);
                addMenuItemViewModel.addMenuItem(newItem, getContext());
                clearFields();
                progressDialog.dismiss();
                btnAddNewItem.setEnabled(true);
            }

            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    Thread.sleep(4500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

    }

    private void clearFields(){
        textInputItemName.getEditText().setText("");
        textInputItemDesc.getEditText().setText("");
        textInputItemCost.getEditText().setText("");
        chipGroup.clearCheck();
        imageSelected = false;
        usedCamera = false;
        dataBAOS = null;
    }
}