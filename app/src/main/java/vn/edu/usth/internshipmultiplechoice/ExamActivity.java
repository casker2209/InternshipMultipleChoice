package vn.edu.usth.internshipmultiplechoice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.sql.Time;
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
    RecyclerView shortcutRecyclerView;
    TextView PageNum;
    TextView Timer,HasDone;
    Button goBefore,goAfter,finishButton;
    String timer,hasDone;
    List<Question> Correct,Wrong,NotChosen;
    CountDownTimer countDownTimer;
    List<String> IncorrectChosen;
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
    public void update(){
        Correct = new ArrayList<>();
        Wrong = new ArrayList<>();
        IncorrectChosen = new ArrayList<>();
        NotChosen = new ArrayList<>();
        for (int i = 0; i < adapterRecycler.getItemCount(); i++) {
            Question question = adapterRecycler.getQuestion(i);
            String chosen = adapterRecycler.getChosen(i);
            if (chosen.equals(question.getQuestionCorrect())) {
                Correct.add(question);
            }
            else if (chosen.equals("Not chosen")){
                NotChosen.add(question);
            }
            else{
                Wrong.add(question);
                IncorrectChosen.add(chosen);

            }
        }
    }
    public String getScore() {
        update();
        return(Correct.size() + "/" + adapterRecycler.getItemCount());
    }

    public void ExamFinish(){
        Intent intent = new Intent(getBaseContext(),ExamFinishActivity.class);
        ExamHistory examHistory = new ExamHistory(exam,getScore(),Correct,Wrong,IncorrectChosen,NotChosen);
        intent.putExtra("exam result",examHistory);
        startActivity(intent);
        finish();
    }
    public void init(){
        viewPager = findViewById(R.id.viewpager);
        adapterRecycler = new ExamAdapterRecycler(this,this.exam);
        HasDone = findViewById(R.id.hasDone);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((LinearLayout) findViewById(R.id.shortcut_layout));
        bottomSheetBehavior.setDraggable(true);
        viewPager.setAdapter(adapterRecycler);
        PageNum = findViewById(R.id.QuestionPage);
        PageNum.setText("1"+"/"+String.valueOf(exam.getQuestionList().size()));
        Timer = findViewById(R.id.Timer);
        finishButton = findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ExamActivity.this).setTitle("Bạn có muốn kết thúc bài thi không")
                        .setMessage("Vẫn còn thời gian làm bài,bạn có muốn nộp bài sớm không")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                countDownTimer.cancel();
                                countDownTimer = null;
                                ExamFinish();
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = dialog.show();
            }
        });
        int durationinMilis = 1000*30;
        countDownTimer = new CountDownTimer(durationinMilis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = (millisUntilFinished / 1000) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                String time;
                if(seconds < 10){
                     time = (minutes +":"+"0"+ seconds);
                }
                else{
                     time = (minutes +":"+ seconds);
                }
                Timer.setText(time);
                if(millisUntilFinished < (long) Math.ceil(durationinMilis *1/3)) Timer.setTextColor(Color.RED);
            }

            @Override
            public void onFinish() {
                ExamFinish();
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
                viewPager.setCurrentItem(position);
                PageNum.setText(String.valueOf(position+1)+"/"+String.valueOf(exam.getQuestionList().size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        countDownTimer.start();

        shortcutRecyclerView = findViewById(R.id.shortcut);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(ExamActivity.this);

        shortcutRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        shortcutRecyclerView.setLayoutManager(layoutManager);
        shortcutRecyclerView.setAdapter(new ShortcutAdapter(ExamActivity.this,exam.getQuestionList().size()));


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        getQuestion();
    }

    public class ExamAdapterRecycler extends RecyclerView.Adapter<ExamAdapterRecycler.ViewHolder> {
        static final int Question4Answer = 1;
        static final int Question5Answer = 2;
        Exam exam;
        Context context;
        List<String> chosen;
        int hasDone;

        public int getHasDone() {
            return hasDone;
        }

        public ExamAdapterRecycler(Context context, Exam exam){
            this.context = context;
            this.exam = exam;
            this.chosen = new ArrayList<>();
            for(int i = 0;i<exam.getQuestionList().size();i++){
                chosen.add("Not chosen");
            }
            this.hasDone = 0;
        }

        @Override
        public int getItemViewType(int position) {
            if(exam.getQuestionList().get(position).isHasE()){
                return Question4Answer;
            }
            else return Question5Answer;

        }

        @NonNull
        @Override
        public ExamAdapterRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if(viewType == 1)
            {Context context = parent.getContext();
                LayoutInflater inflater = LayoutInflater.from(context);
                View ExamView = inflater.inflate(R.layout.item_exam, parent, false);
                ExamAdapterRecycler.ViewHolder viewHolder = new ExamAdapterRecycler.ViewHolder(ExamView);
                return viewHolder;}
            else{
                LayoutInflater inflater = LayoutInflater.from(context);
                View ExamView = inflater.inflate(R.layout.item_exam, parent, false);
                ExamAdapterRecycler.ViewHolder viewHolder = new ExamAdapterRecycler.ViewHolder(ExamView);
                return viewHolder;
            }
        }

        public Question getQuestion(int position){
            return exam.getQuestionList().get(position);
        }
        public String getChosen(int position){
            return chosen.get(position);
        }
        @Override
        public void onBindViewHolder(@NonNull ExamAdapterRecycler.ViewHolder holder, int position) {
            Question question = exam.getQuestionList().get(position);
            holder.A.setText("A: "+question.getA());
            holder.B.setText("B: "+question.getB());
            holder.C.setText("C: "+question.getC());
            holder.D.setText("D: "+question.getD());
            holder.Name.setText(question.getName());
            holder.Num.setText("Question "+String.valueOf(position+1));
            switch(chosen.get(position)){
                case "A":
                    holder.ButtonA.setChecked(true);
                    break;
                case "B":
                    holder.ButtonB.setChecked(true);
                    break;
                case "C":
                    holder.ButtonC.setChecked(true);
                    break;
                case "D":
                    holder.ButtonD.setChecked(true);
                    break;
                default:
                    break;
            }
            holder.buttonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch(checkedId){
                        case R.id.ButtonA:
                            if(chosen.get(position).equals("Not chosen")){
                                hasDone++;
                                notifyDataSetChanged();
                                HasDone.setText("Đã làm: "+ hasDone +"/"+ exam.getQuestionList().size());

                            }
                            chosen.add(position,"A");
                            break;
                        case R.id.ButtonB:
                            if(chosen.get(position).equals("Not chosen"))
                            {
                                hasDone++;
                                notifyDataSetChanged();
                                HasDone.setText("Đã làm: "+ hasDone +"/"+ exam.getQuestionList().size());
                            }
                            chosen.add(position,"B");
                            break;
                        case R.id.ButtonC:
                            if(chosen.get(position).equals("Not chosen")) {
                                hasDone++;
                                notifyDataSetChanged();
                                HasDone.setText("Đã làm: "+ hasDone +"/"+ exam.getQuestionList().size());
                            }
                            chosen.add(position,"C");
                            break;
                        case R.id.ButtonD:
                            if(chosen.get(position).equals("Not chosen")) {
                                hasDone++;
                                notifyDataSetChanged();
                                HasDone.setText("Đã làm: "+ hasDone +"/"+ exam.getQuestionList().size());
                            }
                            chosen.add(position,"D");
                            break;
                        default:{
                            break;
                        }
                    }
                    shortcutRecyclerView.getChildAt(position).findViewById(R.id.page).setBackgroundColor(getResources().getColor(R.color.green));
                }
            });
        }



        @Override
        public int getItemCount() {
            return exam.getQuestionList().size();
        }

        public class ViewHolderWithE extends RecyclerView.ViewHolder{
            TextView A,B,C,D,Name,Num;
            RadioButton ButtonA,ButtonB,ButtonC,ButtonD;
            RadioGroup buttonGroup;
            String correct;
            public ViewHolderWithE(@NonNull View view) {
                super(view);
                this.setIsRecyclable(false);
                A = view.findViewById(R.id.TextA);
                B = view.findViewById(R.id.TextB);
                C = view.findViewById(R.id.TextC);
                D = view.findViewById(R.id.TextD);
                buttonGroup = view.findViewById(R.id.buttonGroup);
                ButtonA = (RadioButton) view.findViewById(R.id.ButtonA);
                ButtonB = (RadioButton)  view.findViewById(R.id.ButtonB);
                ButtonC = (RadioButton) view.findViewById(R.id.ButtonC);
                ButtonD = (RadioButton) view.findViewById(R.id.ButtonD);
                Name = view.findViewById(R.id.QuestionName);
                Num = view.findViewById(R.id.QuestionNum);

            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView A,B,C,D,Name,Num;
            RadioButton ButtonA,ButtonB,ButtonC,ButtonD;
            RadioGroup buttonGroup;
            String correct;
            public ViewHolder(@NonNull View view) {
                super(view);
                this.setIsRecyclable(false);
                A = view.findViewById(R.id.TextA);
                B = view.findViewById(R.id.TextB);
                C = view.findViewById(R.id.TextC);
                D = view.findViewById(R.id.TextD);
                buttonGroup = view.findViewById(R.id.buttonGroup);
                ButtonA = (RadioButton) view.findViewById(R.id.ButtonA);
                ButtonB = (RadioButton)  view.findViewById(R.id.ButtonB);
                ButtonC = (RadioButton) view.findViewById(R.id.ButtonC);
                ButtonD = (RadioButton) view.findViewById(R.id.ButtonD);
                Name = view.findViewById(R.id.QuestionName);
                Num = view.findViewById(R.id.QuestionNum);

            }
        }
    }
    public class ShortcutAdapter extends RecyclerView.Adapter<ShortcutAdapter.ViewHolder> {
        int NumofPage;
        Context context;
        public ShortcutAdapter(Context context,int size){
            this.context = context;
            this.NumofPage = size;
        }

        @NonNull
        @Override
        public ShortcutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.item_shortcut,parent,false);
            ShortcutAdapter.ViewHolder viewHolder = new ShortcutAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ShortcutAdapter.ViewHolder holder, int position) {
            holder.Page.setText(String.valueOf(position+1));
            holder.Page.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return NumofPage;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView Page;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                Page = itemView.findViewById(R.id.page);
            }
        }
    }



}
