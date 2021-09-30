package vn.edu.usth.internshipmultiplechoice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import vn.edu.usth.internshipmultiplechoice.retrofit.User;
import vn.edu.usth.internshipmultiplechoice.utility.UserSharedPreferences;

public class MenuActivity extends AppCompatActivity {
    boolean isUser;
    User user;
    CardView ExamCard,BankCard,SignOutCard,ExamHistoryCard;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    public void init(){
        isUser = UserSharedPreferences.hasUser(MenuActivity.this);
        ExamCard = findViewById(R.id.ExamCard);
        BankCard = findViewById(R.id.BankCard);
        SignOutCard = findViewById(R.id.SignOutCard);
        ExamHistoryCard = findViewById(R.id.ExamHistoryCard);
            user = UserSharedPreferences.getUser(MenuActivity.this);
        SignOutCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserSharedPreferences.deleteUser(getBaseContext());
                    Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

        ExamCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,ExamListActivity.class);
                startActivity(intent);
            }
        });
        BankCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,BankListActivity.class);
                startActivity(intent);
            }
        });
            ExamHistoryCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isUser) {
                    Intent intent = new Intent(MenuActivity.this, ExamHistoryActivity.class);
                    intent.putExtra("user id", user.getId());
                    intent.putExtra("access token", user.getAccessToken());
                    startActivity(intent);
                    }
                    else{
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MenuActivity.this).setTitle("Không có thông tin")
                                .setMessage("Bạn không đăng nhập")
                                .setPositiveButton("OK",null);
                        AlertDialog alertDialog = dialog.show();
                    }
                }
            });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_temp);
        init();

    }
}