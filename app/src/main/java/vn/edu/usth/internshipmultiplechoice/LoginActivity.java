package vn.edu.usth.internshipmultiplechoice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.usth.internshipmultiplechoice.retrofit.RetrofitClient;

public class LoginActivity extends AppCompatActivity {

    private EditText passwordBox;
    private EditText usernameBox;
    //    private TextView loginGoogle;
//    GoogleApiClient mGoogleApiClient;
//    private static final int RC_SIGN_IN = 9001;
    private TextView login;
    private TextView signup;
    private TextView guest;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
/*        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
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
                .build(); */
        final RetrofitClient retrofitClient = RetrofitClient.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        passwordBox = findViewById(R.id.passwordbox);
        usernameBox = findViewById(R.id.usernamebox);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signupbutton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = usernameBox.getText().toString();
                String password = passwordBox.getText().toString();
                if (user.isEmpty() || password.isEmpty()) {
                    Log.e("Login failed", "Username/password not filled in");
                    Toast.makeText(getBaseContext(), "Bạn chưa điền tài khoản hoặc mật khẩu", Toast.LENGTH_LONG).show();
                } else {
                    Log.e("Clicked", "Clicked");
                    String namepassed = usernameBox.getText().toString();
                    String passwordpassed = passwordBox.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), LoadingActivity.class);
                    intent.putExtra("username", namepassed);
                    intent.putExtra("password", passwordpassed);
                    intent.putExtra("isLogin",true);

                    startActivity(intent);
                }
                ;
        /*
                    try {
                        paramObject.put("username", usernameBox.getText().toString());
                        paramObject.put("password", passwordBox.getText().toString());
                        System.out.println(paramObject);
                        Call<ResponseBody> userCall = retrofitClient.getMyApi().Login(paramObject.toString());
                        userCall.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Log.e("Success", "Login successfully");
                                System.out.print("OK");
                                Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_LONG);
                            }

                            @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
/*        loginGoogle = findViewById(R.id.logingoogle);
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

}*/
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
            }
        });
        guest = findViewById(R.id.guest);
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
