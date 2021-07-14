package vn.edu.usth.internshipmultiplechoice.retrofit;

import vn.edu.usth.internshipmultiplechoice.User;

public class StaticUserInformation {
    private UserInfo user;
    private boolean isGuest;


    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public UserInfo getUserInfo() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    private static final StaticUserInformation UserInfo = new StaticUserInformation();
    public static StaticUserInformation getInstance(){
        return UserInfo;
    }


}
