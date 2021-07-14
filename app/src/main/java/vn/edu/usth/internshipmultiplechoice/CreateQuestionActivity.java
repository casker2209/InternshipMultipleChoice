package vn.edu.usth.internshipmultiplechoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.internshipmultiplechoice.object.Exam;
import vn.edu.usth.internshipmultiplechoice.object.Question;

public class CreateQuestionActivity extends AppCompatActivity {
    EditText QuestionName,QuestionA,QuestionB,QuestionC,QuestionD;
    CheckBox ButtonA,ButtonB,ButtonC,ButtonD;
    Button Finish;
    List<String> answer;
    List<String> correct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);
        answer = new ArrayList<>();
        correct = new ArrayList<>();
        QuestionName = findViewById(R.id.QuestionName);
        QuestionA = findViewById(R.id.AnswerA);
        QuestionB = findViewById(R.id.AnswerB);
        QuestionC = findViewById(R.id.AnswerC);
        QuestionD = findViewById(R.id.AnswerD);
        ButtonA = findViewById(R.id.ButtonA);
        ButtonB = findViewById(R.id.ButtonB);
        ButtonC = findViewById(R.id.ButtonC);
        ButtonD = findViewById(R.id.ButtonD);
        Finish = findViewById(R.id.QuestionFinish);
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(CreateQuestionActivity.this,CreateExamActivity.class);
                answer.add(QuestionA.getText().toString());
                answer.add(QuestionB.getText().toString());
                answer.add(QuestionC.getText().toString());
                answer.add(QuestionD.getText().toString());
                if(ButtonA.isChecked()){
                    correct.add(QuestionA.getText().toString());
                }
                if(ButtonB.isChecked()){
                    correct.add(QuestionB.getText().toString());
                }
                if(ButtonC.isChecked()){
                    correct.add(QuestionC.getText().toString());
                }
                if(ButtonD.isChecked()){
                    correct.add(QuestionD.getText().toString());
                }
                data.putExtra("question",new Question(QuestionName.getText().toString(),answer,correct));
                setResult(RESULT_OK,data);
                finish();
            }
        });
    }
}