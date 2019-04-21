package com.example.qra.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.qra.R;
import com.example.qra.data.QrData;
import com.example.qra.data.UserDataForFns;
import com.example.qra.data.WebRequestData;

import static com.example.qra.data.UserDataForFns.getInstanceDefault;


public class WebRequestActivity extends AppCompatActivity {

    public static final String JSON_DATA = "JSON_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_request);

        String response = null;
        try {
            QrData qrData = new QrData("9251440300006654","27152","1988421315");
            UserDataForFns userData = getInstanceDefault();
            response = WebRequestData.getWebRequestData(qrData, userData);
            StringBuilder a = new StringBuilder();
            a.append(response);
        }
        catch (Exception e) {
            Toast.makeText(WebRequestActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}