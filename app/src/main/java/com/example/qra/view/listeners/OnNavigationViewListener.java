package com.example.qra.view.listeners;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.example.qra.R;
import com.example.qra.presenter.interfaces.INavigationBarPresenter;

public class OnNavigationViewListener implements
        NavigationView.OnNavigationItemSelectedListener {

    private INavigationBarPresenter navBarPresenter;
    private AppCompatActivity activity;

    public OnNavigationViewListener(INavigationBarPresenter navBarPresenter, AppCompatActivity activity) {
        this.navBarPresenter = navBarPresenter;
        this.activity = activity;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                navBarPresenter.switchToMain();
                break;

            case R.id.nav_checkItemsList:
                navBarPresenter.switchToShowAllItems();
                break;

            case R.id.nav_categories:
                navBarPresenter.switchToShowCategoryList();
                break;

            case R.id.nav_checksList:
                navBarPresenter.switchToShowCheckList();
                break;

            case R.id.nav_settings:
                navBarPresenter.switchToSettings();
                break;

            case R.id.nav_exit:
                navBarPresenter.onSingOutCall();
                break;
        }

        DrawerLayout drawer = activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
