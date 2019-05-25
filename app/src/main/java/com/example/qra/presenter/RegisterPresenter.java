package com.example.qra.presenter;

import android.os.Handler;
import android.os.Message;

import com.example.qra.model.UserDataForFns;
import com.example.qra.model.webRequests.WebRequestException;
import com.example.qra.model.webRequests.requests.RegistrationWebRequest;
import com.example.qra.presenter.interfaces.IRegisterPresenter;
import com.example.qra.view.LogInActivity;
import com.example.qra.view.interfaces.IRegisterView;

public class RegisterPresenter extends AndroidPresenter implements IRegisterPresenter {

    private IRegisterView registerView;

    public RegisterPresenter(IRegisterView registerView) {
        super(registerView);
        this.registerView = registerView;
    }

    @Override
    public void registerUser(String name, String email, String phone) {
        // update registration data
        UserDataForFns.getInstance(getView().getContext()).setPhoneNumber(phone);
        UserDataForFns.getInstance(getView().getContext()).setUserEmail(email);
        UserDataForFns.getInstance(getView().getContext()).setUserName(name);
        UserDataForFns.getInstance(getView().getContext()).apply(getView().getContext());

        // send request with fail handler
        (new Thread(new RegistrationWebRequest(
                UserDataForFns.getInstance(getView().getContext()),
                new RegisterNotSucceededHandler(this),
                new RegisterSucceededHandler(this)))).start();
    }

    private void registrationSucceeded() {
        // start login activity with entered phone
        putStingExtra(LogInPresenter.STARTER_ACTIVITY, RegisterPresenter.class.getName());
        putStingExtra(LogInPresenter.PHONE_EXTRA, UserDataForFns.getInstance(getView().getContext()).getPhoneNumber());
        startActivity(LogInActivity.class, true);
    }

    private void registrationFailed(WebRequestException exception){
        registerView.showFailMessage(exception.getMessage());
    }

    @Override
    public void onCreate() {
        // nothing to do
    }


    private static class RegisterSucceededHandler extends Handler {

        private RegisterPresenter registerPresenter;

        RegisterSucceededHandler(RegisterPresenter registerPresenter) {
            this.registerPresenter = registerPresenter;
        }

        @Override
        public void handleMessage(Message msg) {
            registerPresenter.registrationSucceeded();
        }
    }


    private static class RegisterNotSucceededHandler extends Handler {

        private RegisterPresenter registerPresenter;

        RegisterNotSucceededHandler(RegisterPresenter registerPresenter) {
            this.registerPresenter = registerPresenter;
        }

        @Override
        public void handleMessage(Message msg) {
            // todo refactor
            registerPresenter.registrationFailed(msg.getData().getParcelable("exception"));
        }
    }
}
