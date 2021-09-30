package vn.edu.usth.internshipmultiplechoice.adapter;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.internshipmultiplechoice.BankTestActivity;
import vn.edu.usth.internshipmultiplechoice.ExamActivity;
import vn.edu.usth.internshipmultiplechoice.R;
import vn.edu.usth.internshipmultiplechoice.object.Bank;
import vn.edu.usth.internshipmultiplechoice.object.BankContent;
import vn.edu.usth.internshipmultiplechoice.object.BankInfo;
import vn.edu.usth.internshipmultiplechoice.object.Exam;
import vn.edu.usth.internshipmultiplechoice.object.Question;
import vn.edu.usth.internshipmultiplechoice.retrofit.RetrofitClient;

public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.ViewHolder>  {
    List<BankInfo> bankList;
    Context context;
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
        BankInfo bankInfo = bankList.get(position);
        holder.BankDescription.setText(bankInfo.getDescription());
        holder.BankName.setText(bankInfo.getName());
        holder.goExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(context).setTitle("Chọn số câu hỏi")
                        .setPositiveButton("Làm bài thi 30 câu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                createExam(30,bankInfo);
                            }
                        })
                        .setNeutralButton("Làm bài thi 60 câu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                createExam(60,bankInfo);
                            }
                        })
                        .setNegativeButton("Bỏ",null).create();
                dialog.show();





            }
        });
        holder.goBankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient retrofitClient = RetrofitClient.getInstance();
                String id = bankInfo.getId();
                Call<BankContent> bankCall = retrofitClient.getMyApi().getBank(id);
                bankCall.enqueue(new Callback<BankContent>() {
                    @Override
                    public void onResponse(Call<BankContent> call, Response<BankContent> response) {
                        BankContent content = response.body();
                        for(Question question: content.getQuestionList()) {
                            Collections.shuffle(question.getAnswer());
                        }
                        Bank bank = new Bank(content.getId(),bankInfo.getName(),bankInfo.getDescription(),content.getQuestionList());
                        Intent intent = new Intent( context, BankTestActivity.class);
                        intent.putExtra("bank",bank);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }

                    @Override
                    public void onFailure(Call<BankContent> call, Throwable t) {

                    }
                });


            }
        });
    }

    public List<Integer> getRandomNumber(int number){
       ArrayList<Integer> list = new ArrayList<>();
       while(list.size()<number){
           int random = (int) Math.ceil(Math.random()*number);
           if(!list.contains(random)) list.add(random);
       }
       return list;
    }

    public void createExam(int number,BankInfo bankInfo){
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        String id = bankInfo.getId();
        Call<BankContent> bankCall = retrofitClient.getMyApi().getBank(id);
        bankCall.enqueue(new Callback<BankContent>() {
            @Override
            public void onResponse(Call<BankContent> call, Response<BankContent> response) {
                BankContent content = response.body();
                Bank bank = new Bank(content.getId(),bankInfo.getName(),bankInfo.getDescription(),content.getQuestionList());
                List<Question> questionList = new ArrayList<>();
                List<Integer> randomIntList = getRandomNumber(number);
                for(int i = 0; i < number; i++){
                    questionList.add(bank.getQuestionList().get(randomIntList.get(i)));
                }
                for(Question question: content.getQuestionList()) {
                    Collections.shuffle(question.getAnswer());
                }
                Exam exam = new Exam("-1",bank.getName(),bank.getDescription(),questionList);
                Intent intent = new Intent(context, ExamActivity.class);
                intent.putExtra("exam",exam);
                context.startActivity(intent);
                ((Activity)context).finish();
            }

            @Override
            public void onFailure(Call<BankContent> call, Throwable t) {

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
