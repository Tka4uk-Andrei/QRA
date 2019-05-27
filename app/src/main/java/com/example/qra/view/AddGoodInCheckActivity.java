package com.example.qra.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qra.CheckDataBase;
import com.example.qra.CheckEditingException;
import com.example.qra.R;
import com.example.qra.model.check.CheckItem;
import com.example.qra.model.check.CheckInformationStorage;

/**
 * Class that allows to set new item \\
 *
 * @autor : Ekaterina Novoselova
 */
public class AddGoodInCheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_good_in_check);

        CheckItem checkItems[];
        CheckInformationStorage check;

        EditText nameTxt = findViewById(R.id.name_text);
        EditText quantityTxt = findViewById(R.id.quantity_text);
        EditText priceTxt = findViewById(R.id.price_text);
        Button CreateGoodBtn = findViewById(R.id.create_good_btn);

        Intent intent = getIntent();
        int position = intent.getIntExtra("checkNumber", 0);

        final CheckInformationStorage checkList[] = CheckDataBase.getCheckList(getApplicationContext());
        check = checkList[position];
        checkItems = check.getShoppingList();

        CreateGoodBtn.setOnClickListener(action -> {

            CheckItem[] newList = new CheckItem[checkItems.length + 1];
            for (int i = 0; i < checkItems.length; i++) {
                newList[i] = checkItems[i];
            }
            CheckItem newItem = new CheckItem.Builder()
                    .setName(nameTxt.getText().toString())
                    .setNameForUser(nameTxt.getText().toString())
                    .setQuantity(Integer.parseInt(quantityTxt.getText().toString()))
                    .setPrice(Integer.parseInt(priceTxt.getText().toString()))
                    .build();

            try {
                newItem = CheckDataBase.addItem(check.getId(),newItem,getApplicationContext());
                newList[newList.length - 1] = newItem;
                check.setShoppingList(newList);
                CheckDataBase.updateAllPositionUserCheck(getApplicationContext(), check);
            } catch (CheckEditingException e) {
                Toast.makeText(AddGoodInCheckActivity.this, e.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
            finish();

        });
    }
}