package vn.edu.usth.internshipmultiplechoice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.internshipmultiplechoice.retrofit.LoginRequest;
import vn.edu.usth.internshipmultiplechoice.retrofit.RetrofitClient;
import vn.edu.usth.internshipmultiplechoice.retrofit.User;
import vn.edu.usth.internshipmultiplechoice.utility.UserSharedPreferences;

public class LoadingActivity extends AppCompatActivity {
    private User user;
    private TextView contentText;
    private RetrofitClient retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        contentText = findViewById(R.id.loadingcontent);
        retrofit = RetrofitClient.getInstance();
        Intent intent = getIntent();

        try {
                Login();
            } catch (JSONException e) {
             e.printStackTrace();
         }
    }
    private void Login() throws JSONException {
        if (UserSharedPreferences.hasUser(LoadingActivity.this)) {
            contentText.setText("Sử dụng thông tin đăng nhập trước đó");
            Intent intent = new Intent(LoadingActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        } else if(getIntent().getBooleanExtra("isLogin",false)){
            contentText.setText("Kiểm tra tên đăng nhập và mật khẩu");
            Intent intent = getIntent();
            JSONObject forLogin = new JSONObject();
            forLogin.put("username", intent.getStringExtra("username"));
            forLogin.put("password", intent.getStringExtra("password"));
            Log.e("Json", forLogin.toString());
            Call<JsonObject> LoginResponse = retrofit.getMyApi().Login(new LoginRequest(intent.getStringExtra("username"), intent.getStringExtra("password")));
            LoginResponse.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonObject result = response.body();
                    System.out.println("The code is: " + response.code());
                    if (response.code() == 200) {
                        System.out.println("Success getting the response");
                        String id = result.get("id").getAsString();
                        String email = result.get("email").getAsString();
                        String username = result.get("username").getAsString();
                        String token = result.get("tokenType").getAsString() + " " + result.get("accessToken").getAsString();
                        String name = result.get("name").getAsString();
                        User user = new User(id, username, email, token, name);
                        UserSharedPreferences.saveUser(user, LoadingActivity.this);

                        User user1 = UserSharedPreferences.getUser(LoadingActivity.this);
                        System.out.println("id: " + user1.toString());
                        startActivity(intent);
                        finish();
                    } else {
                        String error = null;
                        try {
                            error = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(error);
                        JSONObject errorObj;
                        try {
                            errorObj = new JSONObject(error);
                            Toast.makeText(LoadingActivity.this, errorObj.getString("message"), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    t.getStackTrace();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            });
        }
        else{
            Log.e("not login","not logged");
            startActivity(new Intent(LoadingActivity.this,LoginActivity.class));
            finish();
        }
    }

}
