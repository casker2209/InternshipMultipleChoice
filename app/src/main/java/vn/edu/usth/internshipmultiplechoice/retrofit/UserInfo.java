package vn.edu.usth.internshipmultiplechoice.retrofit;

public class UserInfo {
    private String id;
    private String username;
    private String password;
    private String email;
    private String token;
    public UserInfo(String id,String username,String password,String email,String token){
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.token = token;
    }
}
