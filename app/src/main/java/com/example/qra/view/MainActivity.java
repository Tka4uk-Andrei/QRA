package com.example.qra.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

    private Button qrScanButton;
    private Button requestButton;
    private Button editGoodsButton;
    private Button settingsButton;

    IMainPresenter presenter;
    INavigationBarPresenter navPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up navigationBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        ConnectViewsWithCode();
//        setViewListeners();

        presenter = new MainPresenter(this);
        navPresenter = new NavigationBarPresenter(this);

        NavigationView navigation = findViewById(R.id.nav_view);
        navigation.setNavigationItemSelectedListener(new OnNavigationViewListener(navPresenter, this));
    }

    /**
     * Find views and establish links between code and view \\
     * Author: Andrey Tkachuk
     */
    public void ConnectViewsWithCode() {
//        qrScanButton = findViewById(R.id.qr_scan_btn);
//        requestButton = findViewById(R.id.send_request_btn);
//        editGoodsButton = findViewById(R.id.edit_goods_btn);
//        settingsButton = findViewById(R.id.settings_btn);
    }

    /**
     * Set listeners to views \\
     * Author: Andrey Tkachuk
     */
    public void setViewListeners() {
        qrScanButton.setOnClickListener(action -> presenter.switchToScanQr());


        requestButton.setOnClickListener(menuBtnClickListener);
        editGoodsButton.setOnClickListener(menuBtnClickListener);
        settingsButton.setOnClickListener(menuBtnClickListener);

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

    private View.OnClickListener menuBtnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = null;

//            if (v == requestButton)
//                intent = new Intent(getApplicationContext(), WebRequestActivity.class);
//            else if (v == editGoodsButton)
//                intent = new Intent(getApplicationContext(), EditBoughtDataActivity.class);
//            else if (v == settingsButton)
//                intent = new Intent(getApplicationContext(), SettingsActivity.class);
//
//            if (intent != null)
//                startActivity(intent);
//            else
//                Toast.makeText(getApplicationContext(), "ERROR. Don't know what activity i need to start",
//                        Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void askUserConfirmToSingOut(YesNoDialog.IYesNoAction action) {
        YesNoDialog.getInstance("Вы точно хотите выйти?", action)
                .show(getSupportFragmentManager(), "singOutConfirm");
    }
}
