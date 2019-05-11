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
import com.example.qra.model.UserDataForFns;
import com.example.qra.presenter.RegisterPresenter;
import com.example.qra.view.interfaces.IRegisterView;

public class RegisterInFnsActivity extends AppCompatActivity implements IRegisterView {

    private EditText telephoneText;
    private EditText userNameText;
    private EditText emailText;

    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_in_fns);

        // initialize views
        telephoneText = findViewById(R.id.phone_number);
        userNameText = findViewById(R.id.user_name);
        emailText = findViewById(R.id.email);
        registerBtn = findViewById(R.id.register_btn);

        RegisterPresenter presenter = new RegisterPresenter(this);
        presenter.onCreate();

        registerBtn.setOnClickListener(action -> {
            Toast.makeText(getApplicationContext(), "Registration couldn't be started now, but I'm saving data", Toast.LENGTH_SHORT).show();
            presenter.registerUser(
                    userNameText.getText().toString(),
                    emailText.getText().toString(),
                    telephoneText.getText().toString());
        });
    }


    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public Intent getStarterIntent() {
        return getIntent();
    }

    @Override
    public void setTelephoneText(String phone) {
        telephoneText.setText(phone);
    }

    @Override
    public void setUserNameText(String name) {
        userNameText.setText(name);
    }

    @Override
    public void setEmailText(String email) {
        emailText.setText(email);
    }
}
