package com.example.qra.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.qra.R;
import com.example.qra.presenter.NavigationBarPresenter;
import com.example.qra.presenter.ShowAllChecksPresenter;
import com.example.qra.view.dialogs.YesNoDialog;
import com.example.qra.view.interfaces.IShowAllChecksView;
import com.example.qra.view.listeners.OnNavigationViewListener;


public class ShowAllChecksActivity extends AppCompatActivity implements IShowAllChecksView {

    private ShowAllChecksPresenter presenter;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_checks);

        // initialize presenters
        NavigationBarPresenter navPresenter = new NavigationBarPresenter(this);
        presenter = new ShowAllChecksPresenter(this);
        presenter.onCreate();

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

        // initialize checks list
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, presenter.getCheckList());
        listView = findViewById(R.id.choose_check);
        listView.setAdapter(arrayAdapter);

        // set clickListeners
        listView.setOnItemClickListener((adapterView, view, i, l) -> presenter.startShowItemsInCheckActivity(i));
        findViewById(R.id.add_check_btn).setOnClickListener(v -> presenter.addCheck());
    }

    @Override
    public void update(String[] checkList) {
        listView = findViewById(R.id.choose_check);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, checkList);
        listView.setAdapter(arrayAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            presenter.updateView();
        }
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
        YesNoDialog.getInstance(getString(R.string.confirm_log_out), action)
                .show(getSupportFragmentManager(), "singOutConfirm");
    }
}
