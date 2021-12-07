package com.example.readon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readon.model.Challenge;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    ArrayList<Notes> notesList;

    public NotesAdapter(ArrayList<Notes> notesList){
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_notes, parent, false);
        return new NotesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Notes notes = notesList.get(position);
        holder.notesTime.setText(String.format("%01d:%01d", notes.getHours(), notes.getMinutes()));
        holder.notesDesc.setText(notes.getDesc());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public  class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView notesTime;
        TextView notesDesc;
        public NotesViewHolder(View itemView){
            super(itemView);
            notesTime = itemView.findViewById(R.id.notes_time);
            notesDesc = itemView.findViewById(R.id.notes_desc);
        }
    }

    public void setData(ArrayList<Notes> notes) {
        this.notesList.clear();
        this.notesList.addAll(notes);
        notifyDataSetChanged();
    }

}
