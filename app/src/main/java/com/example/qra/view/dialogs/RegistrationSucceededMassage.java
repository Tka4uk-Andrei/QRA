package com.example.qra.view.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

public class RegistrationSucceededMassage extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setMessage("Регистрация прошла успешно. Вам выслан СМС с паролем")
                .setNeutralButton("Ok", null)
                .create();
    }
}
