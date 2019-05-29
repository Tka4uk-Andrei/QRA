package com.example.qra.presenter;

import android.os.Parcel;
import android.view.View;

import com.example.qra.model.UserDataForFns;
import com.example.qra.presenter.interfaces.ILogOutPresenter;
import com.example.qra.view.LogInActivity;
import com.example.qra.view.dialogs.YesNoDialog;
import com.example.qra.view.interfaces.ILogOutView;

public class LogOutPresenter extends AndroidPresenter implements ILogOutPresenter {

    private ILogOutView view;
    private YesNoSingOutConfirm singOutConfirm;

    public LogOutPresenter(ILogOutView view) {
        super(view);

        this.view = view;
        singOutConfirm = new YesNoSingOutConfirm(this);
    }

    @Override
    public void onSingOutCall() {
        view.askUserConfirmToSingOut(singOutConfirm);
    }

    private class YesNoSingOutConfirm implements YesNoDialog.IYesNoAction {

        private LogOutPresenter presenter;

        public YesNoSingOutConfirm(LogOutPresenter settingsPresenter) {
            this.presenter = settingsPresenter;
        }

        YesNoSingOutConfirm(Parcel in) {

        }

        @Override
        public void runYes() {

            // Clear user data
            UserDataForFns.getInstance(getView().getContext()).setPhoneNumber("");
            UserDataForFns.getInstance(getView().getContext()).setUserName("");
            UserDataForFns.getInstance(getView().getContext()).setUserEmail("");
            UserDataForFns.getInstance(getView().getContext()).setPassword("");
            UserDataForFns.getInstance(view.getContext()).setLoggedInFlag(false);
            UserDataForFns.getInstance(getView().getContext()).apply(getView().getContext());

            putStingExtra(LogInPresenter.STARTER_ACTIVITY, View.class.getName());
            startActivity(LogInActivity.class, true);
        }

        @Override
        public void runNo() {
            // nothing to do
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }

        public final Creator<YesNoSingOutConfirm> CREATOR = new Creator<YesNoSingOutConfirm>() {

            @Override
            public YesNoSingOutConfirm createFromParcel(Parcel in) {
                return new YesNoSingOutConfirm(in);
            }

            @Override
            public YesNoSingOutConfirm[] newArray(int size) {
                return new YesNoSingOutConfirm[size];
            }
        };
    }

    ;
}
