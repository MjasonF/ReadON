package com.example.readon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readon.model.Duel;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

public class DuelHistoryAdapter extends RecyclerView.Adapter<DuelHistoryAdapter.DuelHistoryViewHolder> {

    private ArrayList<Duel> duelRequests;

    public DuelHistoryAdapter() {
        this.duelRequests = new ArrayList<>();
    }

    @NonNull
    @Override
    public DuelHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.duel_history_item, parent, false);
        return new DuelHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DuelHistoryViewHolder holder, int position) {
        Duel duel = duelRequests.get(position);
        holder.bind(duel);
    }

    @Override
    public int getItemCount() {
        return duelRequests.size();
    }

    public void setData(List<Duel> duels) {
        duelRequests.clear();
        duelRequests.addAll(duels);
        notifyDataSetChanged();
    }

    class DuelHistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView tvRequestFrom, tvTitle, tvTimeLimit, tvExpired;
        private TextView tvPoint, tvOpponentPoint, tvWinner;
        private Button btnStatus;
        private ImageView ivArrow;
        private ExpandableLayout elContainer;

        public DuelHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRequestFrom = itemView.findViewById(R.id.tv_request_from);
            tvTitle = itemView.findViewById(R.id.title);
            tvTimeLimit = itemView.findViewById(R.id.tv_time_limit);
            tvExpired = itemView.findViewById(R.id.tv_expired);
            tvPoint = itemView.findViewById(R.id.tv_point);
            tvOpponentPoint = itemView.findViewById(R.id.tv_opponent_point);
            btnStatus = itemView.findViewById(R.id.btn_status);
            tvWinner = itemView.findViewById(R.id.tv_winner);
            ivArrow = itemView.findViewById(R.id.iv_arrow);
            elContainer = itemView.findViewById(R.id.el_container);
        }

        public void bind(Duel duel) {

            tvRequestFrom.setText(String.format("Duel request from: %s", duel.getOpponentUsername()));
            tvTitle.setText(String.format("Duel history at %s with %s", duel.getCreatedAt(), duel.getOpponentUsername()));

            tvPoint.setText("Your Point: " + duel.getPoint());
            tvOpponentPoint.setText("Opponent Point: " + duel.getOpponentPoint());
            tvWinner.setText("Winner: " + (duel.getDraw() ? "-" : duel.getWinner() ? "You" : duel.getOpponentUsername()));

            btnStatus.setText(duel.getDraw() ? "DRAW" : duel.getWinner() ? "WIN" : "LOSE");
            int color = duel.getDraw() ? ContextCompat.getColor(itemView.getContext(), R.color.cardview_dark_background)
                    : duel.getWinner() ? ContextCompat.getColor(itemView.getContext(), R.color.green)
                    : ContextCompat.getColor(itemView.getContext(), R.color.red);
            btnStatus.setBackgroundColor(color);

            ivArrow.setOnClickListener(v -> {
                if (elContainer.isExpanded()) {
                    ivArrow.setImageResource(R.drawable.ic_arrow_down);
                }
                else {
                    ivArrow.setImageResource(R.drawable.ic_arrow_up);
                }
                elContainer.setExpanded(!elContainer.isExpanded());
            });
        }
    }
}