package com.example.readon;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readon.model.ProductModel;
import com.example.readon.model.ProductModel;
import com.example.readon.ShopItemProductAdapter;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ShopItemProductAdapter adapter;
    private ArrayList<ProductModel> productModelArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shop);

        addData();

        recyclerView = (RecyclerView) findViewById(R.id.shopRecyclerView);

        adapter = new ShopItemProductAdapter(productModelArrayList, this);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

    }

    void addData(){
        productModelArrayList = new ArrayList<>();
        productModelArrayList.add(new ProductModel("Jay", "Rp 2.400.000,-", R.drawable.boy1, getString(R.string.placeholder_text)));
        productModelArrayList.add(new ProductModel("Mike", "Rp 2.000.000,-", R.drawable.boy2, getString(R.string.placeholder_text)));
        productModelArrayList.add(new ProductModel("Briegitta", "Rp 100.000.000,-", R.drawable.girl1,getString(R.string.placeholder_text)));
        productModelArrayList.add(new ProductModel("Tata", "Rp 99.000.000,-", R.drawable.girl2,getString(R.string.placeholder_text)));
    }
}
