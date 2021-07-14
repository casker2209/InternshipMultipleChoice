package vn.edu.usth.internshipmultiplechoice.utility;

import android.content.Context;
import android.content.SharedPreferences;

import vn.edu.usth.internshipmultiplechoice.retrofit.UserInfo;

public class UserSharedPreferences {
    public static void saveUser(UserInfo user, Context context){
        SharedPreferences preferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putString("id",user.getId());
        preferencesEditor.putString("username",user.getUsername());
        preferencesEditor.putString("token",user.getAccessToken());
        preferencesEditor.putString("name",user.getName());
        preferencesEditor.putString("email",user.getEmail());
        preferencesEditor.commit();
    }
    public static UserInfo getUser(Context context){
        SharedPreferences preferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        UserInfo userInfo = new UserInfo(preferences.getString("id",null),
                preferences.getString("username",null),
                preferences.getString("email",null),
                preferences.getString("token",null),
                preferences.getString("name",null));
        return userInfo;
    }
    public static void deleteUser(Context context){
        SharedPreferences preferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.clear().commit();
    }
    public static boolean hasUser(Context context){
        SharedPreferences preferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        return preferences.contains("username");
    }


}
