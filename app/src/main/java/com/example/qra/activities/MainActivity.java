package com.example.qra.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.qra.R;

/**
 * MainActivity class using for display main info
 */
public class MainActivity extends AppCompatActivity {

    private Button qrScanButton;
    private Button jsonButton;
    private Button requestButton;
    private Button settingsButton;

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectViewsWithCode();
        setViewListeners();

    }

    /**
     * Find views and establish links between code and view \\
     * Author: Andrey Tkachuk
     */
    public void ConnectViewsWithCode() {
        qrScanButton = findViewById(R.id.qr_scan_btn);
        jsonButton = findViewById(R.id.json_parse_btn);
        requestButton = findViewById(R.id.send_request_btn);
        settingsButton = findViewById(R.id.settings_btn);
    }

    /**
     * Set listeners to views \\
     * Author: Andrey Tkachuk
     */
    public void setViewListeners() {

        class MenuBtnClickListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                Intent intent = null;

                if (v == qrScanButton) {
                    try {
                        intent = new Intent(ACTION_SCAN);
                        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                        startActivityForResult(intent, 0);
                    } catch (ActivityNotFoundException exception) {
                        (new AppNotInstalledDialog()).show(getSupportFragmentManager(), "custom");
                    }
                } else {
                    if (v == jsonButton) {
                        intent = new Intent(getApplicationContext(), JsonParseActivity.class);
                    } else if (v == requestButton) {
                        Toast.makeText(getApplicationContext(), "Send request button pressed",
                                Toast.LENGTH_SHORT).show();
                    } else if (v == settingsButton) {
                        Toast.makeText(getApplicationContext(), "Settings button pressed",
                                Toast.LENGTH_SHORT).show();
                    }
                    if (intent != null)
                        startActivity(intent);
                    else
                        Toast.makeText(getApplicationContext(), "ERROR. Don't know what activity i need to start",
                                Toast.LENGTH_SHORT).show();
                }
            }
        }
        MenuBtnClickListener menuBtnClickListener = new MenuBtnClickListener();

        qrScanButton.setOnClickListener(menuBtnClickListener);
        jsonButton.setOnClickListener(menuBtnClickListener);
        requestButton.setOnClickListener(menuBtnClickListener);
        settingsButton.setOnClickListener(menuBtnClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {

                // Получаем данные после работы сканера и выводим их в Toast сообщении:
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast toast = Toast.makeText(this, "Содержание: " + contents + " Формат: " + format, Toast.LENGTH_LONG);
                toast.show();

                // TODO switch to request
            }
        }
    }
}
