package com.example.qra.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qra.CheckDataBase;
import com.example.qra.CheckEditingException;
import com.example.qra.R;
import com.example.qra.model.check.BoughtItem;
import com.example.qra.model.check.CheckInformationStorage;

public class EditCheckDataActivity extends AppCompatActivity {

    private BoughtItem boughtItems[];
    private String itemNames[];
    private CheckInformationStorage check;

    private ArrayAdapter<String> arrayAdapter;
    private int position;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_check_data);

        Intent intent = getIntent();
        position = intent.getIntExtra("checkNumber", 0);

        listView = findViewById(R.id.check_data);
        update();
        listView.setOnItemClickListener((adapterView, view, i, l) -> showInputBox(itemNames[i], i));

        Button addGoodButton = findViewById(R.id.add_good_btn);
        addGoodButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), AddGoodInCheckActivity.class);
            intent1.putExtra("checkNumber", position);
            startActivity(intent1);
        });

        Button deleteCheckButton = findViewById(R.id.delete_check_btn);
        deleteCheckButton.setOnClickListener(v -> {
            CheckDataBase.deleteCheck(check.getId(), getApplicationContext());
            finish();
        });

        if (check.getObtainingMethod().equals("FNS")) {
            addGoodButton.setVisibility(View.INVISIBLE);
        }


    }

    public void showInputBox(String oldItem, final int index) {
        final Dialog dialog = new Dialog(EditCheckDataActivity.this);
        dialog.setTitle("Input Box");
        dialog.setContentView(R.layout.input_box);
        dialog.findViewById(R.id.message_text);
        final EditText editText = dialog.findViewById(R.id.input_text);
        editText.setText(oldItem);
        Button doneBtn = dialog.findViewById(R.id.done_btn);
        doneBtn.setOnClickListener(v -> {
            String newText = editText.getText().toString();
            itemNames[index] = newText;
            check.getShoppingList()[index].setNameForUser(newText);

            CheckDataBase.updateNameForUser(check.getShoppingList()[index].getId(), newText, getApplicationContext());
            arrayAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        Button deleteBtn = dialog.findViewById(R.id.delete_btn);
        deleteBtn.setOnClickListener(v -> {
            try {
                CheckDataBase.deleteItem(boughtItems[index].getId(),getApplicationContext());
            } catch (CheckEditingException e) {
                Toast.makeText(EditCheckDataActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            update();
            dialog.dismiss();
        });
        if (check.getObtainingMethod().equals("FNS")) {
            deleteBtn.setVisibility(View.INVISIBLE);
        }
        dialog.show();
    }

    private void update(){
        final CheckInformationStorage checkList[] = CheckDataBase.getCheckList(getApplicationContext());
        check = checkList[position];

        boughtItems = check.getShoppingList();
        itemNames = new String[boughtItems.length];
        for (int i = 0; i < boughtItems.length; i++) {
            itemNames[i] = boughtItems[i].getNameForUser();
        }
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemNames);
        listView.setAdapter(arrayAdapter);

    }
}
