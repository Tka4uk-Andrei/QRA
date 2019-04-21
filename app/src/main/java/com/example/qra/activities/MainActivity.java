package com.example.qra.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
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

    public static final String QR_DATA_EXTRA = "QR_DATA";

    private static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private static final String FIRST_TIME_RUN = "First time";
    private static final String IS_FIRST_TIME = "is first";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences firstTimePreference = getApplicationContext().getSharedPreferences(FIRST_TIME_RUN, MODE_PRIVATE);
        boolean isFirstLaunch = firstTimePreference.getBoolean(IS_FIRST_TIME, true);

        if (isFirstLaunch) {
            Toast.makeText(getApplicationContext(), "First Launch",
                    Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = firstTimePreference.edit();
            editor.putBoolean(IS_FIRST_TIME, false);
            editor.apply();
        } else {
            Toast.makeText(getApplicationContext(), "I was launched previously",
                    Toast.LENGTH_SHORT).show();
        }

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
        showCheckButton = findViewById(R.id.show_check_btn);
    }

    /**
     * Set listeners to views \\
     * Author: Andrey Tkachuk
     */
    public void setViewListeners() {

        qrScanButton.setOnClickListener(menuBtnClickListener);
        jsonButton.setOnClickListener(menuBtnClickListener);
        requestButton.setOnClickListener(menuBtnClickListener);
        settingsButton.setOnClickListener(menuBtnClickListener);
        showCheckButton.setOnClickListener(menuBtnClickListener);

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

                Intent webRequestIntent = new Intent(getApplicationContext(), WebRequestActivity.class);
                webRequestIntent.putExtra(QR_DATA_EXTRA, intent.getStringExtra("SCAN_RESULT"));
                startActivity(webRequestIntent);
            }
        }
    }

    private Button qrScanButton;
    private Button jsonButton;
    private Button requestButton;
    private Button settingsButton;
    private Button showCheckButton;

    private View.OnClickListener menuBtnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = null;

            // TODO move texts to resources
            if (v == qrScanButton) {
                try {
                    intent = new Intent(ACTION_SCAN);
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 0);
                } catch (ActivityNotFoundException exception) {
                    (new AppNotInstalledDialog()).show(getSupportFragmentManager(), "APP_NOT_INSTALLED");
                } catch (Exception exception) {
                    (new AppNotInstalledDialog()).show(getSupportFragmentManager(), "APP_NOT_INSTALLED");
                }
            } else {
                if (v == jsonButton) {
                    intent = new Intent(getApplicationContext(), JsonParseActivity.class);
                } else if (v == requestButton) {
                    Toast.makeText(getApplicationContext(), "Send request button pressed",
                            Toast.LENGTH_SHORT).show();
                } else if (v == settingsButton) {
                    //TODO switch to settings activity
                    intent = new Intent(getApplicationContext(), RegisterInFnsActivity.class);
                } else if (v == showCheckButton) {
                    intent = new Intent(getApplicationContext(), ShowCheckInfoActivity.class);
                }

                if (intent != null)
                    startActivity(intent);
                else
                    Toast.makeText(getApplicationContext(), "ERROR. Don't know what activity i need to start",
                            Toast.LENGTH_SHORT).show();
            }
        }
    };

}
