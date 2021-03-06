package com.example.simplestore.UtilsProduct;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simplestore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdabterRecycleProduct extends RecyclerView.Adapter<AdabterRecycleProduct.holder> {
    Context mcontext;
    ArrayList<ModelProduct> modelProductArrayList;

    public AdabterRecycleProduct(Context mcontext, ArrayList<ModelProduct> modelProductArrayList) {

        this.mcontext = mcontext;
        this.modelProductArrayList = modelProductArrayList;
    }


    @Override
    public holder onCreateViewHolder( ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(mcontext).inflate(R.layout.item_recyclerview,viewGroup,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder( holder holder, int i) {

        ModelProduct product = modelProductArrayList.get(i);
        Picasso.with(mcontext)
                .load(Uri.parse(product.getUriImg()))
                .into(holder.imageView);


        holder.textView.setText(product.getPriceProduct()+"  $");



    }

    @Override
    public int getItemCount() {
        return modelProductArrayList.size();
    }


    public class holder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        public holder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.tv_recycleItem);
            imageView=itemView.findViewById(R.id.img_recycleItem);

        }
    }
}
