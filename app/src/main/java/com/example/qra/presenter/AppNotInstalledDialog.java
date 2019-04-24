package com.example.qra.presenter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;

public class AppNotInstalledDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        return alertDialogBuilder
                .setTitle("Test")
                .setMessage("У вас нет приложения для считывания QR кода. Скачать его с Google Play?")
                .setPositiveButton("Да", PositiveBtnClickListener)
                .setNegativeButton("Нет", null)
                .create();
    }

    private DialogInterface.OnClickListener PositiveBtnClickListener = (dialog, which) -> {
        final String URL = "https://play.google.com/store/search?q=com.google.zxing.client.android&c=apps";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
    };
}
