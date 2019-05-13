package com.example.qra.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qra.R;
import com.example.qra.model.UserDataForFns;
import com.example.qra.model.qrCode.QrData;
import com.example.qra.model.webRequests.WebRequestException;
import com.example.qra.model.webRequests.WebRequests;
import com.example.qra.model.webRequests.requests.CheckExistsWebRequest;
import com.example.qra.model.webRequests.requests.GetCheckDataWebRequest;
// todo refactor

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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest(new QrData(
                        fiscalNum.getText().toString(),
                        fiscalDoc.getText().toString(),
                        fiscalSign.getText().toString()));
            }
        });

        String qrDataStr = getIntent().getStringExtra(MainActivity.QR_DATA_EXTRA);
        if (qrDataStr == null)
            return;

        sendRequest(new QrData(qrDataStr));
//        try {
//            qrDataStr = WebRequestSender.getWebRequestData(new QrData(qrDataStr), getInstanceDefault());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        int y = 0;
//        String response = data.getStringExtra("JSON_DATA");
//        Intent intent = new Intent(this, ShowCheckInfoActivity.class);
//        intent.putExtra(JSON_DATA, response);
//        showNotiication(this,intent); // там
//        startActivity(intent);
//
//
//        Button btn = findViewById(R.id.send_btn);
//        EditText fiscalNum = findViewById(R.id.fiscal_num);
//        EditText fiscalDoc = findViewById(R.id.fiscal_doc);
//        EditText fiscalSign = findViewById(R.id.fiscal_sign);
//        EditText typeOfFiscalDoc = findViewById(R.id.type_of_fiscal_document);
//        EditText buyTime = findViewById(R.id.buy_time);
//        EditText totalCheckSum = findViewById(R.id.total_check_sum);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendRequest(new QrData(
//                        fiscalNum.getText().toString(),
//                        fiscalDoc.getText().toString(),
//                        fiscalSign.getText().toString(),
//                        totalCheckSum.getText().toString(),
//                        typeOfFiscalDoc.getText().toString(),
//                        buyTime.getText().toString()));
//            }
//        });
//
//        String qrDataStr = getIntent().getStringExtra(MainActivity.QR_DATA_EXTRA);
//        if (qrDataStr == null)
//            return;
//
//        sendRequest(new QrData(qrDataStr));
//    }

    private void sendRequest(QrData qrData) {
        (new Thread(new CheckExistsWebRequest(qrData,
                new CheckExistingExceptionHandler(this),
                new CheckExistingReturnHandler(this, qrData)))).start();
    }

<<<<<<< HEAD
    private void showQuickMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void getCheck(QrData qrData) {
        (new Thread(new GetCheckDataWebRequest(qrData, UserDataForFns.getInstance(this),
                new CheckExistingExceptionHandler(this),
                new GetCheckReturnHandler(this)))).start();
    }

    private void showCheck(String str) {
=======
        String response = null;
        try {
            if(WebRequests.isCheckExistsWebRequest(qrData)) {
                response = WebRequests.getCheckDataWebRequest(qrData, UserDataForFns.getInstanceDefault());
            }
        } catch (Exception e) {
            Toast.makeText(WebRequestActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
>>>>>>> develop
        Intent intent = new Intent(getApplicationContext(), ShowCheckInfoActivity.class);
        if (str != null) {
            intent.putExtra(JSON_DATA, str);
            startActivity(intent);
        }
<<<<<<< HEAD
    }

    class CheckExistingExceptionHandler extends Handler {

        private WebRequestActivity activity;

        public CheckExistingExceptionHandler(WebRequestActivity activity) {
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String str = ((WebRequestException) bundle.getParcelable("exception")).getMessage();
            activity.showQuickMessage(str);
        }
    }

    class CheckExistingReturnHandler extends Handler {

        private WebRequestActivity activity;
        private QrData qrData;

        public CheckExistingReturnHandler(WebRequestActivity activity, QrData qrData) {
            this.activity = activity;
            this.qrData = qrData;
        }

        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            boolean flag = bundle.getBoolean("return");

            if (flag) {
                activity.getCheck(qrData);
            } else {
                activity.showQuickMessage("Check doesn\'t exist");
            }
        }
    }

    class GetCheckReturnHandler extends Handler {

        private WebRequestActivity activity;

        public GetCheckReturnHandler(WebRequestActivity activity) {
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String str = bundle.getString("return");

            activity.showCheck(str);
        }
=======
>>>>>>> develop
    }
}