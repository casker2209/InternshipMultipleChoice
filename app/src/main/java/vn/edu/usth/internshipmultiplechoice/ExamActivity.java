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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.internshipmultiplechoice.adapter.ExamAdapter;
import vn.edu.usth.internshipmultiplechoice.fragment.ExamQuestionFragment;
import vn.edu.usth.internshipmultiplechoice.fragment.UserFragment;
import vn.edu.usth.internshipmultiplechoice.object.Exam;
import vn.edu.usth.internshipmultiplechoice.object.ExamHistory;
import vn.edu.usth.internshipmultiplechoice.object.Question;
import vn.edu.usth.internshipmultiplechoice.retrofit.RetrofitClient;
import vn.edu.usth.internshipmultiplechoice.retrofit.UserInfo;
import vn.edu.usth.internshipmultiplechoice.utility.UserSharedPreferences;

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
    List<List<String>> IncorrectChosen;
    List<List<String>> forChecked;

    @Override
    public void onBackPressed() {
        Context thisActivity = this;
        AlertDialog.Builder dialog = new AlertDialog.Builder(ExamActivity.this).setTitle("Thoát bài thi")
                .setMessage("Bạn muốn thoát khỏi bài thi không")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        countDownTimer.cancel();
                        countDownTimer = null;
                        startActivity(new Intent(thisActivity,MenuActivity.class));
                        finish();
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

    public boolean compareList(List<String> listA, List<String> listB){
        if(listA.containsAll(listB) && listB.containsAll(listA)) return true;
        return false;
    }
    public void update(){
        Correct = new ArrayList<>();
        Wrong = new ArrayList<>();
        IncorrectChosen = new ArrayList<>();
        NotChosen = new ArrayList<>();
        for (int i = 0; i < adapterRecycler.getItemCount(); i++) {
            Question question = adapterRecycler.getQuestion(i);
            List<String> chosen = adapterRecycler.getChosen(i);
            if (compareList(chosen,question.getQuestionCorrect())) {
                Correct.add(question);
            }
            else if (chosen.size()==0){
                NotChosen.add(question);
            }
            else{
                Wrong.add(question);
                System.out.println("Question name:"+question.getName());
                IncorrectChosen.add(chosen);
                System.out.println("Incorrect chosen:"+chosen.get(0));
            }
        }
    }
    public String getScore() {
        update();
        return(Correct.size() + "/" + adapterRecycler.getItemCount());
    }

    public void sendHistory(ExamHistory examHistory){
        Log.e("Sending exam","Sending exam history");
        RetrofitClient client = RetrofitClient.getInstance();
        UserInfo user = UserSharedPreferences.getUser(this);
        Call<ResponseBody> springResponse = client.getMyApi().sendExam(user.getId(),examHistory,user.getAccessToken());
        System.out.println(examHistory.getExam().getName());
        Context thisContext = this;
        springResponse.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println("Success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(thisContext,"Lưu kết quả bài thi không thành công",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void ExamFinish(){
        Intent intent = new Intent(getBaseContext(),ExamFinishActivity.class);
        ExamHistory examHistory = new ExamHistory(exam,getScore(),Correct,Wrong,IncorrectChosen,NotChosen);
        boolean isUser = UserSharedPreferences.hasUser(this);
        if(isUser){
        sendHistory(examHistory);
        }
        intent.putExtra("exam result",examHistory);
        startActivity(intent);
        finish();
    }
    public void init(){
        exam = (Exam) getIntent().getSerializableExtra("exam");
        forChecked = new ArrayList<>();
        for(int i = 0; i < exam.getQuestionList().size();i++){
            forChecked.add(new ArrayList<>());
        }
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
        init();
    }

    public class ExamAdapterRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        static final int Question4Answer = 1;
        static final int Question5Answer = 2;
        Exam exam;
        Context context;
        List<List<String>> chosen;
        int hasDone;

        public int getHasDone() {
            return hasDone;
        }

        public ExamAdapterRecycler(Context context, Exam exam){
            this.context = context;
            this.exam = exam;
            this.chosen = new ArrayList<>();
            for(int i = 0;i<exam.getQuestionList().size();i++){
                List<String> chosenList = new ArrayList<>();
                chosen.add(chosenList);
            }
            this.hasDone = 0;
        }

        @Override
        public int getItemViewType(int position) {
            if(getQuestion(position).getQuestionCorrect().size()==1) return Question4Answer;
            else return Question5Answer;

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if(viewType == Question4Answer)
            {Context context = parent.getContext();
                LayoutInflater inflater = LayoutInflater.from(context);
                View ExamView = inflater.inflate(R.layout.item_exam, parent, false);
                ViewHolderOne viewHolder = new ExamAdapterRecycler.ViewHolderOne(ExamView);
                return viewHolder;}
            else{
                LayoutInflater inflater = LayoutInflater.from(context);
                View ExamView = inflater.inflate(R.layout.item_exam_two, parent, false);
                ViewHolderTwo viewHolder = new ExamAdapterRecycler.ViewHolderTwo(ExamView);
                return viewHolder;
            }
        }

        public Question getQuestion(int position){
            return exam.getQuestionList().get(position);
        }
        public List<String> getChosen(int position){
            return chosen.get(position);
        }
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position){
            if(getItemViewType(position)==Question4Answer) {
                Question question = exam.getQuestionList().get(position);
                ((ViewHolderOne) holder).A.setText(question.getAnswer().get(0));
                ((ViewHolderOne) holder).B.setText(question.getAnswer().get(1));
                ((ViewHolderOne) holder).C.setText(question.getAnswer().get(2));
                ((ViewHolderOne) holder).D.setText(question.getAnswer().get(3));
                ((ViewHolderOne) holder).Name.setText(question.getName());
                ((ViewHolderOne) holder).Num.setText("Question " + String.valueOf(position + 1));
                if (forChecked.get(position).contains("A")) {
                    ((ViewHolderOne) holder).ButtonA.setChecked(true);
                }
                if (forChecked.get(position).contains("B")) {
                    ((ViewHolderOne) holder).ButtonB.setChecked(true);
                }
                if (forChecked.get(position).contains("C")) {
                    ((ViewHolderOne) holder).ButtonC.setChecked(true);
                }
                if (forChecked.get(position).contains("D")) {
                    ((ViewHolderOne) holder).ButtonD.setChecked(true);
                }
                ViewHolderOne finalHolder = (ViewHolderOne) holder;
                ((ViewHolderOne) holder).buttonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.ButtonA:
                                if (chosen.get(position).size() == 0) {
                                    hasDone++;
                                    notifyDataSetChanged();
                                    HasDone.setText("Đã làm: " + hasDone + "/" + exam.getQuestionList().size());
                                }
                                List<String> templist = new ArrayList<>();
                                templist.add(finalHolder.A.getText().toString());
                                List<String> templist2 = new ArrayList<>();
                                templist2.add("A");
                                chosen.set(position, templist);
                                forChecked.set(position, templist2);
                                break;
                            case R.id.ButtonB:
                                if (chosen.get(position).size() == 0) {
                                    hasDone++;
                                    notifyDataSetChanged();
                                    HasDone.setText("Đã làm: " + hasDone + "/" + exam.getQuestionList().size());
                                }
                                templist = new ArrayList<>();
                                templist.add(finalHolder.B.getText().toString());
                                templist2 = new ArrayList<>();
                                templist2.add("B");
                                chosen.set(position, templist);
                                forChecked.set(position, templist2);
                                break;
                            case R.id.ButtonC:
                                if (chosen.get(position).size() == 0) {
                                    hasDone++;
                                    notifyDataSetChanged();
                                    HasDone.setText("Đã làm: " + hasDone + "/" + exam.getQuestionList().size());
                                }
                                templist = new ArrayList<>();
                                templist.add(finalHolder.C.getText().toString());
                                templist2 = new ArrayList<>();
                                templist2.add("C");
                                chosen.set(position, templist);
                                forChecked.set(position, templist2);
                                break;
                            case R.id.ButtonD:
                                if (chosen.get(position).size() == 0) {
                                    hasDone++;
                                    notifyDataSetChanged();
                                    HasDone.setText("Đã làm: " + hasDone + "/" + exam.getQuestionList().size());
                                }
                                templist = new ArrayList<>();
                                templist.add(finalHolder.D.getText().toString());
                                templist2 = new ArrayList<>();
                                templist2.add("D");
                                chosen.set(position, templist);
                                forChecked.set(position, templist2);
                                break;
                            default: {
                                break;
                            }
                        }
                        shortcutRecyclerView.getChildAt(position).findViewById(R.id.page).setBackgroundColor(getResources().getColor(R.color.green));
                    }
                });
            }
            else{
                Question question = exam.getQuestionList().get(position);
                ((ViewHolderTwo) holder).A.setText(question.getAnswer().get(0));
                ((ViewHolderTwo) holder).B.setText(question.getAnswer().get(1));
                ((ViewHolderTwo) holder).C.setText(question.getAnswer().get(2));
                ((ViewHolderTwo) holder).D.setText(question.getAnswer().get(3));
                ((ViewHolderTwo) holder).Name.setText(question.getName());
                ((ViewHolderTwo) holder).Num.setText("Question " + String.valueOf(position + 1));
                if (forChecked.get(position).contains("A")) {
                    ((ViewHolderTwo) holder).ButtonA.setChecked(true);
                }
                if (forChecked.get(position).contains("B")) {
                    ((ViewHolderTwo) holder).ButtonB.setChecked(true);
                }
                if (forChecked.get(position).contains("C")) {
                    ((ViewHolderTwo) holder).ButtonC.setChecked(true);
                }
                if (forChecked.get(position).contains("D")) {
                    ((ViewHolderTwo) holder).ButtonD.setChecked(true);
                }
                ((ViewHolderTwo) holder).ButtonA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            if(chosen.get(position).size()==0){
                                hasDone++;
                                notifyDataSetChanged();
                                HasDone.setText("Đã làm: " + hasDone + "/" + exam.getQuestionList().size());
                                shortcutRecyclerView.getChildAt(position).findViewById(R.id.page).setBackgroundColor(getResources().getColor(R.color.green));

                            }
                            chosen.get(position).add(((ViewHolderTwo) holder).A.getText().toString());
                            forChecked.get(position).add("A");
                        }
                        else{
                            chosen.get(position).remove(((ViewHolderTwo) holder).A.getText().toString());
                            forChecked.get(position).remove("A");
                            if(chosen.get(position).size()==0){
                                hasDone--;
                                notifyDataSetChanged();
                                HasDone.setText("Đã làm: " + hasDone + "/" + exam.getQuestionList().size());
                                shortcutRecyclerView.getChildAt(position).findViewById(R.id.page).setBackgroundColor(getResources().getColor(R.color.yellow));

                            }
                        }
                    }
                });
                ((ViewHolderTwo) holder).ButtonB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            if(chosen.get(position).size()==0){
                                hasDone++;
                                notifyDataSetChanged();
                                HasDone.setText("Đã làm: " + hasDone + "/" + exam.getQuestionList().size());
                                shortcutRecyclerView.getChildAt(position).findViewById(R.id.page).setBackgroundColor(getResources().getColor(R.color.green));

                            }
                            chosen.get(position).add(((ViewHolderTwo) holder).B.getText().toString());
                            forChecked.get(position).add("B");

                        }
                        else{
                            chosen.get(position).remove(((ViewHolderTwo) holder).B.getText().toString());
                            forChecked.get(position).remove("B");
                            if(chosen.get(position).size()==0){
                                hasDone--;
                                notifyDataSetChanged();
                                HasDone.setText("Đã làm: " + hasDone + "/" + exam.getQuestionList().size());
                                shortcutRecyclerView.getChildAt(position).findViewById(R.id.page).setBackgroundColor(getResources().getColor(R.color.yellow));

                            }
                        }
                    }
                });
                ((ViewHolderTwo) holder).ButtonC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            if(chosen.get(position).size()==0){
                                hasDone++;
                                notifyDataSetChanged();
                                HasDone.setText("Đã làm: " + hasDone + "/" + exam.getQuestionList().size());
                                shortcutRecyclerView.getChildAt(position).findViewById(R.id.page).setBackgroundColor(getResources().getColor(R.color.green));

                            }
                            chosen.get(position).add(((ViewHolderTwo) holder).C.getText().toString());
                            forChecked.get(position).add("C");
                        }
                        else{
                            chosen.get(position).remove(((ViewHolderTwo) holder).C.getText().toString());
                            forChecked.get(position).remove("C");
                            if(chosen.get(position).size()==0){
                                hasDone--;
                                notifyDataSetChanged();
                                HasDone.setText("Đã làm: " + hasDone + "/" + exam.getQuestionList().size());
                                shortcutRecyclerView.getChildAt(position).findViewById(R.id.page).setBackgroundColor(getResources().getColor(R.color.yellow));

                            }
                        }
                    }
                });
                ((ViewHolderTwo) holder).ButtonD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            if(chosen.get(position).size()==0){
                                hasDone++;
                                notifyDataSetChanged();
                                HasDone.setText("Đã làm: " + hasDone + "/" + exam.getQuestionList().size());
                                shortcutRecyclerView.getChildAt(position).findViewById(R.id.page).setBackgroundColor(getResources().getColor(R.color.green));

                            }
                            chosen.get(position).add(((ViewHolderTwo) holder).D.getText().toString());
                            forChecked.get(position).add("D");
                        }
                        else{
                            chosen.get(position).remove(((ViewHolderTwo) holder).D.getText().toString());
                            forChecked.get(position).remove("D");
                            if(chosen.get(position).size()==0){
                                hasDone--;
                                notifyDataSetChanged();
                                HasDone.setText("Đã làm: " + hasDone + "/" + exam.getQuestionList().size());
                                shortcutRecyclerView.getChildAt(position).findViewById(R.id.page).setBackgroundColor(getResources().getColor(R.color.yellow));

                            }
                        }
                    }
                });
            }
        }




        @Override
        public int getItemCount() {
            return exam.getQuestionList().size();
        }

        public class ViewHolderTwo extends RecyclerView.ViewHolder{
            TextView A,B,C,D,Name,Num;
            CheckBox ButtonA,ButtonB,ButtonC,ButtonD;
            String correct;
            public ViewHolderTwo(@NonNull View view) {
                super(view);
                this.setIsRecyclable(false);
                A = view.findViewById(R.id.TextA);
                B = view.findViewById(R.id.TextB);
                C = view.findViewById(R.id.TextC);
                D = view.findViewById(R.id.TextD);
                ButtonA = view.findViewById(R.id.ButtonA);
                ButtonB =   view.findViewById(R.id.ButtonB);
                ButtonC =  view.findViewById(R.id.ButtonC);
                ButtonD = view.findViewById(R.id.ButtonD);
                Name = view.findViewById(R.id.QuestionName);
                Num = view.findViewById(R.id.QuestionNum);

            }
        }

        public class ViewHolderOne extends RecyclerView.ViewHolder {
            TextView A,B,C,D,Name,Num;
            RadioButton ButtonA,ButtonB,ButtonC,ButtonD;
            RadioGroup buttonGroup;
            String correct;
            public ViewHolderOne(@NonNull View view) {
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
