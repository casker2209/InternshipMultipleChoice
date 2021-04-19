package vn.edu.usth.internshipmultiplechoice.retrofit;

import java.util.HashSet;
import java.util.Set;

public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private String name;
    private Set<String> role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
    public SignupRequest(String username,String password,String email,String name){
        this.username = username;
        this.password = password;
        this.email = email;
        Set<String> role_set = new HashSet<>();
        role_set.add("user");
        this.role = role_set;
        this.name = name;
    }


}
