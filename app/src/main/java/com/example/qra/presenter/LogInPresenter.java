package com.example.qra.presenter;

import android.content.SharedPreferences;

import com.example.qra.model.UserDataForFns;
import com.example.qra.presenter.interfaces.ILoginPresenter;
import com.example.qra.view.interfaces.ILoginView;

import static android.content.Context.MODE_PRIVATE;

// TODO documentation
public class LogInPresenter implements ILoginPresenter {

    private static final String FIRST_TIME_RUN = "First time";
    private static final String IS_FIRST_TIME = "is first";

    private ILoginView loginView;

    public LogInPresenter(ILoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void onCreate() {

        SharedPreferences firstTimePreference = loginView.getContext().getSharedPreferences(FIRST_TIME_RUN, MODE_PRIVATE);
        boolean isFirstLaunch = firstTimePreference.getBoolean(IS_FIRST_TIME, true);

        if (isFirstLaunch) {
            SharedPreferences.Editor editor = firstTimePreference.edit();
            editor.putBoolean(IS_FIRST_TIME, false);
            editor.apply();
        } else {
            loginView.showLoginSucceededMessage();
        }
    }

    @Override
    public void singUp(String password, String login) {
        //TODO send login request

        // if login succeed
        if (password.length() != 0 && login.length() != 0) {
            UserDataForFns.getInstance(loginView.getContext()).setPassword(password);
            UserDataForFns.getInstance(loginView.getContext()).setPhoneNumber(login);

            loginView.showLoginSucceededMessage();
            return;
        }

        loginView.showLoginNotSucceededMessage();
    }

}
