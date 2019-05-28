package com.example.qra.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.qra.R;
import com.example.qra.presenter.MainPresenter;
import com.example.qra.presenter.NavigationBarPresenter;
import com.example.qra.presenter.interfaces.IMainPresenter;
import com.example.qra.presenter.interfaces.INavigationBarPresenter;
import com.example.qra.view.dialogs.AppNotInstalledDialog;
import com.example.qra.view.dialogs.YesNoDialog;
import com.example.qra.view.interfaces.IMainView;
import com.example.qra.view.listeners.OnNavigationViewListener;

// TODO documentation
public class MainActivity extends AppCompatActivity implements IMainView {

    public static final String QR_DATA_EXTRA = "QR_DATA";

    IMainPresenter presenter;
    INavigationBarPresenter navPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ini presenters
        presenter = new MainPresenter(this);
        navPresenter = new NavigationBarPresenter(this);

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

        // set up qrButton listener
        findViewById(R.id.qr_scan_btn).setOnClickListener(action -> presenter.switchToScanQr());

        // TODO set up diagrams

    }


    // TODO WARNING using this function is not obvious and could be declared in parent class
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        presenter.onReturnResult(requestCode, resultCode, intent);
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
    public void showAppNotInstalled() {
        (new AppNotInstalledDialog()).show(getSupportFragmentManager(), "APP_NOT_INSTALLED");
    }

    @Override
    public void askUserConfirmToSingOut(YesNoDialog.IYesNoAction action) {
        YesNoDialog.getInstance("Вы точно хотите выйти?", action)
                .show(getSupportFragmentManager(), "singOutConfirm");
    }
}
