package com.example.qra.presenter;

import android.os.Parcel;

import com.example.qra.presenter.interfaces.ISettingsPresenter;
import com.example.qra.view.LogInActivity;
import com.example.qra.view.dialogs.IYesNoAction;
import com.example.qra.view.interfaces.ISettingsView;

public class SettingsPresenter extends AndroidPresenter implements ISettingsPresenter {

    private ISettingsView view;
    private YesNoSingOutConfirm singOutConfirm;

    public SettingsPresenter (ISettingsView view){
        super(view);
        this.view = view;
        singOutConfirm = new YesNoSingOutConfirm(this);
    }

    @Override
    public void singOut() {
        putStingExtra(LogInPresenter.STARTER_ACTIVITY, SettingsPresenter.class.getName());
        startActivity(LogInActivity.class, true);
    }

    @Override
    public void singOutBtnPressed() {
        view.askUserConfirmToSingOut(singOutConfirm);
    }

    @Override
    public void onCreate() {
        // nothing to do
    }

    class YesNoSingOutConfirm implements IYesNoAction {

        private SettingsPresenter presenter;

        public YesNoSingOutConfirm(SettingsPresenter settingsPresenter){
            this.presenter = settingsPresenter;
        }

        YesNoSingOutConfirm(Parcel in) {

        }

        @Override
        public void runYes() {
            presenter.singOut();
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
    };
}
