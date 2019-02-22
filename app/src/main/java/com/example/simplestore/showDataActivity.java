package com.example.simplestore;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class showDataActivity extends AppCompatActivity {
    String productKeyId;
    ImageView imageViewyy;
    TextView editTextphoneUseryy, editTextMailUseryy, editTextNAmeProductyy, editTextQuantityProductyy, editTextPriceProductyy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        editTextphoneUseryy = findViewById(R.id.et_phone_useryy);
        editTextMailUseryy = findViewById(R.id.et_email_useryy);
        editTextNAmeProductyy = findViewById(R.id.et_nameProductyy);
        editTextQuantityProductyy = findViewById(R.id.et_quantityyy);
        editTextPriceProductyy = findViewById(R.id.et_priceyy);

        imageViewyy = findViewById(R.id.id_productImgyy);
        imageViewyy.setImageResource(R.drawable.ic_photo);

        String productKeymail = getIntent().getStringExtra("productKeymail");
        String productKeyname = getIntent().getStringExtra("productKeyname");
        String productKeyphoone = getIntent().getStringExtra("productKeyphoone");
        String productKeyquantity = getIntent().getStringExtra("productKeyquantity");
        String productKeyuri = getIntent().getStringExtra("productKeyuri");
        String productKeyprice = getIntent().getStringExtra("productKeyprice");

        productKeyId = getIntent().getStringExtra("productKeyId");

        editTextMailUseryy.setText(productKeymail);
        editTextQuantityProductyy.setText(productKeyquantity);
        editTextphoneUseryy.setText(productKeyphoone);
        editTextPriceProductyy.setText(productKeyprice);
        editTextNAmeProductyy.setText(productKeyname);
        if (productKeyuri != null) {
            Picasso.with(this)
                    .load(Uri.parse(productKeyuri))
                    .into(imageViewyy);

        }


    }

    public void callFun(View view) {

        Intent myIntent = new Intent(Intent.ACTION_CALL);
        String phNum = "tel:" + getIntent().getStringExtra("productKeyphoone");
        myIntent.setData(Uri.parse(phNum));
        startActivity(myIntent);
    }

    public void sendMailFun(View view) {

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(showDataActivity.this);
        View mView = layoutInflaterAndroid.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(showDataActivity.this);
        alertDialogBuilderUserInput.setView(mView);
        final String productKeymail = getIntent().getStringExtra("productKeymail");
        final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        Toast.makeText(showDataActivity.this, "sending to "+productKeymail+" ..", Toast.LENGTH_SHORT).show();
                        Toast.makeText(showDataActivity.this, "done ", Toast.LENGTH_SHORT).show();
                    }
                })

                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }

}

