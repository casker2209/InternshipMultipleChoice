package vn.edu.usth.internshipmultiplechoice.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {

    String Endpoint = "http://localhost:6789";
    @Headers("Content-Type: application/json")
    @POST("/api/auth/signin")
    Call<UserInfo> getUserInfo(@Body String body);
}
