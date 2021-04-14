package vn.edu.usth.internshipmultiplechoice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.internshipmultiplechoice.R;
import vn.edu.usth.internshipmultiplechoice.object.Exam;
import vn.edu.usth.internshipmultiplechoice.object.Question;

public class ExamAdapterRecycler extends RecyclerView.Adapter<ExamAdapterRecycler.ViewHolder> {
    Exam exam;
    Context context;
    public ExamAdapterRecycler(Context context, Exam exam){
        this.context = context;
        this.exam = exam;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ExamView = inflater.inflate(R.layout.item_exam, parent, false);
        ExamAdapterRecycler.ViewHolder viewHolder = new ExamAdapterRecycler.ViewHolder(ExamView);
        return viewHolder;
    }

    public Question getQuestion(int position){
        return exam.getQuestionList().get(position);
    }
    public String getCorrect(int position){
        return "";
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = exam.getQuestionList().get(position);
        holder.A.setText(question.getA());
        holder.B.setText(question.getB());
        holder.C.setText(question.getC());
        holder.D.setText(question.getD());
        holder.Name.setText(question.getName());
        holder.Num.setText("Question "+String.valueOf(position+1));

        holder.buttonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.ButtonA:
                        holder.correct = "A";
                        break;
                    case R.id.ButtonB:
                        holder.correct = "B";
                        break;
                    case R.id.ButtonC:
                        holder.correct = "C";
                        break;
                    case R.id.ButtonD:
                        holder.correct = "D";
                        break;
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return exam.getQuestionList().size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView A,B,C,D,Name,Num;
        RadioButton ButtonA,ButtonB,ButtonC,ButtonD;
        RadioGroup buttonGroup;
        String correct;
        public ViewHolder(@NonNull View view) {
            super(view);
            A = view.findViewById(R.id.TextA);
            B = view.findViewById(R.id.TextB);
            C = view.findViewById(R.id.TextC);
            D = view.findViewById(R.id.TextD);
            buttonGroup = view.findViewById(R.id.buttonGroup);
            ButtonA = (RadioButton) view.findViewById(R.id.ButtonA);
            ButtonB = (RadioButton)  view.findViewById(R.id.ButtonB);
            ButtonC = (RadioButton) view.findViewById(R.id.ButtonC);
            ButtonD = (RadioButton) view.findViewById(R.id.ButtonD);
            Name = view.findViewById(R.id.QuestionName);
            Num = view.findViewById(R.id.QuestionNum);

        }
    }
}
