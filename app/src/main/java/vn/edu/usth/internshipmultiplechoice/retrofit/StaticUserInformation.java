package vn.edu.usth.internshipmultiplechoice.retrofit;

public class StaticUserInformation {
    private User user;
    private boolean isGuest;


    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public User getUserInfo() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private static final StaticUserInformation UserInfo = new StaticUserInformation();
    public static StaticUserInformation getInstance(){
        return UserInfo;
    }


}
