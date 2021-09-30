package vn.edu.usth.internshipmultiplechoice.retrofit;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("email")
    private String email;
    @SerializedName("accessToken")
    private String accessToken;
    @SerializedName("username")
    private String username;
    @SerializedName("name")
    private String name;
    public User(String id, String username, String email, String accessToken, String name){
        this.id = id;
        this.username = username;
        this.email = email;
        this.accessToken = accessToken;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
    public String getAccessToken() {
        return accessToken;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }
}
