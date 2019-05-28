package com.example.qra.presenter.interfaces;

public interface INavigationBarPresenter extends IPresenter, ILogOutPresenter {

    void switchToMain();
    void switchToShowCheckList();
    void switchToShowAllItems();
    void switchToShowCategoryList();
    void switchToSettings();

}
