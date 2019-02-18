package com.example.simplestore.UtilsProduct;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.simplestore.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class AdapterListView extends BaseAdapter{
    Context context;
    ArrayList<ModelProduct> modelProducts;

    public AdapterListView(Context context, ArrayList<ModelProduct> modelProducts) {
        this.context = context;
        this.modelProducts = modelProducts;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        final ModelProduct product = getItem(position);
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview, parent, false);
        }
        TextView textViewNAme=convertView.findViewById(R.id.idNAmeList);
        TextView textViewQuantity=convertView.findViewById(R.id.idquantityList);

        final ImageView imgViewNAme=convertView.findViewById(R.id.idImgList);

        imgViewNAme.setImageURI(Uri.parse(product.getUriImg()));
        textViewNAme.setText(product.getNameproduct());
        textViewQuantity.setText(product.getQuantityProduct());



        return convertView;
    }
}
