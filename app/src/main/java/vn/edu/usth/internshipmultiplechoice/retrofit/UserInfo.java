package vn.edu.usth.internshipmultiplechoice.retrofit;

public class UserInfo {
    private String id;
    private String username;
    private String email;
    private String accessToken;
    public UserInfo(String id,String username,String email,String accessToken){
        this.id = id;
        this.username = username;
        this.email = email;
        this.accessToken = accessToken;
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
}
