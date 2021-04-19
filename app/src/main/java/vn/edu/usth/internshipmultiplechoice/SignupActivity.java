package vn.edu.usth.internshipmultiplechoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.internshipmultiplechoice.retrofit.RetrofitClient;
import vn.edu.usth.internshipmultiplechoice.retrofit.SignupRequest;

public class SignupActivity extends AppCompatActivity {
    private EditText namebox;
    private EditText passwordbox;
    private EditText usernamebox;
    private EditText emailbox;
    private Button signinbutton;
    private RetrofitClient retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = RetrofitClient.getInstance();
        setContentView(R.layout.activity_signup);
        namebox = findViewById(R.id.namebox);
        usernamebox = findViewById(R.id.usernamebox);
        emailbox = findViewById(R.id.emailbox);
        passwordbox = findViewById(R.id.passwordbox);
        signinbutton = findViewById(R.id.signinbutton);
        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(namebox.getText().toString().isEmpty()&&
                passwordbox.getText().toString().isEmpty()&&
                emailbox.getText().toString().isEmpty())){
                JSONObject jsonObject = new JSONObject();
                SignupRequest signupRequest = new SignupRequest(usernamebox.getText().toString(),passwordbox.getText().toString(),emailbox.getText().toString(),namebox.getText().toString());
                    Call<JsonObject> signup = retrofit.getMyApi().Signup(signupRequest);
                    signup.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if(response.code()==200){
                                System.out.println(response.body().get("message").getAsString());
                                Toast.makeText(SignupActivity.this,response.body().get("message").getAsString(),Toast.LENGTH_LONG).show();

                                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                            finish();
                            }
                            else{
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
                                    Toast.makeText(SignupActivity.this,errorObj.getString("message"),Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

            } else{
                    Toast.makeText(SignupActivity.this,"Unfilled field(s)",Toast.LENGTH_LONG).show();
                }
    }
});}}