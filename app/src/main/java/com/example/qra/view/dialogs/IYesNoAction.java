package com.example.qra.view.dialogs;

import android.os.Parcelable;

@Deprecated
public interface IYesNoAction extends Parcelable {

    void runYes();

    void runNo();
}
