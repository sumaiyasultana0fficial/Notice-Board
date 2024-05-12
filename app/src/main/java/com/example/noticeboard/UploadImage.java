package com.example.noticeboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadImage extends AppCompatActivity {
    private Spinner imageCategory;
    private CardView selectImage;
    private Button uploadImage;
    private ImageView galleryImageView;
    private String category;
    private final int RAQ = 1;
    private Bitmap bitMap;
    private ProgressDialog pd;

    private DatabaseReference reference;
    private StorageReference storageReference;
    private String downloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upload_image);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        selectImage = findViewById(R.id.addGalleryImage);
        imageCategory = findViewById(R.id.image_category);
        uploadImage = findViewById(R.id.uploadImageBtn);
        galleryImageView = findViewById(R.id.galleryImageview);
        reference = FirebaseDatabase.getInstance().getReference().child("gallery");
        storageReference = FirebaseStorage.getInstance().getReference().child("gallery");

        pd = new ProgressDialog(this);
        String[] items = new String[]{"Select Category", "Convocation", "Other Events"};
        imageCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items));
        imageCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = imageCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        selectImage.setOnClickListener(v -> openGallery());
        uploadImage.setOnClickListener(v -> {
            if (bitMap == null) {
                Toast.makeText(UploadImage.this, "Please Upload Image", Toast.LENGTH_SHORT).show();
            } else if (category.equals("Select Category")) {
                Toast.makeText(UploadImage.this, "Please Select Image Category", Toast.LENGTH_SHORT).show();
            } else {
                pd.setMessage("Uploading.......");
                pd.show();
                uploadImage();
            }
        });
    }

    private void uploadImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalImg = baos.toByteArray();
        final StorageReference filePath = storageReference.child(finalImg + "jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImg);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        downloadUrl = String.valueOf(uri);
                        uploadData();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadImage.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadData() {
        // Ensure the image is uploaded to the correct child node
        final String uniqueKey = reference.child(category).push().getKey();
        reference.child(category).child(uniqueKey).setValue(downloadUrl)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        pd.dismiss();
                        Toast.makeText(UploadImage.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(UploadImage.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage, RAQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RAQ && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                bitMap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                setImageWithProperDimensions(bitMap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setImageWithProperDimensions(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float aspectRatio = (float) height / (float) width;

        int desiredWidth = getResources().getDisplayMetrics().widthPixels - (2 * 16);

        int desiredHeight = Math.round(desiredWidth * aspectRatio);

        galleryImageView.getLayoutParams().width = desiredWidth;
        galleryImageView.getLayoutParams().height = desiredHeight;
        galleryImageView.requestLayout();

        galleryImageView.setImageBitmap(bitmap);
    }
}
