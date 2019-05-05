package com.example.qra.presenter;

import com.example.qra.presenter.interfaces.ILoginPresenter;

public class LogInPresenter implements ILoginPresenter {

    public LogInPresenter(){

    }

    @Override
    public String singIn(String password, String login) {
        //TODO send login request
        return ILoginPresenter.ENTER_SUCCESSFUL;
    }

}
