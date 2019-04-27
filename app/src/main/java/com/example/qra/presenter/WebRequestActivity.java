package com.example.qra.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qra.R;
import com.example.qra.model.qrCode.QrData;
import com.example.qra.model.webRequests.WebRequestSender;

import static com.example.qra.model.UserDataForFns.getInstanceDefault;


public class WebRequestActivity extends AppCompatActivity {

    public static final String JSON_DATA = "JSON_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_request);

        Button btn = findViewById(R.id.send_btn);
        EditText fiscalNum = findViewById(R.id.fiscal_num);
        EditText fiscalDoc = findViewById(R.id.fiscal_doc);
        EditText fiscalSign = findViewById(R.id.fiscal_sign);
        EditText typeOfFiscalDoc = findViewById(R.id.type_of_fiscal_document);
        EditText buyTime = findViewById(R.id.buy_time);
        EditText totalCheckSum = findViewById(R.id.total_check_sum);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest(new QrData(
                        fiscalNum.getText().toString(),
                        fiscalDoc.getText().toString(),
                        fiscalSign.getText().toString(),
                        totalCheckSum.getText().toString(),
                        typeOfFiscalDoc.getText().toString(),
                        buyTime.getText().toString()));
            }
        });

        String qrDataStr = getIntent().getStringExtra(MainActivity.QR_DATA_EXTRA);
        if (qrDataStr == null)
            return;

        sendRequest(new QrData(qrDataStr));
    }

    private void sendRequest(QrData qrData) {

        String response = null;
        try {
            //WebRequestSender.registrationWebRequest(getInstanceDefault());
            if(WebRequestSender.checkExistingWebRequestData(qrData)) {
                response = WebRequestSender.getWebRequestData(qrData, getInstanceDefault());
            }
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