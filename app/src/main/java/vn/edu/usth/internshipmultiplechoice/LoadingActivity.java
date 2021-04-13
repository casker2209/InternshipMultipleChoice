package vn.edu.usth.internshipmultiplechoice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.internshipmultiplechoice.object.Exam;
import vn.edu.usth.internshipmultiplechoice.object.Question;
import vn.edu.usth.internshipmultiplechoice.retrofit.LoginRequest;
import vn.edu.usth.internshipmultiplechoice.retrofit.RetrofitClient;
import vn.edu.usth.internshipmultiplechoice.retrofit.UserInfo;

public class LoadingActivity extends AppCompatActivity {
    private UserInfo userInfo;
    private TextView contentText;
    private RetrofitClient retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        contentText = findViewById(R.id.loadingcontent);
        retrofit = RetrofitClient.getInstance();
        if(getIntent().getBooleanExtra("isGuest",false)) {
            getExamList();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.putExtra("isGuest",true);
            startActivity(intent);
        }
        else{
            try {
                Login();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void Login() throws JSONException {
        contentText.setText("Verify the account and download user information");
        Intent intent = getIntent();
        JSONObject forLogin = new JSONObject();
        forLogin.put("username",intent.getStringExtra("username"));
        forLogin.put("password",intent.getStringExtra("password"));
        Log.e("Json",forLogin.toString());
        Call<JSONObject> LoginResponse =  retrofit.getMyApi().Login(new LoginRequest(intent.getStringExtra("username"),intent.getStringExtra("password")));
        LoginResponse.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                JSONObject result = response.body();
                if (response.code() == 200) {
                    try {
                        String id = result.getString("id");
                        String email = result.getString("email");
                        String username = result.getString("username");
                        String token = result.getString("accessToken");
                        userInfo = new UserInfo(id, username, email, token);
                        System.out.println("Successfuly download the user information");
                        getExamList();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                }
            else{
                Log.e("error","Something wrong");
                Log.e("error", String.valueOf(response.body()));
                Log.e("error",String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                t.getStackTrace();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }



    private void getExamList(){
        contentText.setText("Getting the list of the exam");
        Call<List<JSONObject>> ExamList = retrofit.getMyApi().getAllExam();
        ExamList.enqueue(new Callback<List<JSONObject>>() {
            @Override
            public void onResponse(Call<List<JSONObject>> call, Response<List<JSONObject>> response) {
                System.out.println("Download successfully the examlist");
                System.out.println("Log in success");

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<JSONObject>> call, Throwable t) {
                t.getStackTrace();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }
}
