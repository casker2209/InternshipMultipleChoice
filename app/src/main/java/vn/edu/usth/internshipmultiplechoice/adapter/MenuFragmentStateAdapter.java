package vn.edu.usth.internshipmultiplechoice.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import vn.edu.usth.internshipmultiplechoice.fragment.*;
public class MenuFragmentStateAdapter extends FragmentStateAdapter {


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
            return UserFragment.newInstance();
        }
        else{
            return ExamMenuFragment.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
