package vn.edu.usth.internshipmultiplechoice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.internshipmultiplechoice.R;


public class AnswerAdapter  extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {
    static int Correct = 0;
    static int Incorrect = 1;
    static int NotChosen = 2;
    Context context;
    List<String> answer;
    int type;
    public AnswerAdapter(Context context,List<String> answer,int type){
        this.context = context;
        this.answer = answer;
        this.type = type;
    }
    @NonNull
    @Override
    public AnswerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_answer, parent, false);
        AnswerAdapter.ViewHolder viewHolder = new AnswerAdapter.ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter.ViewHolder holder, int position) {
        holder.answerText.setText(answer.get(position));
    }

    @Override
    public int getItemCount() {
        return answer.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView answerText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            answerText = itemView.findViewById(R.id.AnswerText);
        }
    }
}
