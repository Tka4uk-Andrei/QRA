package com.example.qra.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.qra.R;
import com.example.qra.presenter.ShowAllChecksPresenter;
import com.example.qra.view.interfaces.IShowAllChecksView;


public class ShowAllChecksActivity extends AppCompatActivity implements IShowAllChecksView {

    private ShowAllChecksPresenter presenter;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_checks);

        presenter = new ShowAllChecksPresenter(this);
        presenter.onCreate();

        listView = findViewById(R.id.choose_check);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, presenter.getCheckList());
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            presenter.startShowItemsInCheckActivity(i);
        });

        Button addCheckButton = findViewById(R.id.add_check_btn);
        addCheckButton.setOnClickListener(v -> presenter.addCheck());
    }

    @Override
    public void update(String[] checkList) {
        listView = findViewById(R.id.choose_check);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, checkList);
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

}
