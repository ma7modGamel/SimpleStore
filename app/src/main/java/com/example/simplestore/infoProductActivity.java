package com.example.simplestore;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.simplestore.UtilsProduct.ModelProduct;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class infoProductActivity extends AppCompatActivity {
    private static final String TAG = "infoProductActivity";


    int GALLERY_REQUEST = 110;
    String pName, pQuantity, pPrice;
    EditText editTextNAmeProduct, editTextPriceProduct, editTextQuantityProduct;
    ImageView productImg;
    Uri selectedImage;
    Button buttonAddToDB;
    DatabaseReference mDatabase;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_product);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        editTextNAmeProduct = findViewById(R.id.et_nameProduct);
        editTextQuantityProduct = findViewById(R.id.et_quantity);
        editTextPriceProduct = findViewById(R.id.et_price);

        productImg = findViewById(R.id.id_productImg);
        productImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });

        buttonAddToDB = findViewById(R.id.btnAddToDB);
        buttonAddToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(editTextNAmeProduct.getText()) || TextUtils.isEmpty(editTextPriceProduct.getText()) || TextUtils.isEmpty(editTextQuantityProduct.getText())) {
                    Toast.makeText(infoProductActivity.this, "please complet all information !", Toast.LENGTH_SHORT).show();
                } else {
                    pName = editTextNAmeProduct.getText().toString().trim();
                    pQuantity = editTextQuantityProduct.getText().toString().trim();
                    pPrice = editTextPriceProduct.getText().toString().trim();
                    String key = mDatabase.child("products").push().getKey();
                    uploadToFirebase(selectedImage, key);
                    ModelProduct product = new ModelProduct(pName, pQuantity, pPrice, key, selectedImage + "");
                    mDatabase.child("products").child(key).setValue(product);

                    finish();
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 110:
                    selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplication().getBaseContext().getContentResolver(), selectedImage);
                        productImg.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
    }


    private void uploadToFirebase(Uri uriProfileImage, String key) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        //Select destination filename, folder
        final StorageReference profileimageRef = storageRef.child(key + ".jpg");
        UploadTask uploadTask = profileimageRef.putFile(uriProfileImage);


        //Upload image
        if (uriProfileImage != null) {

            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return profileimageRef.getDownloadUrl();
                }

            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if (task.isSuccessful()) {
                        profileimageurl = task.getResult().toString();
                        saveImagePathToDatabase(profileimageurl);
                    }
                }
            });
        }

    }

    String profileimageurl;

    private void saveImagePathToDatabase(String link) {

        ModelProduct product;
        product = new ModelProduct(link);
        mDatabase.push().setValue(product);
    }
}