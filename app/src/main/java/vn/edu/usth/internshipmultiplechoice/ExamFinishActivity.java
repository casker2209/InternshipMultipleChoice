package vn.edu.usth.internshipmultiplechoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vn.edu.usth.internshipmultiplechoice.adapter.CorrectAnsAdapter;
import vn.edu.usth.internshipmultiplechoice.adapter.IncorrectAnsAdapter;
import vn.edu.usth.internshipmultiplechoice.object.ExamHistory;
import vn.edu.usth.internshipmultiplechoice.retrofit.RetrofitClient;

public class ExamFinishActivity extends AppCompatActivity {
    TextView ExamName,ExamDescription,ExamScore,RightQuestionShow,WrongQuestionShow,NotChosenShow,RedoExam;
    ExamHistory examHistory;
    RecyclerView RightQuestionList,WrongQuestionList,NotChosenList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_finish);
        init();
        sendHistory();
    }

    public void sendHistory(){
        RetrofitClient retrofit = RetrofitClient.getInstance();
        Call<String> stringResponse = retrofit.getMyApi().sendExam()
    }

    public void init() {
        Intent intent = getIntent();
        examHistory = (ExamHistory) intent.getSerializableExtra("exam result");
        ExamName = findViewById(R.id.ExamName);
        ExamScore = findViewById(R.id.ExamScoreContent);
        ExamName.setText(examHistory.getExam().getName());
        ExamScore.setText(examHistory.getScore());
        RightQuestionList = findViewById(R.id.ExamCorrectList);
        RightQuestionList.setAdapter(new CorrectAnsAdapter(examHistory.getQuestionRight(), this));
        RightQuestionList.setLayoutManager(new LinearLayoutManager(this));
        WrongQuestionList = findViewById(R.id.ExamIncorrectList);
        WrongQuestionList.setAdapter(new IncorrectAnsAdapter(examHistory.getQuestionWrong(), examHistory.getIncorrectChosen(), this));
        WrongQuestionList.setLayoutManager(new LinearLayoutManager(this));
        RightQuestionShow = findViewById(R.id.CorrectAnswerText);
        WrongQuestionShow = findViewById(R.id.IncorrectAnswerText);
        if(examHistory.getQuestionRight().size()!=0) {
            RightQuestionShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (RightQuestionList.getHeight() != 0) {
                        ViewGroup.LayoutParams params = RightQuestionList.getLayoutParams();
                        params.height = 0;
                        RightQuestionList.setLayoutParams(params);
                        RightQuestionShow.setText(getString(R.string.correct_show));

                    } else {
                        ViewGroup.LayoutParams params = RightQuestionList.getLayoutParams();
                        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                        RightQuestionList.setLayoutParams(params);
                        RightQuestionShow.setText(getString(R.string.correct_hide));

                    }
                }
            });
        }
        else{
            RightQuestionShow.setText(getString(R.string.correct_size_zero));
        }
        if(examHistory.getQuestionWrong().size()!=0) {
            WrongQuestionShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (WrongQuestionList.getHeight() != 0) {
                        ViewGroup.LayoutParams params = WrongQuestionList.getLayoutParams();
                        params.height = 0;
                        WrongQuestionList.setLayoutParams(params);
                        WrongQuestionShow.setText(getString(R.string.incorrect_show));

                    } else {
                        ViewGroup.LayoutParams params = WrongQuestionList.getLayoutParams();
                        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                        WrongQuestionList.setLayoutParams(params);
                        WrongQuestionShow.setText(getString(R.string.incorrect_hide));
                    }
                }
            });
        }
        else WrongQuestionShow.setText(getString(R.string.incorrect_size_zero));
        NotChosenList = findViewById(R.id.NotChosenList);
        NotChosenList.setAdapter(new CorrectAnsAdapter(examHistory.getQuestionNotChosen(), this));
        NotChosenList.setLayoutManager(new LinearLayoutManager(this));
        NotChosenShow = findViewById(R.id.NotChosenText);
        if (examHistory.getQuestionNotChosen().size() != 0) {
            NotChosenShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NotChosenList.getHeight() != 0) {
                        ViewGroup.LayoutParams params = NotChosenList.getLayoutParams();
                        params.height = 0;
                        NotChosenList.setLayoutParams(params);
                        NotChosenShow.setText(getString(R.string.notchosen_show));
                    } else {
                        ViewGroup.LayoutParams params = NotChosenList.getLayoutParams();
                        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                        NotChosenList.setLayoutParams(params);
                        NotChosenShow.setText(getString(R.string.notchosen_hide));

                    }
                }
            });
        }
        else{
            NotChosenShow.setText(getString(R.string.notchosen_size_zero));
        }
    }
}