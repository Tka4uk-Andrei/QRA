package com.example.qra.presenter.interfaces;

public interface ILoginPresenter {

    String ENTER_UNSUCCESSFUL = "wrong login or password";
    String ENTER_SUCCESSFUL = "ok";

    String singIn(String password, String login);

}
