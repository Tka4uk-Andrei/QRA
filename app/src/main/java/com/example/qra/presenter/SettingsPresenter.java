package com.example.qra.presenter;

import android.os.Parcel;

import com.example.qra.presenter.interfaces.ISettingsPresenter;
import com.example.qra.view.LogInActivity;
import com.example.qra.view.dialogs.YesNoDialog;
import com.example.qra.view.interfaces.ISettingsView;

public class SettingsPresenter extends LogOutPresenter implements ISettingsPresenter {

    private ISettingsView view;

    public SettingsPresenter (ISettingsView view){
        super(view);
        this.view = view;
    }

    @Override
    public void onCreate() {
        // nothing to do
    }
}
