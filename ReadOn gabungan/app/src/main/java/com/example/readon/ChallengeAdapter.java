package com.example.readon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readon.model.Challenge;

import java.util.ArrayList;

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.MyViewHolder> {
    private ArrayList<Challenge> challengeList;

    public ChallengeAdapter(ArrayList<Challenge> challengeList){
        this.challengeList = challengeList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView challengeName, challengeDesc, reward, progress;
        private ProgressBar progressBar;

        public MyViewHolder(final View itemView){
            super(itemView);
            challengeName = itemView.findViewById(R.id.challengename);
            challengeDesc = itemView.findViewById(R.id.challengedesc);
            reward = itemView.findViewById(R.id.reward);
            progress = itemView.findViewById(R.id.progress);
            progressBar = itemView.findViewById(R.id.progress_bar);

        }
    }

    @NonNull
    @Override
    public ChallengeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_challenge, parent, false);
        return new ChallengeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeAdapter.MyViewHolder holder, int position) {
        Challenge challenge = challengeList.get(position);
        holder.challengeName.setText(challenge.getChallengeName());
        holder.challengeDesc.setText(challenge.getChallengeDesc());
        holder.reward.setText(""+challenge.getReward());
        holder.progress.setText(""+challenge.getProgress()+"%");
        holder.progressBar.setProgress(challenge.getProgress());

    }

    @Override
    public int getItemCount() {
        return challengeList.size();
    }




}
