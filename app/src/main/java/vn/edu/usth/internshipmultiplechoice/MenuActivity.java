package vn.edu.usth.internshipmultiplechoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import vn.edu.usth.internshipmultiplechoice.retrofit.UserInfo;

public class MenuActivity extends AppCompatActivity {
    TextView MockExam,BankExam,NameView,EmailView,Logout,ExamHistoryInfo;
    UserInfo userInfo;



    public void init(){
        boolean isGuest = getIntent().getBooleanExtra("isGuest",true);
        ExamHistoryInfo = findViewById(R.id.ExamHistory);
        if(!isGuest){userInfo = (UserInfo) getIntent().getSerializableExtra("User Info");
        NameView = findViewById(R.id.nameUser);
        EmailView = findViewById(R.id.mailUser);
        NameView.setText("Tên: "+userInfo.getName());
        EmailView.setText("Mail: "+userInfo.getEmail());
        Logout = findViewById(R.id.LogOut);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this,LoginActivity.class));
                finish();
            }
        });
        }
        else{
            ExamHistoryInfo.setVisibility(View.GONE);
        }
        MockExam = findViewById(R.id.MockExam);
        MockExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this,ExamListActivity.class));
            }
        });
        BankExam = findViewById(R.id.MockExamBank);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        init();

    }
}