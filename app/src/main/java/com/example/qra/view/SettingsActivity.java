package com.example.qra.view;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.qra.R;
import com.example.qra.presenter.NavigationBarPresenter;
import com.example.qra.presenter.SettingsPresenter;
import com.example.qra.view.dialogs.YesNoDialog;
import com.example.qra.view.interfaces.ISettingsView;
import com.example.qra.view.listeners.OnNavigationViewListener;

public class SettingsActivity extends AppCompatActivity implements ISettingsView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // ini presenters
        SettingsPresenter presenter = new SettingsPresenter(this);
        NavigationBarPresenter navPresenter = new NavigationBarPresenter(this);

        // set up navigationBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigation = findViewById(R.id.nav_view);
        navigation.setNavigationItemSelectedListener(new OnNavigationViewListener(navPresenter, this));

        // set logOut button listener
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
