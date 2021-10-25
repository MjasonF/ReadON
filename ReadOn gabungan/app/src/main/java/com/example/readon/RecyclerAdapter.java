package com.example.readon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readon.model.Duel;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private ArrayList<Duel> duelList;

    public RecyclerAdapter(ArrayList<Duel> duelList){
        this.duelList = duelList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title, request, type, time, point1, point2, winner, result;
        private CardView resultbg, titlebg;
        private ConstraintLayout expendableLayout;

        public MyViewHolder(final View view){
            super(view);
            title = view.findViewById(R.id.title);
            request = view.findViewById(R.id.request);
            type = view.findViewById(R.id.type);
            time = view.findViewById(R.id.time);
            point1 = view.findViewById(R.id.point1);
            point2 = view.findViewById(R.id.point2);
            winner = view.findViewById(R.id.winner);
            result = view.findViewById(R.id.result);
            resultbg = view.findViewById(R.id.resultbg);
            titlebg = view.findViewById(R.id.titlebg);
            expendableLayout = view.findViewById(R.id.expandableLayout);

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     Duel duel = duelList.get(getAdapterPosition());
                     duel.setExpanded(!duel.isExpanded());
                     notifyItemChanged(getAdapterPosition());
                }
            });
        }

    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        Duel duel = duelList.get(position);
        holder.title.setText("Duel History at "+duel.getTitle()+" with "+duel.getRequest());
        holder.request.setText("Duel Request From : "+duel.getRequest());
        holder.type.setText("Duel Type : "+duel.getType());
        holder.time.setText("Time Limit : "+duel.getTime());
//        holder.point1.setText("Your Point : "+duel.getPoint1());
//        holder.point2.setText("Opponent Point: "+duel.getPoint2());
//        holder.winner.setText("Winner : "+duel.getWinner());
//        if(duel.getResult() == 0){
//            holder.result.setText("Lose");
//            holder.resultbg.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.red));
//            holder.titlebg.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.red));
//        }else{
//            holder.result.setText("Win");
//            holder.resultbg.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.green));
//            holder.titlebg.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.green));
//        }
        boolean isExpanded = duelList.get(position).isExpanded();
        holder.expendableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return duelList.size();
    }
}
