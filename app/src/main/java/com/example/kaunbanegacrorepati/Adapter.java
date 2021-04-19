package com.example.kaunbanegacrorepati;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    LayoutInflater inflater;
    List<Question> questionList;

    public Adapter(Context ctx, List<Question> questionList){
        this.inflater = LayoutInflater.from(ctx);
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.questions_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.question.setText(questionList.get(position).getQuestion());
        holder.option1.setText(questionList.get(position).getOptions()[0]);
        holder.option2.setText(questionList.get(position).getOptions()[1]);
        holder.option3.setText(questionList.get(position).getOptions()[2]);
        holder.option4.setText(questionList.get(position).getOptions()[3]);

        if(questionList.get(position).getResponse().equals("A")){
            holder.option1.setBackgroundResource(R.drawable.selected_answer);
        }
        if(questionList.get(position).getResponse().equals("B")){
            holder.option2.setBackgroundResource(R.drawable.selected_answer);
        }
        if(questionList.get(position).getResponse().equals("C")){
            holder.option3.setBackgroundResource(R.drawable.selected_answer);
        }
        if(questionList.get(position).getResponse().equals("D")){
            holder.option4.setBackgroundResource(R.drawable.selected_answer);
        }

        if(holder.option1.getText().toString().equals(questionList.get(position).getCorrect_answer())){
            holder.option1.setBackgroundResource(R.drawable.correct_answer);
        }
        if(holder.option2.getText().toString().equals(questionList.get(position).getCorrect_answer())){
            holder.option2.setBackgroundResource(R.drawable.correct_answer);
        }
        if(holder.option3.getText().toString().equals(questionList.get(position).getCorrect_answer())){
            holder.option3.setBackgroundResource(R.drawable.correct_answer);
        }
        if(holder.option4.getText().toString().equals(questionList.get(position).getCorrect_answer())){
            holder.option4.setBackgroundResource(R.drawable.correct_answer);
        }
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView question, option1, option2, option3, option4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.setIsRecyclable(false);

            question = itemView.findViewById(R.id.result_question);
            option1 = itemView.findViewById(R.id.result_option1);
            option2 = itemView.findViewById(R.id.result_option2);
            option3 = itemView.findViewById(R.id.result_option3);
            option4 = itemView.findViewById(R.id.result_option4);
        }
    }
}
