package com.example.simplestore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


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

        ModelProduct product = getItem(position);
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview, parent, false);
        }
        TextView textViewNAme=convertView.findViewById(R.id.idNAmeList);
        TextView textViewQuantity=convertView.findViewById(R.id.idquantityList);

        ImageView imgViewNAme=convertView.findViewById(R.id.idImgList);

        textViewNAme.setText(product.getNameproduct());
        textViewQuantity.setText(product.getQuantityProduct());
        imgViewNAme.setImageResource(product.getImgProduct());

        return convertView;
    }
}
