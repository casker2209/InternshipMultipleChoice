package vn.edu.usth.internshipmultiplechoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.edu.usth.internshipmultiplechoice.adapter.ExamHistoryListAdapter;
import vn.edu.usth.internshipmultiplechoice.object.Exam;
import vn.edu.usth.internshipmultiplechoice.object.ExamHistory;
import vn.edu.usth.internshipmultiplechoice.retrofit.RetrofitClient;
import vn.edu.usth.internshipmultiplechoice.retrofit.UserInfo;
import vn.edu.usth.internshipmultiplechoice.utility.UserSharedPreferences;

public class ExamHistoryActivity extends AppCompatActivity {
    List<ExamHistory> examHistoryList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_history);
        init();
    }
    public void init(){
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        UserInfo user = UserSharedPreferences.getUser(this);
        System.out.println("ID :" + user.getId());
        System.out.println("Token :" + user.getAccessToken());
        Call<List<ExamHistory>> getExam = retrofitClient.getMyApi().getExamHistory(user.getId(),user.getAccessToken());
        getExam.enqueue(new Callback<List<ExamHistory>>() {
            @Override
            public void onResponse(Call<List<ExamHistory>> call, Response<List<ExamHistory>> response) {
                examHistoryList = response.body();
                recyclerView = findViewById(R.id.ExamHistoryList);
                recyclerView.setAdapter(new ExamHistoryListAdapter(examHistoryList,getApplicationContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.addItemDecoration(new DividerItemDecoration(ExamHistoryActivity.this, LinearLayoutManager.VERTICAL));

            }

            @Override
            public void onFailure(Call<List<ExamHistory>> call, Throwable t) {
                startActivity(new Intent(ExamHistoryActivity.this,MenuActivity.class));
                finish();
            }
        });
    }

}