package com.example.simplestore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.simplestore.UtilsProduct.AdabterRecycleProduct;
import com.example.simplestore.UtilsProduct.AdapterListView;
import com.example.simplestore.UtilsProduct.ModelProduct;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    ListView listView;
    Button btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnAdd = findViewById(R.id.btnAddId);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, infoProductActivity.class);
                startActivity(intent);
            }
        });
        listView = findViewById(R.id.id_listView);
        manager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = findViewById(R.id.id_recycle);
        recyclerView.setLayoutManager(manager);
        String[] namesProduct = {"Dog", "Cat", "Fish", "Bird"};
        int[] imgProduct = {R.drawable.dog, R.drawable.cat, R.drawable.fish, R.drawable.bird};
        String[] quantityProduct = {"5", "3", "12", "8"};
        String[] priceProduct = {"120", "410", "170", "200"};


        ArrayList<ModelProduct> modelProducts = new ArrayList<>();
        for (int i = 0; i < namesProduct.length; i++) {
            modelProducts.add(new ModelProduct(namesProduct[i], imgProduct[i], quantityProduct[i], priceProduct[i]));
        }
        listView.setAdapter(new AdapterListView(MainActivity.this, modelProducts));
        recyclerView.setAdapter(new AdabterRecycleProduct(MainActivity.this, modelProducts));

    }


}

