package com.example.qra.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qra.R;
import com.example.qra.presenter.CreateCheckItemPresenter;
import com.example.qra.view.interfaces.ICreateCheckItemView;

public class CreateCheckItemActivity extends AppCompatActivity implements ICreateCheckItemView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_check_item);

        EditText nameTxt = findViewById(R.id.name_text);
        EditText categoryTxt = findViewById(R.id.category_text);
        EditText quantityTxt = findViewById(R.id.quantity_text);
        EditText priceTxt = findViewById(R.id.price_text);
        Button CreateGoodBtn = findViewById(R.id.create_good_btn);

        Intent intent = getIntent();

        CreateCheckItemPresenter presenter = new CreateCheckItemPresenter(this);
        presenter.onCreate();

        CreateGoodBtn.setOnClickListener(action -> {

            try {
                presenter.addItem(intent.getIntExtra("checkNumber", 0),
                        nameTxt.getText().toString(),
                        categoryTxt.getText().toString(),
                        quantityTxt.getText().toString(),
                        priceTxt.getText().toString());
                finish();
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public Intent getStarterIntent() {
        return getIntent();
    }

}
