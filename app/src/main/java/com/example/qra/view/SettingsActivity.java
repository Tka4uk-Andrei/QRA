package com.example.qra.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.qra.R;
import com.example.qra.presenter.SettingsPresenter;
import com.example.qra.view.dialogs.YesNoDialog;
import com.example.qra.view.interfaces.ISettingsView;

public class SettingsActivity extends AppCompatActivity implements ISettingsView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SettingsPresenter presenter = new SettingsPresenter(this);
        findViewById(R.id.log_out_btn).setOnClickListener(action -> presenter.onSingOutCall());

        presenter.onCreate();
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
    public void askUserConfirmToSingOut(YesNoDialog.IYesNoAction action) {
        YesNoDialog.getInstance("Вы точно хотите выйти?", action)
                .show(getSupportFragmentManager(), "singOutConfirm");
    }
}
