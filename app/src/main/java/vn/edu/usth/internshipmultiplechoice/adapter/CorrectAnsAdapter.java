package vn.edu.usth.internshipmultiplechoice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.internshipmultiplechoice.ExamHistoryActivity;
import vn.edu.usth.internshipmultiplechoice.R;
import vn.edu.usth.internshipmultiplechoice.object.Question;

public class CorrectAnsAdapter extends RecyclerView.Adapter<CorrectAnsAdapter.ViewHolder> {
    private List<Question> question;
    private Context context;
    private boolean isCorrect;
    public CorrectAnsAdapter(List<Question> question, Context context,boolean isCorrect){
        this.question = question;
        this.context = context;
        this.isCorrect = isCorrect;
    }

    @NonNull
    @Override
    public CorrectAnsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_examanswercorrect, parent, false);
        CorrectAnsAdapter.ViewHolder viewHolder = new CorrectAnsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question _question = question.get(position);
        holder.questionName.setText(_question.getName());
        List<String> chosen = _question.getQuestionCorrect();
        if(isCorrect) {
            holder.correctView.setAdapter(new AnswerAdapter(this.context, _question.getQuestionCorrect(), AnswerAdapter.Correct));
            holder.correctView.setBackgroundColor(Color.GREEN);
        }
        else{
            holder.correctView.setAdapter(new AnswerAdapter(this.context,_question.getQuestionCorrect(),AnswerAdapter.NotChosen));
            holder.correctView.setBackgroundColor(Color.YELLOW);
        }
        holder.correctView.setLayoutManager(new LinearLayoutManager(this.context));
        holder.correctView.addItemDecoration(new DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL));
        }

    @Override
    public int getItemCount() {
        return question.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
         RecyclerView correctView;
         TextView questionName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            correctView = itemView.findViewById(R.id.CorrectAnswer);
            questionName = itemView.findViewById(R.id.QuestionName);
        }
    }
}
