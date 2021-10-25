package com.example.readon;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readon.databinding.DuelTextAnswerItemBinding;
import com.example.readon.model.DuelAnswer;
import java.util.List;

public class DuelTextAnswerAdapter extends RecyclerView.Adapter<DuelTextAnswerAdapter.AnswerViewHolder> {

    private List<DuelAnswer> answers;
    private DuelAnswerQuestion listener;

    public DuelTextAnswerAdapter(List<DuelAnswer> answers, DuelAnswerQuestion listener) {
        this.answers = answers;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        DuelTextAnswerItemBinding binding = DuelTextAnswerItemBinding.inflate(layoutInflater, parent, false);
        return new AnswerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerViewHolder holder, int position) {
        DuelAnswer answer = answers.get(position);
        holder.bind(answer);
        holder.itemBinding.cbAnswer.setOnClickListener(v -> {
            answer.setAnswer(!answer.getQuestionAnswer());
            resetAllAnswer(position);
            notifyDataSetChanged();
        });
        holder.itemBinding.btnRemoveAnswer.setOnClickListener(v -> listener.onAnswerRemove(position));
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    private void resetAllAnswer(int position) {
        for (int i = 0; i < answers.size(); i++) {
            if (i == position) continue;
            answers.get(i).setAnswer(false);
        }
    }

    static class AnswerViewHolder extends RecyclerView.ViewHolder {

        private DuelTextAnswerItemBinding itemBinding;

        public AnswerViewHolder(@NonNull DuelTextAnswerItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        public void bind(DuelAnswer answer) {
            itemBinding.setAnswer(answer);
        }
    }

    public interface DuelAnswerQuestion {
        void onAnswerRemove(int position);
    }
}
