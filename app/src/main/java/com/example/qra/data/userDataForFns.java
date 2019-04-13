package com.example.qra.data;

import android.content.Context;
import android.content.SharedPreferences;

public class userDataForFns {
    private static userDataForFns ourInstance;

    private String userName;
    private String phoneNumber;
    private String userEmail;
    private String password;

    public static userDataForFns getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new userDataForFns(context);

        return ourInstance;
    }

    private userDataForFns(Context context) {

        final String APP_PREFERENCES = "userDataForFns";

        SharedPreferences mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);



    }

}
