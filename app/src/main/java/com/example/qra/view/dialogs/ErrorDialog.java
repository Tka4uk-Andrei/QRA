package com.example.qra.view.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

/**
 * Class for showing errors
 */
@Deprecated
public class ErrorDialog extends DialogFragment {

    private String str = "exception called";

    public void setMsg(String msg){
        str = msg;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        return alertDialogBuilder
                .setTitle("Exception")
                .setMessage(str)
                .setNeutralButton("Ok", null)
                .create();
    }
}
