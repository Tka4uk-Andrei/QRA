package com.example.qra.presenter;

import android.os.Handler;
import android.os.Message;

import com.example.qra.model.UserDataForFns;
import com.example.qra.model.webRequests.requests.RegistrationWebRequest;
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
        // update credentials
        UserDataForFns.getInstance(getView().getContext()).setPhoneNumber(phone);
        UserDataForFns.getInstance(getView().getContext()).apply(getView().getContext());

        (new Thread(new RegistrationWebRequest(UserDataForFns.getInstance(getView().getContext()),
                new RestoreNotSucceededHandler(this),
                new RestoreSucceededHandler(this)))).start();
    }

    private void restoreSucceeded() {
        restorePasswordView.showRestoreSucceeded();
        putStingExtra(LogInPresenter.STARTER_ACTIVITY, LogInPresenter.class.getName());
        putStingExtra(LogInPresenter.PHONE_EXTRA, UserDataForFns.getInstance(getView().getContext()).getPhoneNumber());
        startActivity(LogInActivity.class, true);
    }

    private void restoreFailed(){
        restorePasswordView.showRestoreNotSucceeded();
    }

    @Override
    public void onCreate() {
        // nothing to do
    }

    private static class RestoreSucceededHandler extends Handler {

        private RestorePresenter restorePresenter;

        RestoreSucceededHandler(RestorePresenter restorePresenter) {
            this.restorePresenter = restorePresenter;
        }

        @Override
        public void handleMessage(Message msg) {
            restorePresenter.restoreSucceeded();
        }
    }


    private static class RestoreNotSucceededHandler extends Handler {

        private RestorePresenter restorePresenter;

        RestoreNotSucceededHandler(RestorePresenter restorePresenter) {
            this.restorePresenter = restorePresenter;
        }

        @Override
        public void handleMessage(Message msg) {
            restorePresenter.restoreFailed();
        }
    }
}
