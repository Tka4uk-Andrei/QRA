package com.example.qra.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.qra.R;
import com.example.qra.data.QrData;
import com.example.qra.data.UserDataForFns;
import com.example.qra.data.WebRequestSender;

import static com.example.qra.data.UserDataForFns.getInstanceDefault;


public class WebRequestActivity extends AppCompatActivity {

    public static final String JSON_DATA = "JSON_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_request);

        Intent previousIntent = getIntent();
        QrData qrData = new QrData(previousIntent.getStringExtra(MainActivity.QR_DATA_EXTRA));

        String response = null;
        try {
            UserDataForFns userData = getInstanceDefault();
            response = WebRequestSender.getWebRequestData(qrData, userData);
        } catch (Exception e) {
            Toast.makeText(WebRequestActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(getApplicationContext(), ShowCheckInfoActivity.class);
        if (response != null) {
            intent.putExtra(JSON_DATA, response);
            startActivity(intent);
        }

    }

}