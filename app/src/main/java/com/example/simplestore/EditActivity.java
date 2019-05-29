package com.example.simplestore;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.regex.Pattern;

import static com.example.simplestore.infoProductActivity.checkEmail;

public class EditActivity extends AppCompatActivity {
    private static final String TAG ="editText" ;
    String productKeyId;
    ImageView imageViewxx;
    EditText editTextphoneUserxx, editTextMailUserxx, editTextNAmeProductxx, editTextQuantityProductxx, editTextPriceProductxx;
    int GALLERY_REQUEST = 120;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        imageViewxx = findViewById(R.id.id_productImgxx);
        imageViewxx.setImageResource(R.drawable.ic_photo);

        editTextphoneUserxx = findViewById(R.id.et_phone_userxx);
        editTextMailUserxx = findViewById(R.id.et_email_userxx);
        editTextNAmeProductxx = findViewById(R.id.et_nameProductxx);
        editTextQuantityProductxx = findViewById(R.id.et_quantityxx);
        editTextPriceProductxx = findViewById(R.id.et_pricexx);


        String productKeymail = getIntent().getStringExtra("productKeymail");
        String productKeyname = getIntent().getStringExtra("productKeyname");
        String productKeyphoone = getIntent().getStringExtra("productKeyphoone");
        String productKeyquantity = getIntent().getStringExtra("productKeyquantity");
        String productKeyuri = getIntent().getStringExtra("productKeyuri");
        String productKeyprice = getIntent().getStringExtra("productKeyprice");


        productKeyId = getIntent().getStringExtra("productKeyId");
        editTextMailUserxx.setText(productKeymail);
        editTextQuantityProductxx.setText(productKeyquantity);
        editTextphoneUserxx.setText(productKeyphoone);
        editTextPriceProductxx.setText(productKeyprice);
        editTextNAmeProductxx.setText(productKeyname);
        if (productKeyuri != null) {
            Picasso.with(this)
                    .load(Uri.parse(productKeyuri))
                    .into(imageViewxx);

        }

        imageViewxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });
    }
Uri selectedImageuri;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 120:
                    selectedImageuri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplication().getBaseContext().getContentResolver(), selectedImageuri);
                        imageViewxx.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
    }


    String uPhone;
    String uMail;
    String pQuantity;
    String pPrice;
    String pName;
    Uri selectedImage;
    ModelProduct product;
    DatabaseReference mDatabase;
    public void updateValues(View view) {

        mDatabase = FirebaseDatabase.getInstance().getReference();
        if (TextUtils.isEmpty(editTextMailUserxx.getText()) || TextUtils.isEmpty(editTextphoneUserxx.getText()) || TextUtils.isEmpty(editTextNAmeProductxx.getText()) || TextUtils.isEmpty(editTextPriceProductxx.getText()) || TextUtils.isEmpty(editTextQuantityProductxx.getText())) {
            Toast.makeText(EditActivity.this, "please complet all information !", Toast.LENGTH_SHORT).show();
        } else {
            uMail = editTextMailUserxx.getText().toString().trim();

            if (checkEmail(uMail) == true) {

                uPhone = editTextphoneUserxx.getText().toString().trim();
                if (isValidPhoneNumber(uPhone)) {
                    uMail = editTextMailUserxx.getText().toString().trim();
                    pName = editTextNAmeProductxx.getText().toString().trim();
                    pQuantity = editTextQuantityProductxx.getText().toString().trim();
                    pPrice = editTextPriceProductxx.getText().toString().trim();

                    uploadToFirebase(selectedImage, productKeyId);
                    product = new ModelProduct(pName, uMail, uPhone, pQuantity, pPrice, productKeyId, selectedImage + "");
                    mDatabase.child("products").child(productKeyId).setValue(product);
                    finish();
                } else {
                    Toast.makeText(this, "phone is Valid .. ", Toast.LENGTH_SHORT).show();

                }
            } else {
                Toast.makeText(this, "E-Mail is Valid .. ", Toast.LENGTH_SHORT).show();
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
}
