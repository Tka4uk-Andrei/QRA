package com.example.qra.view.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

@Deprecated
public class RestoreNotSucceededMessage extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setMessage("Телефонный номер не зарегистрирован в ФНС")
                .setNeutralButton("Ok", null)
                .create();
    }
}
