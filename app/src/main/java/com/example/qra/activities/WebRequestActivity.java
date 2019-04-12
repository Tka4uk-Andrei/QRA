package com.example.qra.activities;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.qra.R;
import com.example.qra.data.WebRequestData;


public class WebRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_request);
        new WebRequestData().getWebRequestData();
    }

}