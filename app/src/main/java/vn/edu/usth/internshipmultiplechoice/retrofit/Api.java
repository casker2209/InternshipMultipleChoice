package vn.edu.usth.internshipmultiplechoice.retrofit;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import vn.edu.usth.internshipmultiplechoice.User;

public interface Api {
    String URL = "http://192.168.100.3:6789";
    @Headers({"Content-Type: application/json","Accept: application/json","Access-Control-Allow-Credentials: true","Access-Control-Allow-Origin: true"})
    @POST("/api/auth/signin")
    Call<JSONObject> Login(@Body LoginRequest loginRequest);
    @Headers("Content-Type: application/json")
    @GET("/exam/")
    Call<List<JSONObject>> getAllExam();
    @Headers("Content-Type: application/json")
    @POST("/api/auth/signup")
    Call<String> Signup(@Body JSONObject body);
}
