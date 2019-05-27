package com.example.qra.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.qra.R;
import com.example.qra.model.check.BoughtItem;
import com.example.qra.model.check.CheckInformationStorage;
import com.example.qra.presenter.ShowItemsInCheckPresenter;
import com.example.qra.view.ShowItemsInCheckActivity;

public class EditCheckItemDialog extends Dialog {
    private ShowItemsInCheckActivity starterActivity;

    public EditCheckItemDialog(Context context, ShowItemsInCheckActivity activity) {
        super(context);
        starterActivity = activity;
        ShowItemsInCheckPresenter presenter = starterActivity.getPresenter();

        int index = activity.getIntent().getIntExtra(ShowItemsInCheckPresenter.CURRENT_ITEM, 0);
        BoughtItem item = presenter.getShoppingList()[index];

        setTitle("Input Box");
        setContentView(R.layout.input_box);
        findViewById(R.id.message_text);

        final EditText editNameText = findViewById(R.id.input_name_text);
        final EditText editCategoryText = findViewById(R.id.input_category_text);
        final EditText editQuantityText = findViewById(R.id.input_quantity_text);
        final EditText editPriceText = findViewById(R.id.input_price_text);

        if (presenter.getCheckObtainingMethod().equals(CheckInformationStorage.OBTAIN_METHOD_FNS)) {
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
            boolean success = presenter.tryChangeItem(index,
                    editNameText.getText().toString(),
                    editCategoryText.getText().toString(),
                    editQuantityText.getText().toString(),
                    editPriceText.getText().toString());
            if (success) {
                presenter.updateView();
                dismiss();
            }
        });

        Button deleteBtn = findViewById(R.id.delete_btn);
        deleteBtn.setOnClickListener(v -> {
            presenter.deleteItem(index);
            presenter.updateView();
            dismiss();
        });

        if (presenter.getCheckObtainingMethod().equals(CheckInformationStorage.OBTAIN_METHOD_FNS)) {
            deleteBtn.setVisibility(View.INVISIBLE);
        }
    }
}
