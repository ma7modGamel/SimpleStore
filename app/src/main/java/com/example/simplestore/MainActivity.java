package com.example.simplestore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.id_listView);
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
        listView.setAdapter(new AdapterListView(MainActivity.this,modelProducts));
        recyclerView.setAdapter(new AdabterRecycleProduct(MainActivity.this, modelProducts));

    }

}

