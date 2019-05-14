package com.example.qra.view.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

// TODO documentation
public class YesNoDialog extends DialogFragment {

    private static final String MSG_KEY = "message";
    private static final String ACT_KEY = "actions";

    public static YesNoDialog getInstance(String message, IYesNoAction action){
        // wrap into bundle what we want to send
        Bundle arguments = new Bundle();
        arguments.putString(MSG_KEY, message);
        arguments.putParcelable(ACT_KEY, action);

        // create dialog
        YesNoDialog dialog = new YesNoDialog();
        dialog.setArguments(arguments);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        IYesNoAction action = getArguments().getParcelable(ACT_KEY);

        if (action == null)
            throw new NullPointerException("IYesNoAction is null");

        return alertDialogBuilder
                .setTitle("Test")
                .setMessage(getArguments().getString(MSG_KEY))
                .setPositiveButton("Да", (dialog, which) -> action.runYes())
                .setNegativeButton("Нет", (dialog, which) -> action.runNo())
                .create();
    }
}
