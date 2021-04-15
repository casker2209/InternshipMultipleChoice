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

public class IncorrectAnsAdapter extends RecyclerView.Adapter<IncorrectAnsAdapter.ViewHolder> {
    private List<Question> question;
    private Context context;
    private List<String> chosen;
    public IncorrectAnsAdapter(List<Question> question,List<String> chosen,Context context){
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
        String incorrect = chosen.get(position);
        String chosen = _question.getQuestionCorrect();
        holder.QuestionName.setText(_question.getName());
        switch(chosen){
            case("A"):{
                holder.QuestionCorrect.setText("A: "+_question.getA());
                break;
            }
            case("B"):{
                holder.QuestionCorrect.setText("B: "+_question.getB());
                break;
            }
            case("C"):{
                holder.QuestionCorrect.setText("C: "+_question.getC());
                break;
            }
            case("D"):{
                holder.QuestionCorrect.setText("D: "+_question.getD());
                break;
            }
            default:break;
        }
        switch (incorrect){
            case("A"):{
                holder.QuestionIncorrect.setText("A: "+_question.getA());
                break;
            }
            case("B"):{
                holder.QuestionIncorrect.setText("B: "+_question.getB());
                break;
            }
            case("C"):{
                holder.QuestionIncorrect.setText("C: "+_question.getC());
                break;
            }
            case("D"):{
                holder.QuestionIncorrect.setText("D: "+_question.getD());
                break;
            }
            default:break;

        }

    }

    @Override
    public int getItemCount() {
        return question.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView QuestionCorrect,QuestionIncorrect,QuestionName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            QuestionCorrect = itemView.findViewById(R.id.CorrectAnswer);
            QuestionIncorrect = itemView.findViewById(R.id.IncorrectAnswer);
            QuestionName = itemView.findViewById(R.id.QuestionName);
        }
    }
}
