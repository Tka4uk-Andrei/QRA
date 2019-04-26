package com.example.qra.model;

import android.content.Context;
import android.content.SharedPreferences;
import static android.content.Context.MODE_PRIVATE;

/**
 * Class that stores FNS's credentials for user, and gives access to them.
 * @author Tkachuk Andrei
 */
public class UserDataForFns {
    private static UserDataForFns ourInstance;
    private static UserDataForFns ourDefaultInstance;

    private String userName;
    private String phoneNumber;
    private String userEmail;
    private String password;

    /**
     * key to access {@link SharedPreferences}
     */
    private static final String USER_LOGIN_DATA = "USER_DATA_IN_FNS";

    /**
     * key to access phone number in USER_LOGIN_DATA preferences
     */
    private static final String PHONE_KEY = "PHONE";

    /**
     * key to access password in USER_LOGIN_DATA preferences
     */
    private static final String PASSWORD_KEY = "PASSWORD";

    /**
     * key to access email in USER_LOGIN_DATA preferences
     */
    private static final String USER_MAIL_KEY = "MAIL";

    /**
     * key to access user name in USER_LOGIN_DATA preferences
     */
    private static final String USER_NAME_KEY = "NAME";

    /**
     * Method that provides access to {@link UserDataForFns} class
     * @param context android {@link Context} class, used to get info from cache memory.
     *                If you sure, that class was initialized previously you could use <b>null</b>
     * @return register data used in FNS
     */
    public static UserDataForFns getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new UserDataForFns(context);

        return ourInstance;
    }

    /**
     * Method that provides access to {@link UserDataForFns} class
     * @return default {@link UserDataForFns} class (Tkachuk Andrey credentials)
     */
    public static UserDataForFns getInstanceDefault() {
        if (ourDefaultInstance == null)
            ourDefaultInstance = new UserDataForFns();

        return ourDefaultInstance;
    }

    private UserDataForFns()
    {
        phoneNumber = "+79817987505";
        password = "721363";
        userName = "Kate";
        userEmail = "novos.katerina@mail.ru";
    }

    private UserDataForFns(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_LOGIN_DATA, MODE_PRIVATE);

        phoneNumber = preferences.getString(PHONE_KEY, "");
        password = preferences.getString(PASSWORD_KEY, "");
        userName = preferences.getString(USER_NAME_KEY, "");
        userEmail = preferences.getString(USER_MAIL_KEY, "");
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Set phone number
     *
     * <p>Note that you <em>must</em> call {@link UserDataForFns#apply(Context)} to have any
     * changes in the SharedPreferences.
     *
     * @param phoneNumber phone number used in registration
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Set Email address
     *
     * <p>Note that you <em>must</em> call {@link UserDataForFns#apply(Context)} to have any
     * changes in the SharedPreferences.
     *
     * @param userEmail Email used in registration
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * Set phone number
     *
     * <p>Note that you <em>must</em> call {@link UserDataForFns#apply(Context)} to have any
     * changes in the SharedPreferences.
     *
     * @param password phone number used in registration
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set phone number
     *
     * <p>Note that you <em>must</em> call {@link UserDataForFns#apply(Context)} to have any
     * changes in the SharedPreferences.
     *
     * @param userName phone number used in registration
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Save data from {@link UserDataForFns} to {@link SharedPreferences}
     *
     * @param context android {@link Context} class, used to save info in cache memory
     */
    public void apply(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_LOGIN_DATA, MODE_PRIVATE).edit();
        editor.putString(PHONE_KEY, phoneNumber);
        editor.putString(USER_MAIL_KEY, userEmail);
        editor.putString(PASSWORD_KEY, password);
        editor.putString(USER_NAME_KEY, userName);
        editor.apply();
    }
}
