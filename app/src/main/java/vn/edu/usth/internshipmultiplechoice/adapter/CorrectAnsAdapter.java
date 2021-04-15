package vn.edu.usth.internshipmultiplechoice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.internshipmultiplechoice.R;
import vn.edu.usth.internshipmultiplechoice.object.Question;

public class CorrectAnsAdapter extends RecyclerView.Adapter<CorrectAnsAdapter.ViewHolder> {
    private List<Question> question;
    private Context context;
    public CorrectAnsAdapter(List<Question> question, Context context){
        this.question = question;
        this.context = context;
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
        String chosen = _question.getQuestionCorrect();
        switch(chosen){
            case("A"):{
                holder.correctView.setText("A: "+_question.getA());
                break;
            }
            case("B"):{
                holder.correctView.setText("B: "+_question.getB());
                break;
            }
            case("C"):{
                holder.correctView.setText("C: "+_question.getC());
                break;
            }
            case("D"):{
                holder.correctView.setText("D: "+_question.getD());
                break;
            }

        }
    }

    @Override
    public int getItemCount() {
        return question.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
         TextView correctView;
         TextView questionName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            correctView = itemView.findViewById(R.id.CorrectAnswer);
            questionName = itemView.findViewById(R.id.QuestionName);
        }
    }
}
