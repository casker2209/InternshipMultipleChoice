package vn.edu.usth.internshipmultiplechoice;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.internshipmultiplechoice.adapter.CreateExamAdapter;
import vn.edu.usth.internshipmultiplechoice.object.Question;

public class CreateExamActivity extends AppCompatActivity {
    Button CreateQuestion;
    Button ExamCreateFinish;
    RecyclerView QuestionRecyclerView;
    List<Question> QuestionList;
    CreateExamAdapter QuestionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exam);
        init();
    }
    public void init(){
        QuestionList = new ArrayList<Question>();
        QuestionRecyclerView = findViewById(R.id.ExamQuestion);
        QuestionAdapter = new CreateExamAdapter(CreateExamActivity.this,QuestionList);
        QuestionRecyclerView.setAdapter(QuestionAdapter);
        QuestionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Question question = (Question) result.getData().getSerializableExtra("question");
                            QuestionList.add(question);
                            QuestionAdapter.notifyDataSetChanged();
                            }
                        else{
                            System.out.println("Fail");
                        }
                    }
                }
        );

        CreateQuestion = findViewById(R.id.ExamCreate);
        CreateQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityResult.launch(new Intent(CreateExamActivity.this,CreateQuestionActivity.class));
            };
        }
        );
        ExamCreateFinish = findViewById(R.id.ExamCreateFinish);
        ExamCreateFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}