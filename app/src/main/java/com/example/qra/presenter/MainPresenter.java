package com.example.qra.presenter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;

import com.example.qra.presenter.interfaces.IMainPresenter;
import com.example.qra.view.WebRequestActivity;
import com.example.qra.view.interfaces.IMainView;

public class MainPresenter extends AndroidPresenter implements IMainPresenter {

    public static final String QR_DATA_EXTRA = "QR_DATA";

    private static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private static final int QR_SCAN_REQUEST_CODE = 0;

    private IMainView view;

    public MainPresenter(IMainView view) {
        super(view);
        this.view = view;
    }

    @Override
    public void switchToScanQr() {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, QR_SCAN_REQUEST_CODE);
        } catch (ActivityNotFoundException exception) {
            view.showAppNotInstalled();
        } catch (Exception exception) {
            view.showAppNotInstalled();
        }
    }

    @Override
    public void onReturnResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == Activity.RESULT_OK) {
            // if return from qr scanning
            if (requestCode == QR_SCAN_REQUEST_CODE) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                putStingExtra(QR_DATA_EXTRA, contents);
                startActivity(WebRequestActivity.class, false);
            }
        } else {
            startActivity(WebRequestActivity.class, false);
        }
    }

    @Override
    public void onCreate() {
        // noting to do
    }
}
