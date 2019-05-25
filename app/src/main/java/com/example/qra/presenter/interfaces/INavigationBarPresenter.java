package com.example.qra.presenter.interfaces;

public interface INavigationBarPresenter extends IPresenter {

    void switchToMain();
    void switchToShowCheckList();
    void switchToShowAllItems();
    void switchToShowCategoryList();
    void switchToSettings();
    void logOut();

}
