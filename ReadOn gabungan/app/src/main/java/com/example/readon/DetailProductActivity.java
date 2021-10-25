package com.example.readon;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.readon.constant.IntentDataConstant;
import com.example.readon.model.ProductModel;

public class DetailProductActivity extends AppCompatActivity {

    private ProductModel data;
    private TextView productName;
    private TextView productPrice;
    private TextView productDescription;
    private ImageView productImage;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_product);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#90EE90"));

        getSupportActionBar().setBackgroundDrawable(colorDrawable);


        initData();
        initViews();
    }

    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }

    void initData(){
        data = (ProductModel) getIntent().getSerializableExtra(IntentDataConstant.PRODUCT_DATA);
    }

    void initViews(){
        productName = (TextView) findViewById(R.id.productNameTextView);
        productPrice = (TextView) findViewById(R.id.productPriceTextView);
        productDescription = (TextView) findViewById(R.id.productDescriptionTextView);
        productImage = (ImageView) findViewById(R.id.productImageView);

        productName.setText(data.getProductName());
        productPrice.setText(data.getProductPrice());
        productDescription.setText(data.getProductDescription());
        productImage.setImageResource(data.getProductImage());
    }
}
