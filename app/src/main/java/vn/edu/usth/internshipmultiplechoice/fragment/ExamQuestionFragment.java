package vn.edu.usth.internshipmultiplechoice.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import vn.edu.usth.internshipmultiplechoice.R;
import vn.edu.usth.internshipmultiplechoice.object.Question;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExamQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExamQuestionFragment extends Fragment {
    private Question question;
    private TextView A,B,C,D,Name,Num;
    private RadioGroup buttonGroup;
    private RadioButton ButtonA,ButtonB,ButtonC,ButtonD;
    private int position;
    public ExamQuestionFragment() {
        // Required empty public constructor
    }

    public RadioButton getButtonA() {
        return ButtonA;
    }

    public RadioButton getButtonB() {
        return ButtonB;
    }

    public RadioButton getButtonC() {
        return ButtonC;
    }

    public RadioButton getButtonD() {
        return ButtonD;
    }

    public RadioGroup getButtonGroup() {
        return buttonGroup;
    }

    public Question getQuestion() {
        return question;
    }

    public ExamQuestionFragment(Question question, int position){
        this.question = question;
        this.position = position;
    }
    public static ExamQuestionFragment newInstance(Question question,int position) {
        ExamQuestionFragment fragment = new ExamQuestionFragment(question,position);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = initView(inflater, container);
        return view;
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_exam_question, container, false);
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
        A.setText("A: "+question.getAnswer().get(0));
        B.setText("B: "+question.getAnswer().get(1));
        C.setText("C: "+question.getAnswer().get(2));
        D.setText("D: "+question.getAnswer().get(3));
        Name.setText(question.getName());
        Num.setText("Question " +String.valueOf(position+1)+": ");
        return view;
    }
}