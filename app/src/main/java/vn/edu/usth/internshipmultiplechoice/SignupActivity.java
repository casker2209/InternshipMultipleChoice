package vn.edu.usth.internshipmultiplechoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.internshipmultiplechoice.retrofit.RetrofitClient;

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
        usernamebox = findViewById(R.id.namebox);
        emailbox = findViewById(R.id.namebox);
        passwordbox = findViewById(R.id.passwordbox);
        signinbutton = findViewById(R.id.signinbutton);
        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(namebox.getText().toString().isEmpty()&&
                passwordbox.getText().toString().isEmpty()&&
                emailbox.getText().toString().isEmpty())){
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("username",usernamebox.getText().toString());
                    jsonObject.put("password",passwordbox.getText().toString());
                    jsonObject.put("email",emailbox.getText().toString());
                    jsonObject.put("roles","user");
                    Call<String> signup = retrofit.getMyApi().Signup(jsonObject);
                    signup.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.code()==200){
                            System.out.println(response.body());
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));}
                            else{
                                System.out.println(response.code());
                                System.out.println(response.errorBody()); }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else{
                    Toast.makeText(getBaseContext(),"Unfilled field(s)",Toast.LENGTH_LONG);
                }
    }
});}}