package com.example.qra.presenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.qra.model.UserDataForFns;
import com.example.qra.model.webRequests.requests.LoginWebRequest;
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

    private ILoginView view;

    // login view should be extend from AppCompatActivity
    public LogInPresenter(ILoginView view) {
        super(view);
        this.view = view;
    }

    @Override
    public void onCreate() {

        // obtain launch Intent
        Intent launchIntent = view.getStarterIntent();
        if (launchIntent != null && launchIntent.getStringExtra(STARTER_ACTIVITY) != null) {
            //check if activity launched from LogInPresenter or RegisterPresenter
            if (launchIntent.getStringExtra(STARTER_ACTIVITY).equals(LogInPresenter.class.getName())
                    || launchIntent.getStringExtra(STARTER_ACTIVITY).equals(RegisterPresenter.class.getName()))
                view.updatePhoneText(launchIntent.getStringExtra(PHONE_EXTRA));
        }

        // check if user logged in
        boolean loggedFlag = UserDataForFns.getInstance(view.getContext()).isLogged();
        if (loggedFlag) {
            view.showLoginSucceededMessage();
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
        // Update user data
        UserDataForFns.getInstance(getView().getContext()).setUserEmail(email);
        UserDataForFns.getInstance(getView().getContext()).setUserName(name);
        UserDataForFns.getInstance(view.getContext()).setLoggedInFlag(true);
        UserDataForFns.getInstance(getView().getContext()).apply(getView().getContext());

        // Show success message and launch main screen
        view.showLoginSucceededMessage();
        startActivity(MainActivity.class, true);
    }

    // if login failed
    private void singUpNotSucceeded() {
        view.showLoginNotSucceededMessage();
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
