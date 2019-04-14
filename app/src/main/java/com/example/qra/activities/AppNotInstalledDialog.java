package com.example.qra.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

public class AppNotInstalledDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder alertDialoBuilder = new AlertDialog.Builder(getActivity());

        return alertDialoBuilder
                .setTitle("Test")
                .setMessage("У вас нет приложения для считывания QR кода. Скачать его с Google Play?")
                .setPositiveButton("Да", null)
                .setNegativeButton("Нет", null)
                .create();
    }


}
