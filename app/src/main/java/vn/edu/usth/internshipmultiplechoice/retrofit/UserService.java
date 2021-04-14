package vn.edu.usth.internshipmultiplechoice.retrofit;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {

    String Endpoint = "https://192.168.1.8:6789";
    @Headers("Content-Type: application/json")
    @POST("/api/auth/signin")
    Call<ResponseBody> getUserInfo(@Body String body);
}
