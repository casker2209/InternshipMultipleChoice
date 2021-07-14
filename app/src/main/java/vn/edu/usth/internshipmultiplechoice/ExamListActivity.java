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
                AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("Thông tin bài kiểm tra")
                        .setMessage("Bấm vào bài kiểm tra thử bạn muốn chọn để bắt đầu làm bài thi \n" +
                                "Bài thi mô phỏng đề thi vòng một: Thi công chức, 60 câu (nếu thi kiến thức chung),30 câu (nếu thi ngoại ngữ hoặc tin học)\n" +
                                "Bài thi có thời gian làm bài là 30 phút hoặc 60 phút tùy số lượng câu trong đề \n" +
                                "Bấm vào nút Hoàn thành để hoàn thành bài thi sớm. Sau khi bài thi kết thúc kết quả sẽ được lưu và hiển thị. Để xem lại có thể chọn phần Lịch sử làm bài ở menu")
                        .setPositiveButton("Tôi hiểu rồi",null);
                AlertDialog alertDialog = dialog.show();
                return true;
            }
            default:
               return super.onOptionsItemSelected(item);
        }
    }
}