package vn.edu.usth.internshipmultiplechoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.internshipmultiplechoice.adapter.ExamHistoryListAdapter;
import vn.edu.usth.internshipmultiplechoice.object.ExamResult;
import vn.edu.usth.internshipmultiplechoice.retrofit.RetrofitClient;
import vn.edu.usth.internshipmultiplechoice.retrofit.User;
import vn.edu.usth.internshipmultiplechoice.utility.UserSharedPreferences;

public class ExamHistoryActivity extends AppCompatActivity {
    List<ExamResult> examResultList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_history);
        init();
    }
    public void init(){
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        User user = UserSharedPreferences.getUser(this);
        System.out.println("ID :" + user.getId());
        System.out.println("Token :" + user.getAccessToken());
        Call<List<ExamResult>> getExam = retrofitClient.getMyApi().getExamHistory(user.getId(),user.getAccessToken());
        getExam.enqueue(new Callback<List<ExamResult>>() {
            @Override
            public void onResponse(Call<List<ExamResult>> call, Response<List<ExamResult>> response) {
                examResultList = response.body();
                recyclerView = findViewById(R.id.ExamHistoryList);
                recyclerView.setAdapter(new ExamHistoryListAdapter(examResultList,getApplicationContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.addItemDecoration(new DividerItemDecoration(ExamHistoryActivity.this, LinearLayoutManager.VERTICAL));

            }

            @Override
            public void onFailure(Call<List<ExamResult>> call, Throwable t) {
                startActivity(new Intent(ExamHistoryActivity.this,MenuActivity.class));
                finish();
            }
        });
    }

}