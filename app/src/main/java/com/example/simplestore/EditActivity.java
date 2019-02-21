package com.example.simplestore;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.simplestore.UtilsProduct.ModelProduct;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditActivity extends AppCompatActivity {
    String productKeyId;
    ImageView imageViewxx;
    EditText editTextphoneUserxx,editTextMailUserxx,editTextNAmeProductxx,editTextQuantityProductxx,editTextPriceProductxx;
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
            imageViewxx.setImageURI(Uri.parse(productKeyuri));
        }
    }

    String uPhone,uMail,pQuantity,pPrice,pName,selectedImage;
    public void updateValues(View view) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        if (TextUtils.isEmpty(editTextMailUserxx.getText()) || TextUtils.isEmpty(editTextphoneUserxx.getText()) ||TextUtils.isEmpty(editTextNAmeProductxx.getText()) || TextUtils.isEmpty(editTextPriceProductxx.getText()) || TextUtils.isEmpty(editTextQuantityProductxx.getText())) {
            Toast.makeText(EditActivity.this, "please complet all information !", Toast.LENGTH_SHORT).show();
        } else {
            uPhone = editTextphoneUserxx.getText().toString().trim();
            uMail = editTextMailUserxx.getText().toString().trim();
            pName = editTextNAmeProductxx.getText().toString().trim();
            pQuantity = editTextQuantityProductxx.getText().toString().trim();
            pPrice = editTextPriceProductxx.getText().toString().trim();
            ModelProduct product = new ModelProduct(pName,   uMail,   uPhone,   pQuantity, pPrice, productKeyId,selectedImage+"");
            mDatabase.child("products").child(productKeyId).setValue(product);
            finish();
        }

    }
}
