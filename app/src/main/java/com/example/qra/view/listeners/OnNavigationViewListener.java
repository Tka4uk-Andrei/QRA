package com.example.qra.view.listeners;

import android.support.design.widget.NavigationView;
import android.view.View;

import com.example.qra.R;
import com.example.qra.presenter.interfaces.INavigationBarPresenter;

public class OnNavigationViewListener implements NavigationView.OnClickListener {

    public INavigationBarPresenter navBarPresenter;

    public OnNavigationViewListener(INavigationBarPresenter navBarPresenter) {
        this.navBarPresenter = navBarPresenter;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
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
                navBarPresenter.logOut();
                break;
        }
    }

}
