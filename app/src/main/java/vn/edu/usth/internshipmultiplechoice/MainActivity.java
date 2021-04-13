package vn.edu.usth.internshipmultiplechoice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

import vn.edu.usth.internshipmultiplechoice.adapter.MenuFragmentStateAdapter;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    ViewPager2 viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager2) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MenuFragmentStateAdapter(this));
    }
}