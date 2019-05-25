package com.example.qra.presenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;

import com.example.qra.view.interfaces.IView;

import java.util.Stack;

public class AndroidPresenter {

    private IView view;

    private Stack<Pair<String, String>> intentExtraStrings;

    public AndroidPresenter(IView view) {
        this.view = view;
        intentExtraStrings = new Stack<>();
    }

    protected void setView(IView view) {
        this.view = view;
    }

    protected IView getView() {
        return view;
    }

    void putStingExtra(String key, String value) {
        intentExtraStrings.push(new Pair<>(key, value));
    }

    private Intent formStarterIntent(Class activityName){
        if (!(view instanceof AppCompatActivity)) {
            throw new ClassCastException("view field, that send from constructor " +
                    "should be AppCompatActivity class");
        }
        Intent intent = new Intent(view.getContext(), activityName);

        // put extras
        while (!intentExtraStrings.empty()) {
            intent.putExtra(intentExtraStrings.peek().first, intentExtraStrings.peek().second);
            intentExtraStrings.pop();
        }

        return intent;
    }

    // TODO documentation
    protected void startActivity(Class activityName, boolean finishFlag) {

        ((AppCompatActivity) view).startActivity(formStarterIntent(activityName));

        // if we want exclude return to this presenter
        if (finishFlag)
            ((AppCompatActivity) view).finish();
    }

    // TODO documentation
    protected void startActivityForResult(Intent intent, int requestCode){
        ((AppCompatActivity) view).startActivityForResult(intent, requestCode);
    }
}
