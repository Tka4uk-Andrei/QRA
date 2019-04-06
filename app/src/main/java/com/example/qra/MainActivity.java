package com.example.qra;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * MainActivity class using for display main info
 */
public class MainActivity extends AppCompatActivity {

    private Button qrScanButton;
    private Button jsonButton;
    private Button requestButton;
    private Button settingsButton;


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

        class MenuBtnClickListener implements View.OnClickListener{
            @Override
            public void onClick(View v) {
                if (v == qrScanButton) {
                    Toast.makeText(getApplicationContext(), "QR scan button pressed",
                            Toast.LENGTH_SHORT).show();
                } else if (v == jsonButton) {
                    Toast.makeText(getApplicationContext(), "Json recognition button pressed",
                            Toast.LENGTH_SHORT).show();
                } else if (v == requestButton) {
                    Toast.makeText(getApplicationContext(), "Send request button pressed",
                            Toast.LENGTH_SHORT).show();
                } else if (v == settingsButton) {
                    Toast.makeText(getApplicationContext(), "Settings button pressed",
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
}
