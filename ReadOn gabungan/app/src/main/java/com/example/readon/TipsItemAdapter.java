package com.example.readon;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.readon.constant.IntentDataConstant;
import com.example.readon.model.TipsModel;

import java.io.Serializable;
import java.util.ArrayList;

public class TipsItemAdapter extends RecyclerView.Adapter<TipsItemAdapter.TipsViewHolder> {
    ArrayList<TipsModel> dataList;
    Context context;

    public TipsItemAdapter(Context context, ArrayList<TipsModel> dataList){
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public TipsViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tips_activity_row, parent, false);
        return new TipsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TipsViewHolder holder, final int position) {
        holder.title.setText(dataList.get(position).getTitle());
        holder.subtitle.setText(dataList.get(position).getSubtitle());
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final Intent intent;
                intent = new Intent(context, DetailTipsActivity.class);
                intent.putExtra(IntentDataConstant.TIPS_DATA, (Serializable) dataList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public  class TipsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView subtitle;
        public TipsViewHolder(View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.itemTipsTitleTextView);
            subtitle = (TextView) itemView.findViewById(R.id.itemTipsSubtitleTextView);
        }
    }
}
