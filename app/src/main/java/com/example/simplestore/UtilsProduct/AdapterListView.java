package com.example.simplestore.UtilsProduct;
import com.example.simplestore.EditActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simplestore.MainActivity;
import com.example.simplestore.R;
import com.example.simplestore.showDataActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class AdapterListView extends BaseAdapter{

    private static final String TAG="AdapterListView";
    Context context;
    ArrayList<ModelProduct> modelProducts;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    public AdapterListView(Context context, ArrayList<ModelProduct> modelProducts) {
        this.context = context;
        this.modelProducts = modelProducts;
        databaseReference = FirebaseDatabase.getInstance().getReference("products");
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public int getCount() {
        return modelProducts.size();
    }

    @Override
    public ModelProduct getItem(int position) {
        return modelProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ModelProduct product = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview, parent, false);
        }
        TextView textViewNAme=convertView.findViewById(R.id.idNAmeList);
        TextView textViewQuantity=convertView.findViewById(R.id.idquantityList);
        ImageButton imgDelete=convertView.findViewById(R.id.img_delete);
        ImageButton imgEdit=convertView.findViewById(R.id.img_edit);
        ImageView imgViewNAme=convertView.findViewById(R.id.idImgList);

        Picasso.with(context)
                .load(Uri.parse(product.getUriImg()))
                .into(imgViewNAme);

        textViewNAme.setText(product.getNameproduct());
        textViewQuantity.setText(product.getQuantityProduct());
        notifyDataSetChanged();
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(context);
                alertDialog2.setTitle("Confirm Delete...");
                alertDialog2.setMessage("Are you sure you want delete this item?");
                alertDialog2.setIcon(R.drawable.ic_action_delet);

                alertDialog2.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference.child(product.getId()).removeValue();
                                storageReference.child(product.getId()+".jpg").delete();
                              notifyDataSetChanged();
                            }
                        });
                alertDialog2.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog2.show();
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, showDataActivity.class);
                intent.putExtra("productKeymail",product.getEmail());
                intent.putExtra("productKeyname",product.getNameproduct());
                intent.putExtra("productKeyphoone",product.getPhoneNumber());
                intent.putExtra("productKeyquantity",product.getQuantityProduct());
                intent.putExtra("productKeyuri",product.getUriImg());
                intent.putExtra("productKeyprice",product.getPriceProduct());
                intent.putExtra("productKeyId",product.getId());
                context.startActivity(intent);
            }
        });

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("productKeymail",product.getEmail());
                intent.putExtra("productKeyname",product.getNameproduct());
                intent.putExtra("productKeyphoone",product.getPhoneNumber());
                intent.putExtra("productKeyquantity",product.getQuantityProduct());
                intent.putExtra("productKeyuri",product.getUriImg());
                intent.putExtra("productKeyprice",product.getPriceProduct());
                intent.putExtra("productKeyId",product.getId());
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
