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
import com.example.readon.pref.AppPreference;

import java.util.ArrayList;
import java.util.List;

public class FriendlistAdapter extends RecyclerView.Adapter<FriendlistAdapter.friendlistViewHolder> {

    ArrayList<FriendlistResponse>friendlist = new ArrayList<>();

    private AppPreference pref;
    private Friendlist action;

    public FriendlistAdapter(AppPreference pref, Friendlist action){
        this.pref = pref;
        this.action = action;
    }

    @NonNull
    @Override
    public friendlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_friend, parent, false);
        return new FriendlistAdapter.friendlistViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull friendlistViewHolder holder, int position) {
        FriendlistResponse friendlistResponse = friendlist.get(position);
        FriendlistItemResponse friendData;

        if(friendlistResponse.getFriend().getUserId().equals(pref.getUserId())){
            friendData = friendlistResponse.getUser();
        }
        else{
            friendData = friendlistResponse.getFriend();
        }

        holder.username.setText(friendData.getUsername());
        if (friendData.getOnline()){
            holder.statustext.setText("Online");
            holder.status.setImageResource(R.drawable.status2);
        }else{
            holder.statustext.setText("Offline");
            holder.status.setImageResource(R.drawable.status);
        }
        Glide.with(holder.itemView.getContext()).load(friendData.getAvatar()).into(holder.avatar);

        holder.delete.setOnClickListener(v -> {
            action.onDeleteClick(friendlistResponse);
        });
    }

    public void setData(List<FriendlistResponse> friendlist){
        this.friendlist.clear();
        this.friendlist.addAll(friendlist);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return friendlist.size();
    }

    public class friendlistViewHolder extends RecyclerView.ViewHolder{
        private TextView username, statustext;
        private ImageView avatar, status, delete;

        public friendlistViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            statustext = itemView.findViewById(R.id.statustext);
            avatar = itemView.findViewById(R.id.avatar);
            status = itemView.findViewById(R.id.status);
            delete = itemView.findViewById(R.id.delete);



        }
    }

    interface Friendlist{
        void onDeleteClick(FriendlistResponse friendlistResponse);
    }
}
