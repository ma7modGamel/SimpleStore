package com.example.simplestore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.simplestore.UtilsProduct.AdabterRecycleProduct;
import com.example.simplestore.UtilsProduct.AdapterListView;
import com.example.simplestore.UtilsProduct.ModelProduct;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    ListView listView;
    Button btnAdd;
    ArrayList<ModelProduct> modelProducts;
    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnAddId);
        listView = findViewById(R.id.id_listView);
        recyclerView = findViewById(R.id.id_recycle);
        manager = new  LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("products");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                modelProducts = new ArrayList<>();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    ModelProduct product = snapshot.getValue(ModelProduct.class);
                    modelProducts.add(product);
                }
                listView.setAdapter(new AdapterListView(MainActivity.this, modelProducts));
                recyclerView.setAdapter(new AdabterRecycleProduct(MainActivity.this, modelProducts));
                new AdapterListView(MainActivity.this, modelProducts).notifyDataSetChanged();
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });

    }



    public void addNewProduct(View view) {
        Intent intent = new Intent(MainActivity.this, infoProductActivity.class);
        startActivity(intent);
    }
}

