package com.example.qra.activities;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.qra.R;
import com.example.qra.data.QrData;
import com.example.qra.data.UserDataForFns;
import com.example.qra.data.WebRequestData;


public class WebRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_request);
        try {
            QrData targetQr = new QrData("9251440300006654","27152","1988421315");
            UserDataForFns currentUserData = new UserDataForFns("+79097984616","229963");
            new WebRequestData().getWebRequestData(targetQr, currentUserData);
        }
        catch (Exception e) {
            Toast toast = Toast.makeText(WebRequestActivity.this, e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}