package com.example.qra.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.qra.CheckDataBase;
import com.example.qra.R;
import com.example.qra.model.check.BoughtItem;
import com.example.qra.model.check.CheckInformationStorage;

public class EditGoodsDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goods_data);

        ListView listView = findViewById(R.id.choose_check);
        final CheckInformationStorage checkList[] = CheckDataBase.getCheckList(getApplicationContext());
        final String checkBuyTimes[] = new String[checkList.length];
        for (int i = 0; i < checkList.length; i++) {
          checkBuyTimes[i] = checkList[i].getBuyTime();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, checkBuyTimes);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), EditCheckDataActivity.class);
            intent.putExtra("checkNumber", i);
            startActivity(intent);
        });

        Button addCheckButton=findViewById(R.id.add_check_btn);
        addCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckInformationStorage newCheck = new CheckInformationStorage.Builder()
                        .setTotalSum(0)
                        .setObtainingMethod("user")
                        .build();

                BoughtItem[] items = new BoughtItem[1];
                items[0] = new BoughtItem.Builder()
                        .setName("milk")
                        .setQuantity(2)
                        .build();
                newCheck.setShoppingList(items);
                CheckDataBase.insert(newCheck, getApplicationContext());

                //Intent intent = new Intent(getApplicationContext(), EditCheckDataActivity.class);
                //intent.putExtra("checkNumber", checkList.length);
                //startActivity(intent);
            }
        });
    }

}
