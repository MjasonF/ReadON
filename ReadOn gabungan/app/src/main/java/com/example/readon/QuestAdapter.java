package com.example.readon;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readon.model.QuestModel;
import com.example.readon.model.Reads;

import java.util.ArrayList;
import java.util.List;


public class QuestAdapter extends RecyclerView.Adapter<QuestAdapter.ViewHolder> {

    private LayoutInflater inflater;
    List<QuestModel> quests = new ArrayList<QuestModel>();

    QuestAdapter(Context context){
        this.inflater = LayoutInflater.from(context);
    }

    public void addItem(List<QuestModel> quests) {
        this.quests = quests;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup ViewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_view_quest, ViewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String title = quests.get(i).name;
        String content = quests.get(i).status;


        viewHolder.textTitle.setText(title);
        viewHolder.textContent.setText(content);
    }

    @Override
    public int getItemCount() {
        return quests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle, textContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent i = new Intent(v.getContext(), TextDetails.class);
//                    i.putExtra("titleOfText", reads.get(getAdapterPosition()).getTitle());
//                    i.putExtra("contentOfText", reads.get(getAdapterPosition()).getFull_story());
//                    i.putExtra("id", reads.get(getAdapterPosition()).getId());
//
//                    v.getContext().startActivity(i);
                }
            });
            textTitle = itemView.findViewById(R.id.textTitle);
            textContent = itemView.findViewById(R.id.textContent);
        }
    }
}