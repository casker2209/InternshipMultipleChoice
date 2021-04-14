package vn.edu.usth.internshipmultiplechoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.internshipmultiplechoice.adapter.ExamAdapter;
import vn.edu.usth.internshipmultiplechoice.adapter.ExamAdapterRecycler;
import vn.edu.usth.internshipmultiplechoice.fragment.ExamQuestionFragment;
import vn.edu.usth.internshipmultiplechoice.fragment.UserFragment;
import vn.edu.usth.internshipmultiplechoice.object.Exam;
import vn.edu.usth.internshipmultiplechoice.object.ExamHistory;
import vn.edu.usth.internshipmultiplechoice.object.Question;
import vn.edu.usth.internshipmultiplechoice.retrofit.RetrofitClient;

public class ExamActivity extends AppCompatActivity {
    ViewPager2 viewPager;
    Exam exam;
    ExamAdapter examAdapter;
    ExamAdapterRecycler adapterRecycler;
    TextView PageNum;
    TextView Timer;
    Button goBefore,goAfter,finishButton;
    String timer;
    CountDownTimer countDownTimer;
    public void getQuestion(){
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        String id = getIntent().getStringExtra("id");
        Log.d("id",id);
        Call<Exam> examCall = retrofitClient.getMyApi().getExam(id);
        examCall.enqueue(new Callback<Exam>() {
            @Override
            public void onResponse(Call<Exam> call, Response<Exam> response) {
               exam = response.body();
               System.out.println(response.body().getQuestionList().get(0).getName());
               System.out.println("success getting exam");
               init();
            }

            @Override
            public void onFailure(Call<Exam> call, Throwable t) {

            }
        });
    }

    public void getScore() {
        List<UserFragment> fragmentList = new ArrayList<>();
        List<Question> Correct = new ArrayList<>();
        List<Question> Wrong = new ArrayList<>();
        for (int i = 0; i < adapterRecycler.getItemCount(); i++) {
            Question question = adapterRecycler.getQuestion(i);
            String chosen = adapterRecycler.getCorrect(i);
            if (chosen.equals(question.getQuestionCorrect())) {
                Correct.add(question);
            } else Wrong.add(question);

        /*for(int i = 0;i< examAdapter.getItemCount();i++){
            ExamQuestionFragment fragment = (ExamQuestionFragment) getSupportFragmentManager().findFragmentByTag("f"+examAdapter.getItemId(i)+2);
            System.out.println(fragment instanceof ExamQuestionFragment);
            System.out.println(fragment != null);
            Question question = fragment.getQuestion();
            String correct = question.getQuestionCorrect();
            switch (correct){
                case "A":
                    RadioButton A = fragment.getButtonA();
                    if(A.isChecked()){
                        Correct.add(question);
                    }
                    else{
                        Wrong.add(question);
                    }
                case "B":
                    RadioButton B = fragment.getButtonA();
                    if(B.isChecked()){
                        Correct.add(question);
                    }
                    else{
                        Wrong.add(question);
                    }
                case "C":
                    RadioButton C = fragment.getButtonA();
                    if(C.isChecked()){
                        Correct.add(question);
                    }
                    else{
                        Wrong.add(question);
                    }
                case "D":
                    RadioButton D = fragment.getButtonA();
                    if(D.isChecked()){
                        Correct.add(question);
                    }
                    else{
                        Wrong.add(question);
                    }
            }

        }*/
            System.out.println("Score:" + Correct.size() + "/" + adapterRecycler.getItemCount());


        }
    }

    public void finish(){
        Intent intent = new Intent(getBaseContext(),ExamFinishActivity.class);
        getScore();
        startActivity(intent);

    }
    public void init(){
        viewPager = findViewById(R.id.viewpager);
        adapterRecycler = new ExamAdapterRecycler(this,this.exam);

        viewPager.setAdapter(adapterRecycler);
        PageNum = findViewById(R.id.QuestionPage);
        PageNum.setText("1"+"/"+String.valueOf(exam.getQuestionList().size()));
        Timer = findViewById(R.id.Timer);
        finishButton = findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        countDownTimer = new CountDownTimer(1000*60*30,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = (millisUntilFinished / 1000) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                Timer.setText(String.valueOf(minutes)+": "+String.valueOf(seconds));
            }

            @Override
            public void onFinish() {
                finish();
                }
        };
        goBefore = findViewById(R.id.goBefore);
        goBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager.getCurrentItem()!=0)
                viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
            }
        });
        goAfter = findViewById(R.id.goAfter);
        goAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager.getCurrentItem()!= exam.getQuestionList().size()-1){
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                }
            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                PageNum.setText(String.valueOf(position+1)+"/"+String.valueOf(exam.getQuestionList().size()));
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        countDownTimer.start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        getQuestion();
    }
}
