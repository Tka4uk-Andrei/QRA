package com.example.qra.view.interfaces;

import com.example.qra.view.dialogs.IYesNoAction;

public interface ISettingsView extends IView {

    void askUserConfirmToSingOut(IYesNoAction action);

}
