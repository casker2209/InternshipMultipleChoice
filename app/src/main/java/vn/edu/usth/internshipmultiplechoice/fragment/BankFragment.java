package vn.edu.usth.internshipmultiplechoice.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import vn.edu.usth.internshipmultiplechoice.adapter.BankListAdapter;
import vn.edu.usth.internshipmultiplechoice.object.Bank;
import vn.edu.usth.internshipmultiplechoice.retrofit.RetrofitClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BankFragment extends Fragment {
    RetrofitClient retrofit;
    List<Bank> bankList;
    RecyclerView recyclerView;
    BankListAdapter bankListAdapter;
    SwipeRefreshLayout pullToRefresh;

    Context context;
    static final int init = 0;
    static final int update = 1;
    public void init(){
        context = getContext();
        recyclerView = (RecyclerView) ((Activity) context).findViewById(R.id.RecyclerViewBankList);
        bankListAdapter = new BankListAdapter(bankList,context);
        recyclerView.setAdapter(bankListAdapter);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));


    }
    public void getList(int mode) throws IOException {
        retrofit = RetrofitClient.getInstance();
        bankList = new ArrayList<>();
        Call<List<Bank>> examCall = retrofit.getMyApi().getAllBank();
        examCall.enqueue(new Callback<List<Bank>>() {
            @Override
            public void onResponse(Call<List<Bank>> call, Response<List< Bank>> response) {
                bankList = response.body();;
                if(mode == init){
                    init();
                }
                else{
                    bankListAdapter.notifyDataSetChanged();
                    pullToRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<Bank>> call, Throwable t) {

            }
        });
    }
    public BankFragment() {
    }


    public static BankFragment newInstance() {
        BankFragment fragment = new BankFragment();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inflater.inflate(R.layout.fragment_bank, container, false);
    }
}