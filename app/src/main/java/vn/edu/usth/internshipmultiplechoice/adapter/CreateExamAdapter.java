package vn.edu.usth.internshipmultiplechoice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.internshipmultiplechoice.R;
import vn.edu.usth.internshipmultiplechoice.object.Question;

public class CreateExamAdapter extends RecyclerView.Adapter<CreateExamAdapter.ViewHolder> {
    List<Question> questionList;
    Context context;
    public CreateExamAdapter(Context context){
        this.context = context;
        this.questionList = new ArrayList<Question>();
    }
    public CreateExamAdapter(Context context,List<Question> questionList){
        this.context = context;
        this.questionList = questionList;
    }
    @NonNull
    @Override
    public CreateExamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_create_exam, parent, false);
        CreateExamAdapter.ViewHolder viewHolder = new CreateExamAdapter.ViewHolder(view);
        return viewHolder;    }

    @Override
    public void onBindViewHolder(@NonNull CreateExamAdapter.ViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.A.setText(question.getAnswer().get(0));
        holder.B.setText(question.getAnswer().get(1));
        holder.C.setText(question.getAnswer().get(2));
        holder.D.setText(question.getAnswer().get(3));
        holder.QuestionName.setText(question.getName());
        for(String correct: question.getQuestionCorrect()){
            if(correct.equals(question.getAnswer().get(0))){
                holder.ButtonA.setChecked(true);
            }
            if(correct.equals(question.getAnswer().get(1))){
                holder.ButtonB.setChecked(true);
            }
            if(correct.equals(question.getAnswer().get(2))){
                holder.ButtonC.setChecked(true);
            }
            if(correct.equals(question.getAnswer().get(3))){
                holder.ButtonD.setChecked(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView A,B,C,D,QuestionName;
        RadioButton ButtonA,ButtonB,ButtonC,ButtonD;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            A =  itemView.findViewById(R.id.QuestionA);
            B = itemView.findViewById(R.id.QuestionB);
            C = itemView.findViewById(R.id.QuestionC);
            D = itemView.findViewById(R.id.QuestionD);
            ButtonA = itemView.findViewById(R.id.QuestionAButton);
            ButtonB = itemView.findViewById(R.id.QuestionBButton);
            ButtonC = itemView.findViewById(R.id.QuestionCButton);
            ButtonD = itemView.findViewById(R.id.QuestionDButton);
            QuestionName = itemView.findViewById(R.id.QuestionName);
        }
    }


}
