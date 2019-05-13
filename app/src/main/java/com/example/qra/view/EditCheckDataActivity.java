package com.example.qra.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qra.CheckDataBase;
import com.example.qra.R;
import com.example.qra.model.check.BoughtItem;
import com.example.qra.model.check.CheckInformationStorage;

public class EditCheckDataActivity extends AppCompatActivity {

    private BoughtItem boughtItems[];
    private String itemNames[];
    private CheckInformationStorage check;

    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_check_data);

        Intent intent = getIntent();
        int position = intent.getIntExtra("checkNumber", 0);

        ListView listView = findViewById(R.id.check_data);
        final CheckInformationStorage checkList[] = CheckDataBase.getCheckList(getApplicationContext());
        check = checkList[position];

        boughtItems = check.getShoppingList();
        itemNames = new String[boughtItems.length];
        for (int i = 0; i < boughtItems.length; i++) {
            itemNames[i] = boughtItems[i].getName();
        }
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemNames);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showInputBox(itemNames[i], i);
            }
        });

        Button addGoodButton = findViewById(R.id.add_good_btn);
        addGoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoughtItem[] newList = new BoughtItem[boughtItems.length + 1];
                for (int i = 0; i < boughtItems.length; i++) {
                    newList[i] = boughtItems[i];
                }
                BoughtItem newItem = new BoughtItem.Builder().build();
                newList[newList.length - 1] = newItem;
                check.setShoppingList(newList);
            }
        });

        if (check.getObtainingMethod().equals("user")) {
            addGoodButton.setVisibility(View.INVISIBLE);
        }
    }

    public void showInputBox(String oldItem, final int index) {
        final Dialog dialog = new Dialog(EditCheckDataActivity.this);
        dialog.setTitle("Input Box");
        dialog.setContentView(R.layout.input_box);
        TextView txtMessage = (TextView) dialog.findViewById(R.id.txtmessage);
        final EditText editText = (EditText) dialog.findViewById(R.id.txtinput);
        editText.setText(oldItem);
        Button bt = (Button) dialog.findViewById(R.id.btdone);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = editText.getText().toString();
                itemNames[index] = newText;

                check.getShoppingList()[index].setName(newText);

                CheckDataBase.updateNameForUser(check.getShoppingList()[index].getId(), newText, getApplicationContext());
                arrayAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
