package com.example.servermatch.cecs445.ui.addmenuitem;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
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
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class AddMenuItemFragment extends Fragment {

    private TextInputLayout textInputItemName;
    private TextInputLayout textInputItemCost;
    private TextInputLayout textInputItemDesc;
    private MaterialButton btnAddNewItem;
    private MaterialButton btnAddPhoto;
    private MaterialButton btnConfirmImageView;
    private MaterialButton btnCancelImageView;
    private ProgressDialog progressDialog;
    private FrameLayout imageViewFrameLayout;
    private ImageView imageView;
    private static final String TAG = "AddMenuItemFragment";
    private ChipGroup chipGroup; // filters that will be applied to MenuItem
    private List<String> tags = new ArrayList<String>();
    private AddMenuItemViewModel addMenuItemViewModel;
    private static final int PICK_IMAGE_REQUEST = 1; // arbitrary request code for opening gallery
    private static final int CAMERA_REQUEST = 100; // arbitrary request code for opening camera
    private StorageReference mStorageRef; // reference to FirebaseStorage
    // boolean to determine if image was selected from gallery or camera
    private boolean imageSelected;
    private Uri imguri; // reference to gallery image
    private StorageTask uploadTask; // controllable Task that has a synchronized state machine
    private String downloadURL; // url provided to MenuItem for displaying on menu
    private boolean usedCamera; // boolean to determine if image was created from camera
    private byte[] dataBAOS; // byte array if image was created from camera
    // arbitrary request code for requesting permission to read external storage
    private static final int STORAGE_PERMISSION_CODE = 10;
    // arbitrary request code for requesting permission to access camera
    private static final int CAMERA_PERMISSION_CODE = 20;
    // arbitrary request code for requesting permission to write to storage
    private static final int WRITE_STORAGE_PERMISSION_CODE = 30;
    private static final int CAMERA_AND_WRITE_PERMISSION_CODE = 40;
    private String pictureFilePath;

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

    /**
     * Creates view for Add Menu Item.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_menu_item, container, false);
        textInputItemName = root.findViewById(R.id.text_input_item_name);
        textInputItemCost = root.findViewById(R.id.text_input_item_cost);
        textInputItemDesc = root.findViewById(R.id.text_input_item_desc);
        btnAddNewItem = root.findViewById((R.id.add_item_button));
        btnAddPhoto = root.findViewById((R.id.add_photo_button));
        btnConfirmImageView = root.findViewById((R.id.confirm_image_view));
        btnCancelImageView = root.findViewById((R.id.cancel_image_view));
        imageViewFrameLayout = root.findViewById(R.id.image_holder);
        imageView = root.findViewById(R.id.add_menu_item_image);
        progressDialog = new ProgressDialog(getContext());
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");
        imageSelected = false;
        addMenuItemViewModel = new ViewModelProvider(this).get(AddMenuItemViewModel.class);
        addMenuItemViewModel.init();
        usedCamera = false;

        btnAddNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateName() | !validateCost() | !validateDesc() | !validateFilters() | !validateImage()) {
                    return;
                }
                imageViewFrameLayout.setVisibility(v.VISIBLE);
            }
        });

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Add Photo Button Pressed");
                openDialog();
            }
        });

        btnConfirmImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(v);
            }
        });

        btnCancelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewFrameLayout.setVisibility(v.GONE);
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

    /**
     * Opens AlertDialog with three choices: cancel, gallery, and camera. If user presses gallery,
     * call choosePhoto(). If user presses camera, call openCamera(). Before either of these
     * functions are called, we check if the user allows for reading external storage or taking
     * pictures/videos.
     */
    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        builder.setTitle(R.string.alert_dialog_title_menu_item);
        builder.setMessage(R.string.alert_dialog_message_menu_item);
        builder.setPositiveButton(R.string.alert_dialog_message_pos_button_menu_item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "openDialog: Open Camera Pressed");
                if((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                        &&
                        (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                    openCamera();
                } else {
                    if((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
                            &&
                            (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)) {
                        Log.d(TAG, "Camera and Storage Permission Requested");
                        requestPermissions(new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                CAMERA_AND_WRITE_PERMISSION_CODE);
                    } else if((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
                            &&
                            (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                        Log.d(TAG, "Camera Permission Requested");
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                    } else if((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                            &&
                            (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)) {
                        Log.d(TAG, "Write to Storage Permission Requested");
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE_PERMISSION_CODE);
                    }
                }
            }
        });
        builder.setNegativeButton(R.string.alert_dialog_message_neg_button_menu_item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "openDialog: Open Gallery Pressed");
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    choosePhoto();
                } else {
                    Log.d(TAG, "Storage Permission Requested");
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                }
            }
        });
        builder.setNeutralButton(R.string.alert_dialog_message_neutral_button_menu_item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "openDialog: Pressed Cancel");
            }
        });
        builder.create().show();
    }

    /**
     * Create new Intent for activity of getting photo from Camera.
     */
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(getContext().getPackageManager()) != null) {
            File imageFile = null;
            try {
                imageFile = createImageFile();
            } catch (IOException ex) {
                Log.d(TAG, "openCamera: " + ex.toString());
            }
            if (imageFile != null) {
                Uri photoUri = FileProvider.getUriForFile(getContext(),
                        "com.example.servermatch.cecs445.fileprovider",
                        imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                imageSelected = true;
                usedCamera = true;
                Log.d(TAG, "openCamera: Camera Photo Taken");
            }
        }
    }

    /**
     * Creates a jpeg file that will be stored in a private directory if user chooses to upload
     * their image by taking a photo with their camera.
     * @return File that will be used for storing a direct camera upload
     * @throws IOException if we can't store to directory
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String pictureFile = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(pictureFile, ".jpg", storageDir);
        pictureFilePath = image.getAbsolutePath();
        return image;
    }

    /**
     * Create new Intent for activity of getting photo from Gallery.
     */
    private void choosePhoto() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), PICK_IMAGE_REQUEST);
        imageSelected = true;
        Log.d(TAG, "Image Selected from Gallery");
    }

    /**
     * Overrides the onActivityResult. Determine which code was requested based on Intent.
     * @param requestCode int defined at the top of the class, helps differentiate Intents.
     * @param resultCode int result code from Activity.
     * @param data Intent that is an abstract description of an operation to be performed.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imguri = data.getData();
            Picasso.get().load(data.getData()).into(imageView);
        }
        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            File imgFile = new File(pictureFilePath);
            if(imgFile.exists()) {
                imageView.setImageURI(Uri.fromFile(imgFile));
                Log.d(TAG, "onActivityResult: " + Uri.fromFile(imgFile).toString());
            }
            // for some reason the uploads come out rotated, fix orientation before upload
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap tempBitmap = null;
            try {
                tempBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.fromFile(imgFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = Bitmap.createBitmap(tempBitmap, 0, 0, tempBitmap.getWidth(), tempBitmap.getHeight(), matrix, true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
            dataBAOS = baos.toByteArray();
        }
    }

    /**
     * Method only used if user chooses to upload from Gallery.
     * @param uri imguri will be used to determine extension type
     * @return string for file name in Firebase
     */
    private String getExtension(Uri uri) {
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    /**
     * Uploads image to Firebase. Determines if the user used the gallery or camera, different
     * procedure will be used for uploading. ProgressDialog will pop up that obscures screen.
     * Add Item is disabled to prevent multiple uploads. Set the downloadURL to the Firebase URL.
     * @param v current view
     */
    private void imageUploader(View v) {
        Log.d(TAG, "Image Upload IN PROGRESS");
        progressDialog.setMessage("Uploading Image ...");
        progressDialog.show();
        btnConfirmImageView.setEnabled(false);
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
            ref.putFile(imguri).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "imageUploader: onFailure: Gallery upload failed");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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

    /**
     * Determine if the user has entered a name for their new MenuItem
     * @return boolean true or false
     */
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

    /**
     * Determine if the user has entered a cost for their new MenuItem
     * @return boolean true or false
     */
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

    /**
     * Determine if the user has entered a description for their new MenuItem.
     * @return boolean true or false
     */
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

    /**
     * Determine if the user has selected at least one filter for their new MenuItem.
     * @return boolean true or false
     */
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

    /**
     * Determine if the user has selected an image, either from the gallery or with a camera.
     * @return boolean true or false
     */
    private boolean validateImage() {
        if(!imageSelected) {
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

    /**
     * The user is ready to upload their MenuItem to Firebase. We validate ALL fields: name, cost,
     * description, and if an image was selected. If all fields were completed, we upload the
     * image first to Firebase Storage, then the MenuItem to Firebase Database. If at least one
     * field was not filled in, we do not allow the user to proceed, instead we display a Toast
     * message to let them know that there's something missing.
     * @param v the current view
     */
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
                btnConfirmImageView.setEnabled(true);
                imageViewFrameLayout.setVisibility(getView().GONE);
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

    /***
     * After uploading a new MenuItem to Firebase, we set text fields, chip group,
     * if an image was selected, if we used the camera, and a byte array to reset.
     */
    private void clearFields(){
        textInputItemName.getEditText().setText("");
        textInputItemDesc.getEditText().setText("");
        textInputItemCost.getEditText().setText("");
        chipGroup.clearCheck();
        imageSelected = false;
        usedCamera = false;
        dataBAOS = null;
        imageView = null;
    }
}