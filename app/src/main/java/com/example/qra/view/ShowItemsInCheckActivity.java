package com.example.qra.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.qra.R;
import com.example.qra.model.check.BoughtItem;
import com.example.qra.presenter.ShowItemsInCheckPresenter;
import com.example.qra.view.dialogs.EditCheckItemDialog;
import com.example.qra.view.interfaces.IShowItemsInCheckView;

public class ShowItemsInCheckActivity extends AppCompatActivity implements IShowItemsInCheckView {

    private BoughtItem items[];
    private ArrayAdapter<BoughtItem> arrayAdapter;
    private ListView listView;

    private ShowItemsInCheckPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items_in_check);

        presenter = new ShowItemsInCheckPresenter(this);
        presenter.onCreate();

        listView = findViewById(R.id.check_data);
        update();
        listView.setOnItemClickListener((adapterView, view, i, l) -> showInputBox(i));

        Button addGoodButton = findViewById(R.id.add_good_btn);
        addGoodButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), CreateCheckItemActivity.class);
            intent1.putExtra("checkNumber", getIntent().getIntExtra("checkNumber", 0));
            startActivityForResult(intent1, 1);
        });

        Button deleteCheckButton = findViewById(R.id.delete_check_btn);
        deleteCheckButton.setOnClickListener(v -> {
            presenter.deleteCheck();
            finish();
        });

        if (presenter.getCheckObtainingMethod().equals("FNS")) {
            addGoodButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showInputBox(final int index) {
        presenter.update();
        getIntent().putExtra("Index", index);
        EditCheckItemDialog dialog = new EditCheckItemDialog(ShowItemsInCheckActivity.this, this);
        dialog.show();
    }

    @Override
    public void update() {
        items = presenter.getShoppingList();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            update();
        }
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public Intent getStarterIntent() {
        return getIntent();
    }

    public ArrayAdapter getArrayAdapter() {
        return arrayAdapter;
    }

    public ShowItemsInCheckPresenter getPresenter() {
        return presenter;
    }
}
