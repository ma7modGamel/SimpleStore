package com.example.simplestore;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.simplestore.UtilsProduct.ModelProduct;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class infoProductActivity extends AppCompatActivity {
    private static final String TAG = "infoProductActivity";


    int GALLERY_REQUEST = 110;
    int pImg = 1;
    String pName, pQuantity, pPrice;
    EditText editTextNAmeProduct, editTextPriceProduct, editTextQuantityProduct;
    ImageView productImg;
    Uri selectedImage;

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


    public void addtoDataBase(View view) {
        if (TextUtils.isEmpty(editTextNAmeProduct.getText()) || TextUtils.isEmpty(editTextPriceProduct.getText()) || TextUtils.isEmpty(editTextQuantityProduct.getText())) {
            Toast.makeText(this, "please complet all information !", Toast.LENGTH_SHORT).show();
        } else {
            pName = editTextNAmeProduct.getText().toString().trim();
            pQuantity = editTextQuantityProduct.getText().toString().trim();
            pPrice = editTextPriceProduct.getText().toString().trim();


            ModelProduct product = new ModelProduct(pName, pImg, pQuantity, pPrice);
            String key = mDatabase.child("products").push().getKey();

            Log.e(TAG, product.getNameproduct());
            mDatabase.child("products").child(key).setValue(product);

            Toast.makeText(this, "" + pQuantity + " " + pPrice, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "addtoDataBase: " + pPrice);
            uploadImage(key);


        }
    }

    private void uploadImage(String key) {


        StorageReference mountainsRef = storageReference.child(key);
        StorageReference mountainImagesRef = storageReference.child("images/"+key);


        mountainsRef.getName().equals(mountainImagesRef.getName());
        mountainsRef.getPath().equals(mountainImagesRef.getPath());
        if (selectedImage != null) {

            productImg.setDrawingCacheEnabled(true);
            productImg.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) productImg.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = mountainsRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads

                    Log.e("exiption>>>>>>>>>>", exception + "");
                    Toast.makeText(infoProductActivity.this, " FAILD", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...

                    Toast.makeText(infoProductActivity.this, " SUCCESS ", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, "no image selected", Toast.LENGTH_SHORT).show();
        }
    }
}