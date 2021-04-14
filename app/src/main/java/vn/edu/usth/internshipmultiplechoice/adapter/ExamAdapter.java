package vn.edu.usth.internshipmultiplechoice.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.internshipmultiplechoice.fragment.ExamQuestionFragment;
import vn.edu.usth.internshipmultiplechoice.object.Exam;

public class ExamAdapter extends FragmentStateAdapter {
    List<ExamQuestionFragment> fragmentList;
    Exam exam;

    public ExamAdapter(@NonNull FragmentActivity fragmentActivity,Exam exam) {
        super(fragmentActivity);
        this.exam = exam;
        this.fragmentList = new ArrayList<>();
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        ExamQuestionFragment fragment = new ExamQuestionFragment(exam.getQuestionList().get(position),position);
        fragmentList.add(fragment);
        return fragment;
    }

    public Fragment getFragment(int position){
        return fragmentList.get(position);
    }
    @Override
    public int getItemCount() {
        return exam.getQuestionList().size();
    }

}
