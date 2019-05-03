package com.example.qra.presenter;

import com.example.qra.presenter.interfaces.ILoginPresenter;

public class EnterView implements ILoginPresenter {

    public EnterView(){

    }

    @Override
    public String singIn(String password, String login) {
        //TODO send login request
        return ILoginPresenter.ENTER_SUCCESSFUL;
    }

}
