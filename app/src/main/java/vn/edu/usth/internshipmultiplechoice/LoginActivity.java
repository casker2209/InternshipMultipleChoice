package vn.edu.usth.internshipmultiplechoice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.edu.usth.internshipmultiplechoice.retrofit.UserInfo;
import vn.edu.usth.internshipmultiplechoice.retrofit.UserService;

public class LoginActivity extends AppCompatActivity {

    private EditText passwordBox;
    private EditText usernameBox;
    private TextView loginGoogle;
    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_clientid))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserService.Endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final UserService userService = retrofit.create(UserService.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        passwordBox = findViewById(R.id.passwordbox);
        usernameBox = findViewById(R.id.usernamebox);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = usernameBox.getText().toString();
                String password = passwordBox.getText().toString();
                if (user.isEmpty() || password.equals("")) {
                    Log.e("Login failed","Username/password not filled in");
                    Toast.makeText(getBaseContext(),"Username/password not filled in",Toast.LENGTH_LONG);
                }
                else{
                    JSONObject paramObject = new JSONObject();
                    try {
                        paramObject.put("username", "guest2");
                        paramObject.put("password", "asdasdsadasdasdasdasdasdas");
                        Call<UserInfo> userCall = userService.getUserInfo(paramObject.toString());
                        userCall.enqueue(new Callback<UserInfo>() {
                            @Override
                            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                                Log.e("Success","Login successfully");
                                System.out.print("OK");
                                Toast.makeText(getApplicationContext(),"Login",Toast.LENGTH_LONG);
                            }

                            @Override
                            public void onFailure(Call<UserInfo> call, Throwable t) {

                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        loginGoogle = findViewById(R.id.logingoogle);
        loginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInByGoogle();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            if(data==null){
                Log.d("null","null");
            }
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            System.out.println("This is result:" + result);
            result.getSignInAccount();
            handleSignInByGoogle(result);
        }
    }

    private void signInByGoogle(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void handleSignInByGoogle(GoogleSignInResult result){
        GoogleSignInAccount acc = result.getSignInAccount();
        //String idtoken = acc.getIdToken();
        //Log.d("DD",idtoken);

    }

}