package com.example.qra.view;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.qra.R;
import com.example.qra.presenter.RestorePresenter;
import com.example.qra.view.dialogs.RestoreNotSucceededMessage;
import com.example.qra.view.interfaces.IRestorePasswordView;

public class RestorePasswordActivity extends AppCompatActivity implements IRestorePasswordView {

    private ProgressBar restoreProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_password);

        EditText phoneNumber = findViewById(R.id.phone_number);
        Button restoreBtn = findViewById(R.id.restore_password_btn);
        restoreProgress = findViewById(R.id.restore_progress_bar);

        RestorePresenter presenter = new RestorePresenter(this);

        restoreBtn.setOnClickListener(action -> {
            restoreProgress.setVisibility(View.VISIBLE);
            presenter.restorePassword(phoneNumber.getText().toString());
        });
    }

    @Override
    public void showRestoreSucceeded() {
        restoreProgress.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(),
                "На введённый номер отправленно СМС с паролем",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void showRestoreNotSucceeded() {
        restoreProgress.setVisibility(View.GONE);
        new RestoreNotSucceededMessage().show(getSupportFragmentManager(), "RESTORE_NOT_SUCCEED");
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public Intent getStarterIntent() {
        return getIntent();
    }
}
