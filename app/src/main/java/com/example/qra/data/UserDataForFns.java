package com.example.qra.data;

import android.content.Context;
import android.content.SharedPreferences;

public class UserDataForFns {
    private static UserDataForFns ourInstance;

    private String userName;
    private String phoneNumber;
    private String userEmail;
    private String password;

    public static UserDataForFns getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new UserDataForFns(context);

        return ourInstance;
    }

    public  String getUserName(){
        return userName;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public  String getUserEmail(){
        return userEmail;
    }

    public  String getPassword(){
        return password;
    }

    private UserDataForFns(Context context) {

        final String APP_PREFERENCES = "UserDataForFns";

        SharedPreferences mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);



    }

}
