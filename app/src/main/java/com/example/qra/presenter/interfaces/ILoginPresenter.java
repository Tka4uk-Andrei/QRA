package com.example.qra.presenter.interfaces;

import android.content.Context;

public interface ILoginPresenter extends IPresenter {

    /**
     * Method that sing user in fns and save info in cache
     *
     * @param password password for FNS
     * @param login    phone number for FNS
     */
    void singUp(String password, String login);

    void register();

    void restorePassword();

}
