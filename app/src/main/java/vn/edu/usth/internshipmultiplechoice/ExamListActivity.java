package vn.edu.usth.internshipmultiplechoice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.internshipmultiplechoice.adapter.ExamListAdapter;
import vn.edu.usth.internshipmultiplechoice.object.Exam;
import vn.edu.usth.internshipmultiplechoice.object.ExamMini;
import vn.edu.usth.internshipmultiplechoice.retrofit.RetrofitClient;

public class ExamListActivity extends AppCompatActivity {
    RetrofitClient retrofit;
    List<ExamMini> examList;
    RecyclerView recyclerView;
    ExamListAdapter examListAdapter;
    SwipeRefreshLayout pullToRefresh;
    static final int init = 0;
    static final int update = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            getList(init);
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examlist);

    }

    public void init(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pullToRefresh = findViewById(R.id.pullToRefresh);
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
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerViewExamList);
        examListAdapter = new ExamListAdapter(examList,this);
        recyclerView.setAdapter(examListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                if(mode == update) pullToRefresh.setRefreshing(false);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.infoicon:
            {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("Th??ng tin b??i ki???m tra")
                        .setMessage("B???m v??o b??i ki???m tra th??? b???n mu???n ch???n ????? b???t ?????u l??m b??i thi \n" +
                                "B??i thi m?? ph???ng ????? thi v??ng m???t:-Thi c??ng ch???c, 60 c??u (n???u thi ki???n th???c chung),30 c??u (n???u thi ngo???i ng??? ho???c tin h???c)\n" +
                                "B??i thi c?? th???i gian l??m b??i l?? 30 ph??t ho???c 60 ph??t t??y s??? l?????ng c??u trong ????? \n" +
                                "B???m v??o n??t Ho??n th??nh ????? ho??n th??nh b??i thi s???m. \nSau khi b??i thi k???t th??c k???t qu??? s??? ???????c l??u v?? hi???n th???. \n????? xem l???i c?? th??? ch???n ph???n L???ch s??? l??m b??i ??? menu")
                        .setPositiveButton("T??i hi???u r???i",null);
                AlertDialog alertDialog = dialog.show();
                return true;
            }
            default:
               return super.onOptionsItemSelected(item);
        }
    }
}