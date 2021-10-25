package com.example.readon;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readon.model.Duel;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

public class DuelRequestAdapter extends RecyclerView.Adapter<DuelRequestAdapter.DuelRequestViewHolder> {

    private DuelRequestAction action;
    private ArrayList<Duel> duelRequests;

    public DuelRequestAdapter(DuelRequestAction action) {
        this.action = action;
        this.duelRequests = new ArrayList<>();
    }

    @NonNull
    @Override
    public DuelRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.duel_request_item, parent, false);
        return new DuelRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DuelRequestViewHolder holder, int position) {
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

    class DuelRequestViewHolder extends RecyclerView.ViewHolder {

        private TextView tvRequestFrom, tvTitle, tvTimeLimit, tvExpired;
        private Button btnAccept, btnDecline;
        private ImageView ivArrow;
        private ExpandableLayout elContainer;

        public DuelRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRequestFrom = itemView.findViewById(R.id.tv_request_from);
            tvTitle = itemView.findViewById(R.id.title);
            tvTimeLimit = itemView.findViewById(R.id.tv_time_limit);
            tvExpired = itemView.findViewById(R.id.tv_expired);
            btnAccept = itemView.findViewById(R.id.btn_accept);
            btnDecline = itemView.findViewById(R.id.btn_decline);
            ivArrow = itemView.findViewById(R.id.iv_arrow);
            elContainer = itemView.findViewById(R.id.el_container);
        }

        public void bind(Duel duel) {
            tvRequestFrom.setText(String.format("Duel request from: %s", duel.getOpponentUsername()));
            tvTitle.setText(String.format("Duel request from: %s", duel.getOpponentUsername()));
            btnAccept.setOnClickListener(v -> action.onAccept(duel));

            btnDecline.setOnClickListener(v -> action.onDecline(duel));

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

    interface DuelRequestAction {
        void onAccept(Duel duel);
        void onDecline(Duel duel);
    }
}
