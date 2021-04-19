package vn.edu.usth.internshipmultiplechoice.retrofit;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.edu.usth.internshipmultiplechoice.User;
import vn.edu.usth.internshipmultiplechoice.object.Exam;
import vn.edu.usth.internshipmultiplechoice.object.ExamHistory;
import vn.edu.usth.internshipmultiplechoice.object.ExamMini;

public interface Api {
    String URL = "http://192.168.100.3:6789";
    @Headers({"Content-Type: application/json","Accept: application/json","Access-Control-Allow-Credentials: true","Access-Control-Allow-Origin: true"})
    @POST("/api/auth/signin")
    Call<JsonObject> Login(@Body LoginRequest loginRequest);
    @Headers("Content-Type: application/json")
    @GET("/exam/")
    Call<List<ExamMini>> getAllExam();
    @Headers({"Content-Type: application/json","Access-Control-Allow-Credentials: true","Access-Control-Allow-Origin: true"})
    @POST("/api/auth/signup")
    Call<JsonObject> Signup(@Body SignupRequest body);
    @Headers("Content-Type: application/json")
    @GET("/exam/{id}")
    Call<Exam> getExam(@Path("id") String id);

    @Headers("Content-Type: application/json")
    @POST("/user/{id}/exam")
    Call<ResponseBody> sendExam(@Path("id") String id, @Body ExamHistory examHistory);
}
