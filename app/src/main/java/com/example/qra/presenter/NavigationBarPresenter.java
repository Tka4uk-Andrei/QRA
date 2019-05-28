package com.example.qra.presenter;

import com.example.qra.presenter.interfaces.INavigationBarPresenter;
import com.example.qra.view.MainActivity;
import com.example.qra.view.SettingsActivity;
import com.example.qra.view.ShowAllChecksActivity;
import com.example.qra.view.interfaces.ILogOutView;

public class NavigationBarPresenter extends LogOutPresenter implements INavigationBarPresenter {

    public NavigationBarPresenter(ILogOutView view) {
        super(view);
    }

    @Override
    public void switchToMain() {
        if (!(getView() instanceof MainActivity))
            startActivity(MainActivity.class, false);
    }

    @Override
    public void switchToShowCheckList() {
        if (!(getView() instanceof ShowAllChecksActivity))
            startActivity(ShowAllChecksActivity.class, false);
    }

    @Override
    public void switchToShowAllItems() {
        if (!(getView() instanceof ShowAllChecksActivity))
            startActivity(ShowAllChecksActivity.class, false);
    }

    @Override
    public void switchToShowCategoryList() {
        if (!(getView() instanceof ShowAllChecksActivity))
            startActivity(ShowAllChecksActivity.class, false);
    }

    @Override
    public void switchToSettings() {
        if (!(getView() instanceof SettingsActivity))
            startActivity(SettingsActivity.class, false);
    }

    @Override
    public void onCreate() {
        // nothing to do
    }
}
