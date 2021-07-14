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

import vn.edu.usth.internshipmultiplechoice.R;
import vn.edu.usth.internshipmultiplechoice.object.Question;

public class IncorrectAnsAdapter extends RecyclerView.Adapter<IncorrectAnsAdapter.ViewHolder> {
    private List<Question> question;
    private Context context;
    private List<List<String>> chosen;
    public IncorrectAnsAdapter(List<Question> question,List<List<String>> chosen,Context context){
        this.question = question;
        this.context = context;
        this.chosen = chosen;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_examanswerincorrect, parent, false);
        IncorrectAnsAdapter.ViewHolder viewHolder = new IncorrectAnsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question _question = question.get(position);
        List<String> incorrect = chosen.get(position);
        holder.QuestionName.setText(_question.getName());
        holder.QuestionCorrect.setAdapter(new AnswerAdapter(this.context,_question.getQuestionCorrect(),AnswerAdapter.Correct));
        holder.QuestionIncorrect.setAdapter(new AnswerAdapter(this.context,incorrect,AnswerAdapter.Incorrect));
        holder.QuestionCorrect.setLayoutManager(new LinearLayoutManager(this.context));
        holder.QuestionIncorrect.setLayoutManager(new LinearLayoutManager(this.context));
        holder.QuestionCorrect.addItemDecoration(new DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL));
        holder.QuestionIncorrect.addItemDecoration(new DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL));
        holder.QuestionIncorrect.setBackgroundColor(Color.RED);
        holder.QuestionCorrect.setBackgroundColor(Color.GREEN);


    }

    @Override
    public int getItemCount() {
        return question.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView QuestionName;
        private RecyclerView QuestionCorrect,QuestionIncorrect;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            QuestionCorrect = itemView.findViewById(R.id.CorrectAnswer);
            QuestionIncorrect = itemView.findViewById(R.id.IncorrectAnswer);
            QuestionName = itemView.findViewById(R.id.QuestionName);
        }
    }
}
