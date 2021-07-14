package vn.edu.usth.internshipmultiplechoice.adapter;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import vn.edu.usth.internshipmultiplechoice.BankTestActivity;
import vn.edu.usth.internshipmultiplechoice.ExamActivity;
import vn.edu.usth.internshipmultiplechoice.R;
import vn.edu.usth.internshipmultiplechoice.object.Bank;
import vn.edu.usth.internshipmultiplechoice.object.Exam;
import vn.edu.usth.internshipmultiplechoice.object.ExamMini;
import vn.edu.usth.internshipmultiplechoice.object.Question;
import vn.edu.usth.internshipmultiplechoice.retrofit.RetrofitClient;

public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.ViewHolder>  {
    List<Bank> bankList;
    Context context;
    Bank bank;
    int rotationAngle = 0;
    public BankListAdapter(List bankList, Context context) {
        this.bankList = bankList;
        this.context = context;
    }



    @NonNull
    @Override
    public BankListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ExamView = inflater.inflate(R.layout.item_banklist, parent, false);

        BankListAdapter.ViewHolder viewHolder = new BankListAdapter.ViewHolder(ExamView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull BankListAdapter.ViewHolder holder, int position) {
        Bank bank = bankList.get(position);
        holder.BankDescription.setText(bank.getDescription());
        holder.BankName.setText(bank.getName());
        holder.goExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Question> questionList = new ArrayList<>();
                if(bank.getQuestionList().size()< 30) {
                    while (questionList.size() < 30) {
                        int random = (int) Math.floor(Math.random() * (bank.getQuestionList().size() - 1));
                        questionList.add(bank.getQuestionList().get(random));
                    }
                }
                else{
                    questionList = bank.getQuestionList();
                }
                Exam exam = new Exam("-1",bank.getName(),bank.getDescription(),questionList);
                Intent intent = new Intent(context, ExamActivity.class);
                intent.putExtra("isGuest",true);
                intent.putExtra("exam",exam);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
        holder.goBankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context, BankTestActivity.class);
                intent.putExtra("bank",bank);
                context.startActivity(intent);
                ((Activity) context).finish();

            }
        });
    }

    @Override
    public int getItemCount() {
        return bankList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView BankName,BankDescription,goExam,goBank;
        Button goBankButton,goExamButton,expandButton;
        LinearLayout DescAndGo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            BankName = itemView.findViewById(R.id.BankName);
            BankDescription = itemView.findViewById(R.id.BankDescription);
            goExam = itemView.findViewById(R.id.goExam);
            goBank = itemView.findViewById(R.id.goBank);
            goBankButton = itemView.findViewById(R.id.goBankButton);
            goExamButton = itemView.findViewById(R.id.goExamButton);
            DescAndGo = itemView.findViewById(R.id.DescAndGo);
            expandButton = itemView.findViewById(R.id.expandButton);
            expandButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ObjectAnimator anim = ObjectAnimator.ofFloat(v, "rotation",rotationAngle, rotationAngle + 180);
                    anim.setDuration(500);
                    anim.start();
                    rotationAngle += 180;
                    rotationAngle = rotationAngle%360;


                    if(checkSize()){

                        DescAndGo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0));
                    }
                    else{
                        DescAndGo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    }
                }
            });
        }
        public boolean checkSize(){
            int height = DescAndGo.getHeight();
            if(height!=0){
                return true;
            }
            else{
                return false;
            }
        }
    }
}
