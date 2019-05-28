package com.example.qra.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qra.R;
import com.example.qra.model.check.BoughtItem;
import com.example.qra.model.check.CheckInformationStorage;
import com.example.qra.presenter.ShowItemsInCheckPresenter;
import com.example.qra.view.dialogs.EditCheckItemDialog;
import com.example.qra.view.interfaces.IShowItemsInCheckView;

public class ShowItemsInCheckActivity extends AppCompatActivity implements IShowItemsInCheckView {

    private ArrayAdapter<BoughtItem> arrayAdapter;
    private ListView listView;

    private ShowItemsInCheckPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items_in_check);

        // initialize presenter
        presenter = new ShowItemsInCheckPresenter(this);
        presenter.onCreate();

        // set arrayView
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, presenter.getShoppingList());
        listView = findViewById(R.id.check_data);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> showInputBox(i));

        // set clickListeners
        findViewById(R.id.add_item_btn).setOnClickListener(v -> presenter.startCreateCheckItemActivity());
        findViewById(R.id.delete_check_btn).setOnClickListener(v -> presenter.deleteCheck());

        // disable button if check from fns
        if (presenter.getCheckObtainingMethod().equals(CheckInformationStorage.OBTAIN_METHOD_FNS)) {
            findViewById(R.id.add_item_btn).setVisibility(View.INVISIBLE);
        }
    }

    public void showErrorMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInputBox(final int index) {
        presenter.setCurrentItem(index);
        EditCheckItemDialog dialog = new EditCheckItemDialog(ShowItemsInCheckActivity.this, this);
        dialog.show();
    }

    @Override
    public void update(BoughtItem[] items) {
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            presenter.updateView();
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

    public ShowItemsInCheckPresenter getPresenter() {
        return presenter;
    }
}
