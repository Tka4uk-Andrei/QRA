package com.example.qra.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qra.R;

public class RegisterInFnsActivity extends AppCompatActivity {

    private EditText telephoneText;
    private EditText passwordText;
    private Button registerBtn;

    private static final String USER_LOGIN_DATA = "USER_DATA_IN_FNS";
    private static final String PHONE_KEY = "PHONE";
    private static final String PASSWORD_KEY = "PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_in_fns);

        telephoneText = findViewById(R.id.phone_number);
        passwordText = findViewById(R.id.password);
        registerBtn = findViewById(R.id.register_btn);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(USER_LOGIN_DATA, MODE_PRIVATE);

        passwordText.setText(preferences.getString(PASSWORD_KEY, ""));
        telephoneText.setText(preferences.getString(PHONE_KEY, ""));

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO registration request
                Toast.makeText(getApplicationContext(), "Registration couldn't be started now, but I'm saving data", Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor preferencesEditor = getApplicationContext().getSharedPreferences(USER_LOGIN_DATA, MODE_PRIVATE).edit();

                preferencesEditor.putString(PASSWORD_KEY, passwordText.getText().toString());
                preferencesEditor.putString(PHONE_KEY, telephoneText.getText().toString());

                preferencesEditor.apply();

            }
        });
    }


}
