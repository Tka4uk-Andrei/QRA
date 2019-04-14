package com.example.qra.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qra.R;

public class RegisterInFnsActivity extends AppCompatActivity {

    private EditText telephoneText;
    private EditText passwordText;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_in_fns);

        telephoneText = findViewById(R.id.phone_number);
        passwordText = findViewById(R.id.password);
        registerBtn = findViewById(R.id.register_btn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO registration request
                Toast.makeText(getApplicationContext(), "Registration couldn't be started now, but I'm saving data", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
