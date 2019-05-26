package com.example.qra.presenter.interfaces;

import android.content.Intent;

public interface IMainPresenter extends IPresenter {

    void switchToScanQr();

    void onReturnResult(int requestCode, int resultCode, Intent intent);
}
