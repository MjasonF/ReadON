package com.example.readon;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.readon.constant.IntentDataConstant;
import com.example.readon.model.ProductModel;

import java.io.Serializable;
import java.util.ArrayList;

public class ShopItemProductAdapter extends RecyclerView.Adapter<ShopItemProductAdapter.ShopViewHolder> {
    ArrayList<ProductModel> dataList;
    Context context;

    public ShopItemProductAdapter(ArrayList<ProductModel> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public ShopViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_shop_activity_row, parent, false);
        return new ShopViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ShopViewHolder holder, int position) {
        holder.name.setText(dataList.get(position).getProductName());
        holder.price.setText(dataList.get(position).getProductPrice());
        holder.image.setImageResource(dataList.get(position).getProductImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent;
                intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra(IntentDataConstant.PRODUCT_DATA, (Serializable) dataList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { return (dataList != null) ? dataList.size() : 0; }


    public class ShopViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView price;
        ImageView image;

        public ShopViewHolder(View itemView){
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.productNameTextView);
            price = (TextView) itemView.findViewById(R.id.productPriceTextView);
            image = (ImageView) itemView.findViewById(R.id.productImageView);
        }


    }
}
