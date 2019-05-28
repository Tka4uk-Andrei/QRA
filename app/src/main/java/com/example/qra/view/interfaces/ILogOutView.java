package com.example.qra.view.interfaces;

import com.example.qra.view.dialogs.YesNoDialog;

public interface ILogOutView extends IView {

    void askUserConfirmToSingOut(YesNoDialog.IYesNoAction action);

}
