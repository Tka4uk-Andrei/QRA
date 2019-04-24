package com.example.qra.presenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qra.R;
import com.example.qra.model.UserDataForFns;

public class RegisterInFnsActivity extends AppCompatActivity {

    private EditText telephoneText;
    private EditText passwordText;
    private EditText userNameText;
    private EditText emailText;

    private Button registerBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_in_fns);

        telephoneText = findViewById(R.id.phone_number);
        passwordText = findViewById(R.id.password);
        userNameText = findViewById(R.id.user_name);
        emailText = findViewById(R.id.email);

        registerBtn = findViewById(R.id.register_btn);

        passwordText.setText(UserDataForFns.getInstance(getApplicationContext()).getPassword());
        telephoneText.setText(UserDataForFns.getInstance(null).getPhoneNumber());
        userNameText.setText(UserDataForFns.getInstance(null).getUserName());
        emailText.setText(UserDataForFns.getInstance(null).getUserEmail());

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO registration request
                Toast.makeText(getApplicationContext(), "Registration couldn't be started now, but I'm saving data", Toast.LENGTH_SHORT).show();

                UserDataForFns.getInstance(null).setPassword(passwordText.getText().toString());
                UserDataForFns.getInstance(null).setPhoneNumber(telephoneText.getText().toString());
                UserDataForFns.getInstance(null).setUserEmail(emailText.getText().toString());
                UserDataForFns.getInstance(null).setUserName(userNameText.getText().toString());
                UserDataForFns.getInstance(null).apply(getApplicationContext());
            }
        });
    }


}
