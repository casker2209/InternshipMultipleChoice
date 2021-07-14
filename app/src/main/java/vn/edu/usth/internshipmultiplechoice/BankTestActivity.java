package vn.edu.usth.internshipmultiplechoice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import vn.edu.usth.internshipmultiplechoice.object.*;

public class BankTestActivity extends AppCompatActivity {
    Bank bank;
    BankAdapter bankAdapter;
    ViewPager2 viewPager;
    View currentView;
    Button Switch,Exit,Complete;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_test);
        init();
    }

    public int randomNum(int size,int original){
        int random = (int) Math.floor(Math.random()*(size-1));
        while(random == original){
            random = (int) Math.floor(Math.random()*(size-1));
        }
        return random;
    }
    public void switchQuestion(){
        int size = bankAdapter.getItemCount();
        int current = viewPager.getCurrentItem();
        int random = randomNum(size,current);
        viewPager.setCurrentItem(random);
    }
    public void colorAnswer(int type,Question question){
        currentView = viewPager.getFocusedChild();
        System.out.println(viewPager.getCurrentItem());
        System.out.println(bank.getQuestionList().get(viewPager.getCurrentItem()).getName());
        System.out.println(currentView == null);
        LinearLayout LineA,LineB,LineC,LineD;
        List<LinearLayout> AnswerLineList;
        TextView AnswerA,AnswerB,AnswerC,AnswerD;
        List<TextView> AnswerTextList;

        AnswerA = currentView.findViewById(R.id.TextA);
        AnswerB = currentView.findViewById(R.id.TextB);
        AnswerC = currentView.findViewById(R.id.TextC);
        AnswerD = currentView.findViewById(R.id.TextD);
        AnswerTextList = new ArrayList<>();
        AnswerTextList.add(AnswerA);
        AnswerTextList.add(AnswerB);
        AnswerTextList.add(AnswerC);
        AnswerTextList.add(AnswerD);

        LineA = currentView.findViewById(R.id.LineA);
        LineB = currentView.findViewById(R.id.LineB);
        LineC = currentView.findViewById(R.id.LineC);
        LineD = currentView.findViewById(R.id.LineD);
        AnswerLineList = new ArrayList<>();
        AnswerLineList.add(LineA);
        AnswerLineList.add(LineB);
        AnswerLineList.add(LineC);
        AnswerLineList.add(LineD);

        if(type == 1){
            RadioButton ButtonA = (RadioButton) currentView.findViewById(R.id.ButtonA);
            RadioButton ButtonB = (RadioButton)  currentView.findViewById(R.id.ButtonB);
            RadioButton ButtonC = (RadioButton) currentView.findViewById(R.id.ButtonC);
            RadioButton ButtonD = (RadioButton) currentView.findViewById(R.id.ButtonD);
            List<RadioButton> ButtonList = new ArrayList<>();
            ButtonList.add(ButtonA);
            ButtonList.add(ButtonB);
            ButtonList.add(ButtonC);
            ButtonList.add(ButtonD);

            for(int i = 0;i<4;i++){
                TextView answerView = AnswerTextList.get(i);
                LinearLayout answerLine = AnswerLineList.get(i);
                List<String> correctAnswerList = question.getQuestionCorrect();
                if(correctAnswerList.contains(answerView.getText())) answerLine.setBackgroundColor(Color.GREEN);
                if(ButtonList.get(i).isChecked() && (!correctAnswerList.contains(answerView.getText()))){
                    answerLine.setBackgroundColor(Color.RED);
                }
            }

        }
        else{
            CheckBox ButtonA = currentView.findViewById(R.id.ButtonA);
            CheckBox ButtonB =   currentView.findViewById(R.id.ButtonB);
            CheckBox ButtonC =  currentView.findViewById(R.id.ButtonC);
            CheckBox ButtonD = currentView.findViewById(R.id.ButtonD);
            List<CheckBox> ButtonList = new ArrayList<>();
            ButtonList.add(ButtonA);
            ButtonList.add(ButtonB);
            ButtonList.add(ButtonC);
            ButtonList.add(ButtonD);
            for(int i = 0;i<4;i++){
                TextView answerView = AnswerTextList.get(i);
                LinearLayout answerLine = AnswerLineList.get(i);
                List<String> correctAnswerList = question.getQuestionCorrect();
                if(correctAnswerList.contains(answerView.getText())) answerLine.setBackgroundColor(Color.GREEN);
                if(ButtonList.get(i).isChecked() && (!correctAnswerList.contains(answerView.getText()))){
                    answerLine.setBackgroundColor(Color.RED);
                }
            }
        }
    }

    public void init(){
        bank = (Bank) getIntent().getSerializableExtra("bank");
        viewPager = findViewById(R.id.viewpager);
        bankAdapter = new BankAdapter(this,bank);
        viewPager.setAdapter(bankAdapter);
        currentView = viewPager.getChildAt(viewPager.getCurrentItem());
        Exit = findViewById(R.id.exitButton);
        Switch = findViewById(R.id.switchButton);
        Complete = findViewById(R.id.completeButton);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankTestActivity.this,BankListActivity.class);
                startActivity(intent);
                finish();

            }
        });
        Switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchQuestion();
            }
        });
        Complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewPager.getCurrentItem();
                Question question = bankAdapter.getQuestion(position);
                int questionType = bankAdapter.getItemViewType(position);
                if(questionType == 1){
                    colorAnswer(questionType,question);
                    new CountDownTimer(1000,1000){
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }
                        @Override
                        public void onFinish() {
                            switchQuestion();

                        }
                    }.start();

                  }
                else{
                    colorAnswer(questionType,question);
                    new CountDownTimer(2000,1000){

                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            switchQuestion();

                        }
                    }.start();

                }
            }
        });

    }


    public class BankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        static final int Question4Answer = 1;
        static final int Question5Answer = 2;
        Bank bank;
        Context context;

        public BankAdapter(Context context, Bank bank){
            this.context = context;
            this.bank = bank;

        }
        public Question getQuestion(int position){
            return bank.getQuestionList().get(position);
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
                View View = inflater.inflate(R.layout.item_bank, parent, false);
                BankAdapter.ViewHolderOne viewHolder = new BankAdapter.ViewHolderOne(View);
                return viewHolder;}
            else{
                LayoutInflater inflater = LayoutInflater.from(context);
                View View = inflater.inflate(R.layout.item_bank_two, parent, false);
                BankAdapter.ViewHolderTwo viewHolder = new BankAdapter.ViewHolderTwo(View);
                return viewHolder;
            }
        }


        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if(getItemViewType(position)==Question4Answer) {
                Question question = bank.getQuestionList().get(position);
                List<String> answer = question.getAnswer();
                for(int i = 0; i < 4;i++){
                    TextView answerView = ((BankAdapter.ViewHolderOne) holder).AnswerList.get(i);
                    answerView.setText(answer.get(i));
                }
                ((BankAdapter.ViewHolderOne) holder).Name.setText(question.getName());
                /*
                ((BankAdapter.ViewHolderOne) holder).Complete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i = 0;i < 4;i++){
                            String correct = question.getQuestionCorrect().get(0);
                            TextView answerView = ((BankAdapter.ViewHolderOne) holder).AnswerList.get(i);
                            if(answerView.getText().toString().equals(correct)){
                                answerView.setBackgroundColor(Color.GREEN);
                            }
                        }
                    }
                });
            */
            }
            else{
                Question question = bank.getQuestionList().get(position);
                List<String> answer = question.getAnswer();
                for(int i = 0; i < 4;i++){
                    TextView answerView = ((BankAdapter.ViewHolderTwo) holder).AnswerList.get(i);
                    answerView.setText(answer.get(i));
                }
                ((BankAdapter.ViewHolderTwo) holder).Name.setText(question.getName());
                /*
                ((BankAdapter.ViewHolderTwo) holder).Complete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i = 0;i < 4;i++){
                            TextView answerView = ((BankAdapter.ViewHolderTwo) holder).AnswerList.get(i);
                            LinearLayout answerLine = ((BankAdapter.ViewHolderTwo) holder).LineList.get(i);
                            for (String correct : question.getQuestionCorrect()){
                                if(answerView.getText().toString().equals(correct)){
                                    answerLine.setBackgroundColor(Color.GREEN);
                                    break;
                                }
                            }

                        }
                    }
                });
            */
            }

        }

        @Override
        public int getItemCount() {
            return bank.getQuestionList().size();
        }

        public class ViewHolderTwo extends RecyclerView.ViewHolder{
            TextView A,B,C,D,Name;
            LinearLayout LineA,LineB,LineC,LineD;
            CheckBox ButtonA,ButtonB,ButtonC,ButtonD;
            List<TextView> AnswerList;
            List<LinearLayout> LineList;
            List<CheckBox> ButtonList;
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
                ButtonList = new ArrayList<>();
                ButtonList.add(ButtonA);
                ButtonList.add(ButtonB);
                ButtonList.add(ButtonC);
                ButtonList.add(ButtonD);

                Name = view.findViewById(R.id.QuestionName);

                AnswerList = new ArrayList<>();
                AnswerList.add(A);
                AnswerList.add(B);
                AnswerList.add(C);
                AnswerList.add(D);

                LineA = view.findViewById(R.id.LineA);
                LineB = view.findViewById(R.id.LineB);
                LineC = view.findViewById(R.id.LineC);
                LineD = view.findViewById(R.id.LineD);
                LineList = new ArrayList<>();
                LineList.add(LineA);
                LineList.add(LineB);
                LineList.add(LineC);
                LineList.add(LineD);
            }

            public void colorAnswer(List<String> correctAnswerList){
                for(int i = 0;i<4;i++){
                    TextView answerView = AnswerList.get(i);
                    LinearLayout answerLine = LineList.get(i);
                    if(correctAnswerList.contains(answerView.getText())) answerLine.setBackgroundColor(Color.GREEN);
                    if(ButtonList.get(i).isChecked() && (!correctAnswerList.contains(answerView.getText()))){
                        answerLine.setBackgroundColor(Color.RED);
                    }
                }
            }
        }

        public class ViewHolderOne extends RecyclerView.ViewHolder {
            TextView A,B,C,D,Name;
            LinearLayout LineA,LineB,LineC,LineD;
            RadioButton ButtonA,ButtonB,ButtonC,ButtonD;
            RadioGroup buttonGroup;
            List<TextView> AnswerList;
            List<LinearLayout> LineList;
            List<RadioButton> ButtonList;
            public ViewHolderOne(@NonNull View view) {
                super(view);
                this.setIsRecyclable(false);
                A = view.findViewById(R.id.TextA);
                B = view.findViewById(R.id.TextB);
                C = view.findViewById(R.id.TextC);
                D = view.findViewById(R.id.TextD);

                ButtonA = (RadioButton) view.findViewById(R.id.ButtonA);
                ButtonB = (RadioButton)  view.findViewById(R.id.ButtonB);
                ButtonC = (RadioButton) view.findViewById(R.id.ButtonC);
                ButtonD = (RadioButton) view.findViewById(R.id.ButtonD);
                ButtonList = new ArrayList<>();
                ButtonList.add(ButtonA);
                ButtonList.add(ButtonB);
                ButtonList.add(ButtonC);
                ButtonList.add(ButtonD);
                Name = view.findViewById(R.id.QuestionName);
                AnswerList = new ArrayList<>();
                AnswerList.add(A);
                AnswerList.add(B);
                AnswerList.add(C);
                AnswerList.add(D);

                LineA = view.findViewById(R.id.LineA);
                LineB = view.findViewById(R.id.LineB);
                LineC = view.findViewById(R.id.LineC);
                LineD = view.findViewById(R.id.LineD);
                LineList = new ArrayList<>();
                LineList.add(LineA);
                LineList.add(LineB);
                LineList.add(LineC);
                LineList.add(LineD);
            }


            public void colorAnswer(List<String> correctAnswerList){
                for(int i = 0;i<4;i++){
                    TextView answerView = AnswerList.get(i);
                    LinearLayout answerLine = LineList.get(i);
                    if(correctAnswerList.contains(answerView.getText())) answerLine.setBackgroundColor(Color.GREEN);
                    if(ButtonList.get(i).isChecked() && (!correctAnswerList.contains(answerView.getText()))){
                        answerLine.setBackgroundColor(Color.RED);
                    }
                }
            }

        }
    }
}