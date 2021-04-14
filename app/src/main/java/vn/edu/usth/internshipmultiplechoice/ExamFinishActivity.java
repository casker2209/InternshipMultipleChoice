package vn.edu.usth.internshipmultiplechoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import vn.edu.usth.internshipmultiplechoice.object.ExamHistory;

public class ExamFinishActivity extends AppCompatActivity {
    TextView ExamName,ExamDescription,ExamScore,RightQuestionShow,WrongQuestionShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_finish);

    }

    public void init(){
        Intent intent = getIntent();
        ExamHistory examHistory = (ExamHistory) intent.getSerializableExtra("exam result");

    }
}