package com.example.readon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.readon.datamodel.FriendlistItemResponse;
import com.example.readon.datamodel.FriendlistResponse;
import com.example.readon.datamodel.FriendreqItemResponse;
import com.example.readon.datamodel.FriendreqResponse;
import com.example.readon.pref.AppPreference;

import java.util.ArrayList;
import java.util.List;

public class FriendReqAdapter extends RecyclerView.Adapter<FriendReqAdapter.friendreqViewHolder> {

    ArrayList<FriendreqResponse> friendreq = new ArrayList<>();

    private AppPreference pref;
    private Friendreq action;

    public FriendReqAdapter(AppPreference pref, Friendreq action) {
        this.pref = pref;
        this.action = action;
    }

    @NonNull
    @Override
    public friendreqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_friendreq, parent, false);
        return new FriendReqAdapter.friendreqViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull friendreqViewHolder holder, int position) {
        FriendreqResponse friendreqResponse = friendreq.get(position);
        FriendreqItemResponse friendData = friendreqResponse.getUserId();

        holder.username.setText(friendData.getUsername());
        if (friendData.getIsOnline()) {
            holder.statustext.setText("Online");
            holder.status.setImageResource(R.drawable.status2);
        } else {
            holder.statustext.setText("Offline");
            holder.status.setImageResource(R.drawable.status);
        }
        Glide.with(holder.itemView.getContext()).load(friendData.getAvatar()).into(holder.avatar);

        holder.accept.setOnClickListener(v-> {
            action.onAcceptClick(friendreqResponse);
        });

        holder.reject.setOnClickListener(v -> {
            action.onDeleteClick(friendreqResponse);
        });
    }

    public void setData(List<FriendreqResponse> friendreq) {
        this.friendreq.clear();
        this.friendreq.addAll(friendreq);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return friendreq.size();
    }

    public class friendreqViewHolder extends RecyclerView.ViewHolder {
        private TextView username, statustext;
        private ImageView avatar, status, accept, reject;

        public friendreqViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            statustext = itemView.findViewById(R.id.statustext);
            avatar = itemView.findViewById(R.id.avatar);
            status = itemView.findViewById(R.id.status);
            accept = itemView.findViewById(R.id.accept);
            reject = itemView.findViewById(R.id.reject);


        }
    }

    interface Friendreq {
        void onDeleteClick(FriendreqResponse friendreqResponse);

        void onAcceptClick(FriendreqResponse friendreqResponse);
    }
}
