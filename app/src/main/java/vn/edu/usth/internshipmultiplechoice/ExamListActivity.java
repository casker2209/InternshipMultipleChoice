package vn.edu.usth.internshipmultiplechoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.internshipmultiplechoice.adapter.ExamListAdapter;
import vn.edu.usth.internshipmultiplechoice.object.Exam;
import vn.edu.usth.internshipmultiplechoice.object.ExamMini;
import vn.edu.usth.internshipmultiplechoice.retrofit.RetrofitClient;

public class ExamListActivity extends AppCompatActivity {
    RetrofitClient retrofit;
    List<ExamMini> examList;
    RecyclerView recyclerView;
    ExamListAdapter examListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            getList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examlist);
    }

    public void init(){
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerViewExamList);
        examListAdapter = new ExamListAdapter(examList,this);
        recyclerView.setAdapter(examListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

    }
    public void getList() throws IOException {
        retrofit = RetrofitClient.getInstance();
        examList = new ArrayList<>();
       Call<List<ExamMini>> examCall = retrofit.getMyApi().getAllExam();
        examCall.enqueue(new Callback<List<ExamMini>>() {
            @Override
            public void onResponse(Call<List<ExamMini>> call, Response<List<ExamMini>> response) {
                examList = response.body();
                System.out.println("Succeed");
                System.out.println(response.body().get(0).getName());
                init();
            }

            @Override
            public void onFailure(Call<List<ExamMini>> call, Throwable t) {

            }
        });
    }
}