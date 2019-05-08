package com.example.qra.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.qra.R;

public class RestorePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_password);

        EditText phoneNumber = findViewById(R.id.phone_number);
        Button restoreBtn = findViewById(R.id.restore_password_btn);



        restoreBtn.setOnClickListener(action -> {

        });
    }
}
