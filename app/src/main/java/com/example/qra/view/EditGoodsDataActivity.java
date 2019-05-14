package com.example.qra.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.qra.CheckDataBase;
import com.example.qra.R;
import com.example.qra.model.check.CheckInformationStorage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditGoodsDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goods_data);

        ListView listView = findViewById(R.id.choose_check);
        final CheckInformationStorage checkList[] = CheckDataBase.getCheckList(getApplicationContext());
        final String checkBuyTimes[] = new String[checkList.length];
        for (int i = 0; i < checkList.length; i++) {
          if(checkList[i].getBuyTime().charAt(10) == 'T') {
              StringBuilder sb = new StringBuilder();
              sb.append(checkList[i].getBuyTime().substring(0, 9)).append(' ').append(checkList[i].getBuyTime().substring(11));
              checkBuyTimes[i] = sb.toString();
          }
          else
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
        addCheckButton.setOnClickListener(v -> {

            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();
            String strDate = sdfDate.format(now);
            CheckInformationStorage newCheck = new CheckInformationStorage.Builder()
                    .setObtainingMethod("user")
                    .setBuyTime(strDate)
                    .build();
            CheckDataBase.insert(newCheck, getApplicationContext());

            // TODO добавить обновление checklist
            Intent intent = new Intent(getApplicationContext(), EditGoodsDataActivity.class);
            startActivity(intent);

            //intent = new Intent(getApplicationContext(), EditCheckDataActivity.class);
            //intent.putExtra("checkNumber", checkList.length);
            //startActivity(intent);
        });
    }

}
