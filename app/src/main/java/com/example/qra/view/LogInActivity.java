package com.example.qra.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qra.R;
import com.example.qra.presenter.LogInPresenter;
import com.example.qra.presenter.interfaces.ILoginPresenter;
import com.example.qra.view.dialogs.LoginNotSucceededMessage;
import com.example.qra.view.interfaces.ILoginView;

public class LogInActivity extends AppCompatActivity implements ILoginView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // initialize views
        EditText passwordTxt = findViewById(R.id.password_text);
        EditText phoneNumberTxt = findViewById(R.id.phone_text);
        Button logInBtn = findViewById(R.id.sing_in_btn);
        Button restoreBtn = findViewById(R.id.restore_password_btn);
        Button registerBtn = findViewById(R.id.register_btn);

        // initialize presenter
        LogInPresenter loginPresenter = new LogInPresenter(this);

        logInBtn.setOnClickListener(action -> {
            findViewById(R.id.login_progress_bar).setVisibility(View.VISIBLE);

            loginPresenter.singUp(
                    passwordTxt.getText().toString(),
                    phoneNumberTxt.getText().toString());
        });
        restoreBtn.setOnClickListener(action -> {
            loginPresenter.restorePassword();
        });
        registerBtn.setOnClickListener(action -> {
            loginPresenter.register();
        });

        loginPresenter.onCreate();
    }

    @Override
    public void showLoginSucceededMessage() {
        findViewById(R.id.login_progress_bar).setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), "Вход успешен", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginNotSucceededMessage() {
        findViewById(R.id.login_progress_bar).setVisibility(View.INVISIBLE);
        new LoginNotSucceededMessage().show(getSupportFragmentManager(), "WRONG LOGIN");
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
