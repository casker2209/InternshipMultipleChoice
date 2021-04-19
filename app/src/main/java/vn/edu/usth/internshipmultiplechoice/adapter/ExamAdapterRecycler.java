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
    static final int Question4Answer = 1;
    static final int Question5Answer = 2;
    Exam exam;
    Context context;
    List<String> chosen;
    int hasDone;

    public int getHasDone() {
        return hasDone;
    }

    public ExamAdapterRecycler(Context context, Exam exam){
        this.context = context;
        this.exam = exam;
        this.chosen = new ArrayList<>();
        for(int i = 0;i<exam.getQuestionList().size();i++){
            chosen.add("Not chosen");
        }
        this.hasDone = 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(exam.getQuestionList().get(position).isHasE()){
            return Question4Answer;
        }
        else return Question5Answer;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 1)
        {Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ExamView = inflater.inflate(R.layout.item_exam, parent, false);
        ExamAdapterRecycler.ViewHolder viewHolder = new ExamAdapterRecycler.ViewHolder(ExamView);
        return viewHolder;}
        else{
            LayoutInflater inflater = LayoutInflater.from(context);
            View ExamView = inflater.inflate(R.layout.item_exam, parent, false);
            ExamAdapterRecycler.ViewHolder viewHolder = new ExamAdapterRecycler.ViewHolder(ExamView);
            return viewHolder;
        }
    }

    public Question getQuestion(int position){
        return exam.getQuestionList().get(position);
    }
    public String getChosen(int position){
        return chosen.get(position);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = exam.getQuestionList().get(position);
        holder.A.setText("A: "+question.getA());
        holder.B.setText("B: "+question.getB());
        holder.C.setText("C: "+question.getC());
        holder.D.setText("D: "+question.getD());
        holder.Name.setText(question.getName());
        holder.Num.setText("Question "+String.valueOf(position+1));
        switch(chosen.get(position)){
            case "A":
                holder.ButtonA.setChecked(true);
                break;
            case "B":
                holder.ButtonB.setChecked(true);
                break;
            case "C":
                holder.ButtonC.setChecked(true);
                break;
            case "D":
                holder.ButtonD.setChecked(true);
                break;
            default:
                break;
        }
        holder.buttonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.ButtonA:
                        if(chosen.get(position).equals("Not chosen")){
                            hasDone++;
                            notifyDataSetChanged();

                        }
                        chosen.add(position,"A");
                        break;
                    case R.id.ButtonB:
                        if(chosen.get(position).equals("Not chosen"))
                        {
                            hasDone++;
                            notifyDataSetChanged();
                        }
                        chosen.add(position,"B");
                        break;
                    case R.id.ButtonC:
                        if(chosen.get(position).equals("Not chosen")) {
                            hasDone++;
                            notifyDataSetChanged();
                        }
                        chosen.add(position,"C");
                        break;
                    case R.id.ButtonD:
                        if(chosen.get(position).equals("Not chosen")) {
                            hasDone++;
                            notifyDataSetChanged();
                        }
                        chosen.add(position,"D");
                        break;
                    default:{
                        break;
                    }
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return exam.getQuestionList().size();
    }

    public class ViewHolderWithE extends RecyclerView.ViewHolder{
        TextView A,B,C,D,Name,Num;
        RadioButton ButtonA,ButtonB,ButtonC,ButtonD;
        RadioGroup buttonGroup;
        String correct;
        public ViewHolderWithE(@NonNull View view) {
            super(view);
            this.setIsRecyclable(false);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView A,B,C,D,Name,Num;
        RadioButton ButtonA,ButtonB,ButtonC,ButtonD;
        RadioGroup buttonGroup;
        String correct;
        public ViewHolder(@NonNull View view) {
            super(view);
            this.setIsRecyclable(false);
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
