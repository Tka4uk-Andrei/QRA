package com.example.qra.presenter.interfaces;

public interface IShowAllChecksPresenter extends IPresenter {
    /**
     * Method that add new check \\
     */
    void addCheck();

    /**
     * @return names of all user's checks \\
     */
    String[] getCheckList();
}
