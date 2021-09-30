package vn.edu.usth.internshipmultiplechoice.retrofit;

import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.edu.usth.internshipmultiplechoice.object.BankContent;
import vn.edu.usth.internshipmultiplechoice.object.BankInfo;
import vn.edu.usth.internshipmultiplechoice.object.ExamContent;
import vn.edu.usth.internshipmultiplechoice.object.ExamResult;
import vn.edu.usth.internshipmultiplechoice.object.ExamMini;

public interface Api {
    String URL = "http:///192.168.1.13:6789";
    @Headers({"Content-Type: application/json","Accept: application/json","Access-Control-Allow-Credentials: true","Access-Control-Allow-Origin: true"})
    @POST("/api/auth/signin")
    Call<JsonObject> Login(@Body LoginRequest loginRequest);
    @Headers("Content-Type: application/json")
    @GET("/exam/")
    Call<List<ExamMini>> getAllExam();
    @Headers("Content-Type: application/json")
    @GET("/bank/")
    Call<List<BankInfo>> getAllBank();

    @Headers({"Content-Type: application/json","Access-Control-Allow-Credentials: true","Access-Control-Allow-Origin: true"})
    @POST("/api/auth/signup")
    Call<JsonObject> Signup(@Body SignupRequest body);
    @GET("/exam/{id}")
    Call<ExamContent> getExam(@Path("id") String id);
    @GET("/bank/{id}")
    Call<BankContent> getBank(@Path("id") String id);

    @Headers("Content-Type: application/json")
    @POST("/api/user/{id}/exam/")
    Call<ResponseBody> sendExam(@Path("id") String id, @Body ExamResult examResult, @Header("Authorization") String accessToken);
    @GET("/api/user/{id}/exam/all")
    Call<List<ExamResult>> getExamHistory(@Path("id") String id, @Header("Authorization") String accessToken);
}
