package com.example.simplestore;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.regex.Pattern;

public class infoProductActivity extends AppCompatActivity {
    private static final String TAG = "infoProductActivity";


    int GALLERY_REQUEST = 110;
    String pName, pQuantity, pPrice,uPhone,uMail;
    EditText editTextNAmeProduct, editTextPriceProduct, editTextQuantityProduct,editTextphoneUser,editTextMailUser;
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
        editTextphoneUser = findViewById(R.id.et_phone_user);
        editTextMailUser = findViewById(R.id.et_email_user);
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


    private void uploadToFirebase(Uri uriProfileImage, String key) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        if (uriProfileImage == null) {
            Toast.makeText(this, "no photo selected .. ", Toast.LENGTH_SHORT).show();
        }
        else {
            final StorageReference profileimageRef = storageRef.child(key + ".jpg");
            UploadTask uploadTask = profileimageRef.putFile(uriProfileImage);
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
                        profileimageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                saveImagePathToDatabase(uri.toString());
                            }
                        });
                    }
                }
            });
        }
    }

    private void saveImagePathToDatabase(String link) {
        Log.e(TAG, "saveImagePathToDatabase: "+link);
        product.setUriImg(link);
        mDatabase.push().setValue(product);
    }
    ModelProduct product;
    public void addtoDataBase(View view) {
        BitmapDrawable drawable = (BitmapDrawable) productImg.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Log.e(TAG, "addtoDataBase: " + bitmap.toString());
        if (TextUtils.isEmpty(editTextMailUser.getText()) || TextUtils.isEmpty(editTextphoneUser.getText()) ||TextUtils.isEmpty(editTextNAmeProduct.getText()) || TextUtils.isEmpty(editTextPriceProduct.getText()) || TextUtils.isEmpty(editTextQuantityProduct.getText()) || bitmap == null) {
            Toast.makeText(infoProductActivity.this, "please complet all information !", Toast.LENGTH_SHORT).show();
        } else {
            uMail = editTextMailUser.getText().toString().trim();
            if (checkEmail(uMail) == true) {
                uPhone = editTextphoneUser.getText().toString().trim();
                if(isValidPhoneNumber(uPhone)) {

                    pName = editTextNAmeProduct.getText().toString().trim();
                    pQuantity = editTextQuantityProduct.getText().toString().trim();
                    pPrice = editTextPriceProduct.getText().toString().trim();
                    String key = mDatabase.child("products").push().getKey();
                    uploadToFirebase(selectedImage, key);
                    product = new ModelProduct(pName, uMail, uPhone, pQuantity, pPrice, key, selectedImage + "");
                    mDatabase.child("products").child(key).setValue(product);
                    finish();
                }else{
                    Toast.makeText(this, "phone is inValid .. ", Toast.LENGTH_SHORT).show();

                }
            }else {
                Toast.makeText(this, "E-Mail is inValid .. ", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public final static boolean isValidPhoneNumber(CharSequence target) {
        if (target == null || target.length() < 6 || target.length() > 13) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }

    }

    public static boolean checkEmail(String email) {

        Pattern EMAIL_ADDRESS_PATTERN = Pattern
                .compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
}