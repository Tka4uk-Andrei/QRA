package com.example.qra.presenter;

import com.example.qra.presenter.interfaces.IRestoreUserPresenter;
import com.example.qra.presenter.login.LogInPresenter;
import com.example.qra.view.LogInActivity;
import com.example.qra.view.interfaces.IRestorePasswordView;

// TODO doc
public class RestorePresenter extends AndroidPresenter implements IRestoreUserPresenter {

    private IRestorePasswordView restorePasswordView;

    public RestorePresenter (IRestorePasswordView restorePasswordView){
        super(restorePasswordView);
        this.restorePasswordView = restorePasswordView;
    }

    @Override
    public void restorePassword(String phone) {
        // TODO restore request

        if (phone.length() > 0) {
            restorePasswordView.showRestoreSucceeded();
            putStingExtra(LogInPresenter.STARTER_ACTIVITY, LogInPresenter.class.getName());
            putStingExtra(LogInPresenter.PHONE_EXTRA, phone);
            startActivity(LogInActivity.class, true);
        } else {
            restorePasswordView.showRestoreNotSucceeded();
        }
    }

    @Override
    public void onCreate() {
        // nothing to do
    }
}
