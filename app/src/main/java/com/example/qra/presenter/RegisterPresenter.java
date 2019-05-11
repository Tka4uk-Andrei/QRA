package com.example.qra.presenter;

import com.example.qra.model.UserDataForFns;
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

        // TODO request
        // if succeeded
        if (name.length() != 0 && email.length() != 0
                && phone.length() != 0) {

            // save data in cache
            UserDataForFns.getInstance(registerView.getContext()).setPhoneNumber(phone);
            UserDataForFns.getInstance(null).setUserEmail(email);
            UserDataForFns.getInstance(null).setUserName(name);
            UserDataForFns.getInstance(null).apply(registerView.getContext());

            // start login activity
            putStingExtra(LogInPresenter.STARTER_ACTIVITY, RegisterPresenter.class.getName());
            putStingExtra(LogInPresenter.PHONE_EXTRA, phone);
            startActivity(LogInActivity.class, true);
        }
    }

    @Override
    public void onCreate() {
        // nothing to do
    }
}
