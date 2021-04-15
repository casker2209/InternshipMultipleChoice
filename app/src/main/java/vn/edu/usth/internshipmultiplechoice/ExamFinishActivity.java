package vn.edu.usth.internshipmultiplechoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import vn.edu.usth.internshipmultiplechoice.adapter.CorrectAnsAdapter;
import vn.edu.usth.internshipmultiplechoice.adapter.IncorrectAnsAdapter;
import vn.edu.usth.internshipmultiplechoice.object.ExamHistory;

public class ExamFinishActivity extends AppCompatActivity {
    TextView ExamName,ExamDescription,ExamScore,RightQuestionShow,WrongQuestionShow;
    RecyclerView RightQuestionList,WrongQuestionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_finish);
        init();
    }

    public void init(){
        Intent intent = getIntent();
        ExamHistory examHistory = (ExamHistory) intent.getSerializableExtra("exam result");
        ExamName = findViewById(R.id.ExamName);
        ExamScore =findViewById(R.id.ExamScoreContent);
        ExamName.setText(examHistory.getExam().getName());
        ExamScore.setText(examHistory.getScore());
        RightQuestionList = findViewById(R.id.ExamCorrectList);
        RightQuestionList.setAdapter(new CorrectAnsAdapter(examHistory.getQuestionRight(),this));
        RightQuestionList.setLayoutManager(new LinearLayoutManager(this));
        WrongQuestionList = findViewById(R.id.ExamIncorrectList);
        WrongQuestionList.setAdapter(new IncorrectAnsAdapter(examHistory.getQuestionWrong(),examHistory.getIncorrectChosen(),this));
        WrongQuestionList.setLayoutManager(new LinearLayoutManager(this));
    }
}