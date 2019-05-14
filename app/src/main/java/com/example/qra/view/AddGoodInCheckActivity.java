package com.example.qra.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qra.CheckDataBase;
import com.example.qra.CheckEditingException;
import com.example.qra.R;
import com.example.qra.model.check.BoughtItem;
import com.example.qra.model.check.CheckInformationStorage;

public class AddGoodInCheckActivity extends AppCompatActivity {

    private BoughtItem boughtItems[];
    private CheckInformationStorage check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_good_in_check);

        EditText nameTxt = findViewById(R.id.name_text);
        EditText quantityTxt = findViewById(R.id.quantity_text);
        EditText priceTxt = findViewById(R.id.price_text);
        Button CreateGoodBtn = findViewById(R.id.create_good_btn);

        Intent intent = getIntent();
        int position = intent.getIntExtra("checkNumber", 0);

        final CheckInformationStorage checkList[] = CheckDataBase.getCheckList(getApplicationContext());
        check = checkList[position];
        boughtItems = check.getShoppingList();

        CreateGoodBtn.setOnClickListener(action -> {

            BoughtItem[] newList = new BoughtItem[boughtItems.length + 1];
            for (int i = 0; i < boughtItems.length; i++) {
                newList[i] = boughtItems[i];
            }
            BoughtItem newItem = new BoughtItem.Builder()
                    .setName(nameTxt.getText().toString())
                    .setNameForUser(nameTxt.getText().toString())
                    .setQuantity(Integer.parseInt(quantityTxt.getText().toString()))
                    .setPrice(Integer.parseInt(priceTxt.getText().toString()))
                    .build();
            newList[newList.length - 1] = newItem;
            check.setShoppingList(newList);
            try {
                CheckDataBase.updateAllPositionUserCheck(getApplicationContext(), check);
            } catch (CheckEditingException e) {
                Toast.makeText(AddGoodInCheckActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            Intent newIntent = new Intent(getApplicationContext(), EditCheckDataActivity.class);
            startActivity(newIntent);

        });
    }
}