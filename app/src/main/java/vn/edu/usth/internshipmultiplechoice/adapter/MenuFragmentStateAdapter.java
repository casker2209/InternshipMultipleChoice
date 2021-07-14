package vn.edu.usth.internshipmultiplechoice.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import vn.edu.usth.internshipmultiplechoice.fragment.*;
import vn.edu.usth.internshipmultiplechoice.utility.UserSharedPreferences;

public class MenuFragmentStateAdapter extends FragmentStateAdapter {

    boolean isGuest;
    public MenuFragmentStateAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }
    public MenuFragmentStateAdapter(@NonNull FragmentActivity fragment) {
        super(fragment);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0){
            return ExamFragment.newInstance();
        }
        if(position == 1){
            return BankFragment.newInstance();
        }
        else{
            return UserFragment.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
