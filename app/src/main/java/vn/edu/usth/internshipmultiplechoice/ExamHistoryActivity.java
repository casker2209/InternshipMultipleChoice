package vn.edu.usth.internshipmultiplechoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.internshipmultiplechoice.adapter.ExamHistoryListAdapter;
import vn.edu.usth.internshipmultiplechoice.object.ExamHistory;

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
        Intent intent = getIntent();
        ExamHistory examHistory = (ExamHistory) intent.getSerializableExtra("Exam");
        examHistoryList = new ArrayList<>();
        examHistoryList.add(examHistory);
        recyclerView = findViewById(R.id.ExamHistoryList);
        recyclerView.setAdapter(new ExamHistoryListAdapter(examHistoryList,getApplicationContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }
}