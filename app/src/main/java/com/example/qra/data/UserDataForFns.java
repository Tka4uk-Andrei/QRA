package com.example.qra.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Class that stores FNS's credentials for user, and gives access to them.
 * @author Tkachuk Andrei
 */
public class UserDataForFns {
    private static UserDataForFns ourInstance;

    private String userName;
    private String phoneNumber;
    private String userEmail;
    private String password;

    /**
     * Method that provides access to {@link UserDataForFns} class
     * @param context android {@link Context} class, used to get info from cache memory
     * @return register data used in FNS
     */
    public static UserDataForFns getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new UserDataForFns(context);

        return ourInstance;
    }

    private UserDataForFns(Context context) {

        final String APP_PREFERENCES = "UserDataForFns";

        SharedPreferences mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

    }

}
