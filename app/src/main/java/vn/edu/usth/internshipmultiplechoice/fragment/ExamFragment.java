package vn.edu.usth.internshipmultiplechoice.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.internshipmultiplechoice.R;
import vn.edu.usth.internshipmultiplechoice.adapter.ExamListAdapter;
import vn.edu.usth.internshipmultiplechoice.object.ExamMini;
import vn.edu.usth.internshipmultiplechoice.retrofit.RetrofitClient;

public class ExamFragment extends Fragment {
    RetrofitClient retrofit;
    SwipeRefreshLayout pullToRefresh;
    List<ExamMini> examList;
    RecyclerView recyclerView;
    ExamListAdapter examListAdapter;
    Context context;
    static final int init = 0;
    static final int update = 1;
    public void init(){
        context = getContext();
        pullToRefresh = ((Activity) context).findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    getList(update);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        recyclerView = (RecyclerView)  ((Activity) context).findViewById(R.id.RecyclerViewExamList);
        examListAdapter = new ExamListAdapter(examList,context);
        recyclerView.setAdapter(examListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }


    public void getList(int mode) throws IOException {
        retrofit = RetrofitClient.getInstance();
        examList = new ArrayList<>();
        Call<List<ExamMini>> examCall = retrofit.getMyApi().getAllExam();
        examCall.enqueue(new Callback<List<ExamMini>>() {
            @Override
            public void onResponse(Call<List<ExamMini>> call, Response<List<ExamMini>> response) {
                examList = response.body();;
                if(mode == init){
                init();
                }
                else{
                    examListAdapter.notifyDataSetChanged();
                    pullToRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<ExamMini>> call, Throwable t) {

            }
        });
    }
    public ExamFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static ExamFragment newInstance() {
        ExamFragment fragment = new ExamFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            getList(init);
            Log.e("asc","Size is" +  String.valueOf(examList.size()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inflater.inflate(R.layout.fragment_exam, container, false);
    }


}