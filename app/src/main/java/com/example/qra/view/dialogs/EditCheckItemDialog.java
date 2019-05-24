package com.example.qra.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qra.R;
import com.example.qra.model.check.BoughtItem;
import com.example.qra.presenter.ShowItemsInCheckPresenter;
import com.example.qra.view.ShowItemsInCheckActivity;

public class EditCheckItemDialog extends Dialog {
    private ShowItemsInCheckActivity starterActivity;

    public EditCheckItemDialog(Context context, ShowItemsInCheckActivity activity) {
        super(context);
        starterActivity = activity;
        ArrayAdapter arrayAdapter = starterActivity.getArrayAdapter();
        ShowItemsInCheckPresenter presenter = starterActivity.getPresenter();

        int index = activity.getIntent().getIntExtra("Index", 0);
        BoughtItem item = presenter.getShoppingList()[index];

        setTitle("Input Box");
        setContentView(R.layout.input_box);
        findViewById(R.id.message_text);

        final EditText editNameText = findViewById(R.id.input_name_text);
        final EditText editCategoryText = findViewById(R.id.input_category_text);
        final EditText editQuantityText = findViewById(R.id.input_quantity_text);
        final EditText editPriceText = findViewById(R.id.input_price_text);

        if (presenter.getCheckObtainingMethod().equals("FNS")) {
            editCategoryText.setVisibility(View.INVISIBLE);
            editQuantityText.setVisibility(View.INVISIBLE);
            editPriceText.setVisibility(View.INVISIBLE);
        }
        editNameText.setText(item.getNameForUser());
        editCategoryText.setText(item.getSubjectCategory());
        editQuantityText.setText(Integer.toString(item.getQuantity()));
        editPriceText.setText(item.getPriceForUser());

        Button doneBtn = findViewById(R.id.done_btn);
        doneBtn.setOnClickListener(v -> {
            try {
                presenter.changeItemName(index, editNameText.getText().toString());
                presenter.changeItemCategory(index, editCategoryText.getText().toString());
                presenter.changeItemQuantity(index, editQuantityText.getText().toString());
                presenter.changeItemPrice(index, editPriceText.getText().toString());
                arrayAdapter.notifyDataSetChanged();
                starterActivity.update();
                dismiss();
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Button deleteBtn = findViewById(R.id.delete_btn);
        deleteBtn.setOnClickListener(v -> {
            presenter.deleteItem(index);
            arrayAdapter.notifyDataSetChanged();
            starterActivity.update();
            dismiss();
        });

        if (presenter.getCheckObtainingMethod().equals("FNS")) {
            deleteBtn.setVisibility(View.INVISIBLE);
        }
    }
}
