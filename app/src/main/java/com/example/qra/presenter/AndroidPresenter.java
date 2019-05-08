package com.example.qra.presenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.example.qra.view.interfaces.IView;

public class AndroidPresenter {

    private IView view;

    public AndroidPresenter (IView loginView){
        this.view = loginView;
    }

    protected void setView(IView view){
        this.view = view;
    }

    protected IView getView() {
        return view;
    }

    // TODO documentation
    protected void startActivity(Class activityName, boolean finishFlag) {
        if (!(view instanceof AppCompatActivity)) {
            throw new ClassCastException("view field, that send from constructor " +
                    "should be AppCompatActivity class");
        }

        ((AppCompatActivity) view).startActivity(new Intent(view.getContext(), activityName));
        if (finishFlag)
            ((AppCompatActivity) view).finish();
    }
}
