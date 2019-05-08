package com.example.qra.presenter;

import com.example.qra.presenter.interfaces.IRestoreUserPresenter;
import com.example.qra.view.interfaces.IRestorePasswordView;

// TODO doc
public class RestorePresenter extends AndroidPresenter implements IRestoreUserPresenter {

    IRestorePasswordView restorePasswordView;

    public RestorePresenter (IRestorePasswordView restorePasswordView){
        super(restorePasswordView);
        this.restorePasswordView = restorePasswordView;
    }

    @Override
    public void restorePassword(String phone) {
        // TODO restore request

        if (true) {
            restorePasswordView.showRestoreSucceeded();
            startActivity(, false);
        } else {
            restorePasswordView.showRestoreNotSucceeded();
        }
    }
}
