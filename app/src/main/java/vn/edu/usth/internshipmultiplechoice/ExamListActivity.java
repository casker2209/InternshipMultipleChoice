package vn.edu.usth.internshipmultiplechoice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.internshipmultiplechoice.object.Exam;
import vn.edu.usth.internshipmultiplechoice.object.ExamMini;
import vn.edu.usth.internshipmultiplechoice.retrofit.RetrofitClient;

public class ExamListActivity extends AppCompatActivity {
    RetrofitClient retrofit;
    List<ExamMini> examList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examlist);
    }
    public void getList(){
        retrofit = RetrofitClient.getInstance();
        examList = new ArrayList<>();
        Call<List<JSONObject>> examCall = retrofit.getMyApi().getAllExam();
        examCall.enqueue(new Callback<List<JSONObject>>() {
            @Override
            public void onResponse(Call<List<JSONObject>> call, Response<List<JSONObject>> response) {
                for(JSONObject jsonObject: response.body()){
                    try {
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String description = jsonObject.getString("description");
                        examList.add(new ExamMini(id,name,description));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    }
            }

            @Override
            public void onFailure(Call<List<JSONObject>> call, Throwable t) {

            }
        });
    }
}