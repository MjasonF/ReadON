package com.example.readon;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readon.model.Reads;

import java.util.ArrayList;
import java.util.List;

public class TextAdapter extends RecyclerView.Adapter<com.example.readon.TextAdapter.ViewHolder> {

    private LayoutInflater inflater;
    List<Reads> reads = new ArrayList<Reads>();

    TextAdapter(Context context, ArrayList<String> titles, ArrayList<String> contents){
        this.inflater = LayoutInflater.from(context);
    }

    public void addTitle(List<Reads> reads) {
        this.reads = reads;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup ViewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_view, ViewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String title = reads.get(i).getTitle();
        Reads read = reads.get(i);
        String content ="";

        content += "Title : " + read.getTitle() + "\n";
        content += "Author : " + read.getAuthor() + "\n";
        content += "Summary : " + read.getSummary() + "\n";
        content += "Year : " + read.getYear() + "\n";
        content += "Pages : " + read.getPages() + "\n";
        content += "Words : " + read.getWords() + "\n";
        content += "Difficulties : " + read.getDifficulties() + "\n\n";


        viewHolder.textTitle.setText(title);
        viewHolder.textContent.setText(content);
    }

    @Override
    public int getItemCount() {
        return reads.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle, textContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), TextDetails.class);
                    i.putExtra("titleOfText", reads.get(getAdapterPosition()).getTitle());
                    i.putExtra("contentOfText", reads.get(getAdapterPosition()).getFull_story());
                    i.putExtra("id", reads.get(getAdapterPosition()).getId());

                    v.getContext().startActivity(i);
                }
            });
            textTitle = itemView.findViewById(R.id.textTitle);
            textContent = itemView.findViewById(R.id.textContent);
        }
    }
}
