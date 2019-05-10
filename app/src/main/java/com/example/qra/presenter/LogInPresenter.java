package com.example.qra.presenter;

import android.content.Intent;
import android.content.SharedPreferences;

import com.example.qra.model.UserDataForFns;
import com.example.qra.presenter.interfaces.ILoginPresenter;
import com.example.qra.view.MainActivity;
import com.example.qra.view.RestorePasswordActivity;
import com.example.qra.view.interfaces.ILoginView;

import static android.content.Context.MODE_PRIVATE;

// TODO documentation
public class LogInPresenter extends AndroidPresenter implements ILoginPresenter {

    static String STARTER_ACTIVITY = "ACTIVITY";
    static String PHONE_EXTRA = "PHONE_EXTRA";

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
        // check if user not logged in
        SharedPreferences firstTimePreference = getView().getContext().getSharedPreferences(LOGIN, MODE_PRIVATE);
        boolean loggedFlag = firstTimePreference.getBoolean(IS_LOGGED, false);
        if (loggedFlag) {
            loginView.showLoginSucceededMessage();
            startActivity(MainActivity.class, true);
        }

        // obtain launch Intent
        Intent launchIntent = loginView.getStarterIntent();
        if (launchIntent != null && launchIntent.getStringExtra(STARTER_ACTIVITY) != null) {
            //check if activity launched from RestorePassword Activity
            if (launchIntent.getStringExtra(STARTER_ACTIVITY).equals(LogInPresenter.class.getName()))
                loginView.updatePhoneText(launchIntent.getStringExtra(PHONE_EXTRA));
        }
    }

    @Override
    public void singUp(String password, String login) {
        //TODO send login request

        // if login succeed
        if (password.length() != 0 && login.length() != 0) {
            // Set registration data
            UserDataForFns.getInstance(getView().getContext()).setPassword(password);
            UserDataForFns.getInstance(getView().getContext()).setPhoneNumber(login);

            // Update status. Now user logged in
            SharedPreferences.Editor editor = getView().getContext().getSharedPreferences(LOGIN, MODE_PRIVATE).edit();
            editor.putBoolean(IS_LOGGED, false);
            editor.apply();

            // Show success message and launch main screen
            loginView.showLoginSucceededMessage();
            startActivity(MainActivity.class, true);
        }

        loginView.showLoginNotSucceededMessage();
    }

    @Override
    public void register() {

    }

    @Override
    public void restorePassword() {
        startActivity(RestorePasswordActivity.class, false);
    }
}
