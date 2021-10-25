package com.example.readon;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readon.databinding.DuelTextQuestionItemBinding;
import com.example.readon.model.DuelQuestion;

import java.util.List;

public class DuelTextQuestionAdapter extends RecyclerView.Adapter<DuelTextQuestionAdapter.QuestionViewHolder> {

    private List<DuelQuestion> questions;
    private DuelQuestionAction listener;

    public DuelTextQuestionAdapter(List<DuelQuestion> questions, DuelQuestionAction listener) {
        this.questions = questions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        DuelTextQuestionItemBinding binding = DuelTextQuestionItemBinding.inflate(layoutInflater, parent, false);
        return new QuestionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        DuelQuestion question = questions.get(position);
        holder.bind(question, position, this);

        holder.itemBinding.btnRemoveQuestion.setOnClickListener(v -> listener.onQuestionRemove(position));
        holder.itemBinding.btnAddAnswer.setOnClickListener(v -> listener.onNewAnswerAdded(position));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    static class QuestionViewHolder extends RecyclerView.ViewHolder {

        private DuelTextQuestionItemBinding itemBinding;

        public QuestionViewHolder(@NonNull DuelTextQuestionItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        public void bind(DuelQuestion question, int position, DuelTextQuestionAdapter adapter) {
            itemBinding.tvTitle.setText("Question " + (position + 1));
            itemBinding.setDuelQuestion(question);

            DuelTextAnswerAdapter answerAdapter = new DuelTextAnswerAdapter(question.getAnswers(), answerPosition -> {
                question.getAnswers().remove(answerPosition);
                adapter.notifyDataSetChanged();
            });
            itemBinding.rvAnswers.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            itemBinding.rvAnswers.setAdapter(answerAdapter);
        }
    }

    public interface DuelQuestionAction {
        void onQuestionRemove(int position);
        void onNewAnswerAdded(int position);
    }
}
