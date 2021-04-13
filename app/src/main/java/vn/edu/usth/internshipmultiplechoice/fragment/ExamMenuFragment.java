package vn.edu.usth.internshipmultiplechoice.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.internshipmultiplechoice.ExamActivity;
import vn.edu.usth.internshipmultiplechoice.ExamListActivity;
import vn.edu.usth.internshipmultiplechoice.R;
import vn.edu.usth.internshipmultiplechoice.retrofit.RetrofitClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExamMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExamMenuFragment extends Fragment {

    TextView ExamCardView;
    TextView BankCardView;
    public ExamMenuFragment() {
    }
    public static ExamMenuFragment newInstance() {
        ExamMenuFragment fragment = new ExamMenuFragment();

        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam_menu, container, false);
        ExamCardView = view.findViewById(R.id.Exam);
        ExamCardView.setOnClickListener(new CardView.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ExamListActivity.class));
            }
        });
        BankCardView = view.findViewById(R.id.Bank);
        return view;
    }
}