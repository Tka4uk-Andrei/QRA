package com.example.qra.presenter.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.qra.model.UserDataForFns;
import com.example.qra.model.webRequests.requests.LoginWebRequest;
import com.example.qra.presenter.AndroidPresenter;
import com.example.qra.presenter.RegisterPresenter;
import com.example.qra.presenter.SettingsPresenter;
import com.example.qra.presenter.interfaces.ILoginPresenter;
import com.example.qra.view.MainActivity;
import com.example.qra.view.RegisterInFnsActivity;
import com.example.qra.view.RestorePasswordActivity;
import com.example.qra.view.interfaces.ILoginView;

import static android.content.Context.MODE_PRIVATE;

// TODO documentation
public class LogInPresenter extends AndroidPresenter implements ILoginPresenter {

    public static final String STARTER_ACTIVITY = "ACTIVITY";
    public static final String PHONE_EXTRA = "PHONE_EXTRA";

    private static final String LOGIN = "login";
    private static final String IS_LOGGED = "is_login_succeeded";

    private ILoginView loginView;

    // login view should be extend from AppCompatActivity
    public LogInPresenter(ILoginView loginView) {
        super(loginView);
        this.loginView = loginView;
    }

    @Override
    public void onCreate() {
        // obtain launch Intent
        Intent launchIntent = loginView.getStarterIntent();
        if (launchIntent != null && launchIntent.getStringExtra(STARTER_ACTIVITY) != null) {
            //check if activity launched from LogInPresenter or RegisterPresenter
            if (launchIntent.getStringExtra(STARTER_ACTIVITY).equals(LogInPresenter.class.getName())
                    || launchIntent.getStringExtra(STARTER_ACTIVITY).equals(RegisterPresenter.class.getName()))
                loginView.updatePhoneText(launchIntent.getStringExtra(PHONE_EXTRA));
            //check if user wants to logout
            if (launchIntent.getStringExtra(STARTER_ACTIVITY).equals(SettingsPresenter.class.getName())){
                // Update status. Now user not logged in
                SharedPreferences.Editor editor = getView().getContext().getSharedPreferences(LOGIN, MODE_PRIVATE).edit();
                editor.putBoolean(IS_LOGGED, false);
                editor.apply();

                // Clear user data
                UserDataForFns.getInstance(getView().getContext()).setPhoneNumber("");
                UserDataForFns.getInstance(getView().getContext()).setUserName("");
                UserDataForFns.getInstance(getView().getContext()).setUserEmail("");
                UserDataForFns.getInstance(getView().getContext()).setPassword("");
                UserDataForFns.getInstance(getView().getContext()).apply(getView().getContext());
            }
        }

        // check if user not logged in
        SharedPreferences firstTimePreference = getView().getContext().getSharedPreferences(LOGIN, MODE_PRIVATE);
        boolean loggedFlag = firstTimePreference.getBoolean(IS_LOGGED, false);
        if (loggedFlag) {
            loginView.showLoginSucceededMessage();
            startActivity(MainActivity.class, true);
        }
    }

    @Override
    public void singUp(String password, String login) {
        // update registration data
        UserDataForFns.getInstance(getView().getContext()).setPassword(password);
        UserDataForFns.getInstance(getView().getContext()).setPhoneNumber(login);
        UserDataForFns.getInstance(getView().getContext()).apply(getView().getContext());

        // send request with handlers
        new Thread(new LoginWebRequest(
                UserDataForFns.getInstance(getView().getContext()),
                new LoginNotSucceededHandler(this),
                new LoginSuccessHandler(this)
        )).start();
    }


    // if login succeed
    private void singUpSucceeded(String name, String email) {
        // Update status. Now user logged in
        SharedPreferences.Editor editor = getView().getContext().getSharedPreferences(LOGIN, MODE_PRIVATE).edit();
        editor.putBoolean(IS_LOGGED, true);
        editor.apply();

        // Update user data
        UserDataForFns.getInstance(getView().getContext()).setUserEmail(email);
        UserDataForFns.getInstance(getView().getContext()).setUserName(name);
        UserDataForFns.getInstance(getView().getContext()).apply(getView().getContext());

        // Show success message and launch main screen
        loginView.showLoginSucceededMessage();
        startActivity(MainActivity.class, true);
    }

    // if login failed
    private void singUpNotSucceeded() {
        loginView.showLoginNotSucceededMessage();
    }


    @Override
    public void register() {
        startActivity(RegisterInFnsActivity.class, false);
    }


    @Override
    public void restorePassword() {
        startActivity(RestorePasswordActivity.class, false);
    }


    private static class LoginSuccessHandler extends Handler {

        private LogInPresenter loginPresenter;

        LoginSuccessHandler(LogInPresenter loginPresenter) {
            this.loginPresenter = loginPresenter;
        }

        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            loginPresenter.singUpSucceeded(bundle.getString(LoginWebRequest.HANDLE_RETURN_KEY_NAME),
                    bundle.getString(LoginWebRequest.HANDLE_RETURN_KEY_EMAIL));
        }
    }


    private static class LoginNotSucceededHandler extends Handler {

        private LogInPresenter loginPresenter;

        LoginNotSucceededHandler(LogInPresenter loginPresenter) {
            this.loginPresenter = loginPresenter;
        }

        @Override
        public void handleMessage(Message msg) {
            loginPresenter.singUpNotSucceeded();
        }
    }
}
