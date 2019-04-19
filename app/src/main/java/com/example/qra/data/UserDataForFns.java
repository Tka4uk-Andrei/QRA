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

    public UserDataForFns(String phone, String pass){
        phoneNumber = phone;
        password = pass;
    }

    public  String getPassword(){
        return password;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    private UserDataForFns(Context context) {

        final String APP_PREFERENCES = "UserDataForFns";

        SharedPreferences mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);



    }

}
