package vn.edu.usth.internshipmultiplechoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import vn.edu.usth.internshipmultiplechoice.adapter.MenuFragmentStateAdapter;

public class MainMenuActivity extends AppCompatActivity {
    ViewPager2 viewpager;
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        init();
    }
    public void init(){
        viewpager = findViewById(R.id.viewpager);
        FragmentStateAdapter fragmentAdapter = new MenuFragmentStateAdapter(this);
        viewpager.setAdapter(fragmentAdapter);
    }
}