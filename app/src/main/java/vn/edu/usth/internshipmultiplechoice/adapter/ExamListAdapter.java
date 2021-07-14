package vn.edu.usth.internshipmultiplechoice.adapter;

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

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.internshipmultiplechoice.ExamActivity;
import vn.edu.usth.internshipmultiplechoice.R;
import vn.edu.usth.internshipmultiplechoice.object.Exam;
import vn.edu.usth.internshipmultiplechoice.object.ExamMini;
import vn.edu.usth.internshipmultiplechoice.object.Question;
import vn.edu.usth.internshipmultiplechoice.retrofit.RetrofitClient;
import vn.edu.usth.internshipmultiplechoice.utility.UserSharedPreferences;

public class ExamListAdapter extends RecyclerView.Adapter<ExamListAdapter.ViewHolder> {
    List<ExamMini> examList;
    Context context;
    Exam exam;

    public ExamListAdapter(List examList, Context context) {
        this.examList = examList;
        this.context = context;
    }


    @NonNull
    @Override
    public ExamListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ExamView = inflater.inflate(R.layout.item_examlist, parent, false);

        ExamListAdapter.ViewHolder viewHolder = new ExamListAdapter.ViewHolder(ExamView);
        return viewHolder;
    }

    public void getQuestion(ExamMini examMini){
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        String id = examMini.getId();
        Call<Exam> examCall = retrofitClient.getMyApi().getExam(id);
        examCall.enqueue(new Callback<Exam>() {
            @Override
            public void onResponse(Call<Exam> call, Response<Exam> response) {
                for(Question question: response.body().getQuestionList()) {
                    Collections.shuffle(question.getAnswer());
                }
                exam = response.body();
                Intent intent = new Intent(context,ExamActivity.class);
                boolean isUser = UserSharedPreferences.hasUser(context);
                intent.putExtra("exam",exam);
                context.startActivity(intent);
            }

            @Override
            public void onFailure(Call<Exam> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ExamListAdapter.ViewHolder holder, int position) {
        ExamMini examMini = examList.get(position);
        holder.descView.setText(examList.get(position).getDescription());
        holder.nameView.setText(examList.get(position).getName());
        holder.Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuestion(examMini);
            }
        });
    }


    @Override
    public int getItemCount() {
        return examList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        private TextView descView;
        private Button Go;
        public ViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.ExamName);
            descView = itemView.findViewById(R.id.Description);
            Go = itemView.findViewById(R.id.goButton);
        }
    }
}

