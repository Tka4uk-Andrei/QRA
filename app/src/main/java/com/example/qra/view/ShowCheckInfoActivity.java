package com.example.qra.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qra.CheckDataBase;
import com.example.qra.model.parser.ParsingJson;
import com.example.qra.model.parser.ParsingJsonException;
import com.example.qra.R;
import com.example.qra.model.check.BoughtItem;
import com.example.qra.model.check.CheckInformationStorage;
import com.example.qra.view.dialogs.ErrorDialog;

@Deprecated
public class ShowCheckInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_check_info);

        LinearLayout linearLayout = findViewById(R.id.card_list);

        String msg = getIntent().getStringExtra(WebRequestActivity.JSON_DATA);

        CheckInformationStorage checkInfo = null;
        try {
            checkInfo = ParsingJson.ParseJson(msg);
        } catch (ParsingJsonException e) {
            ErrorDialog dialog = new ErrorDialog();
            dialog.setMsg("json data corrupted. " + e.getErrorMessage());
            dialog.show(getSupportFragmentManager(), "EXCEPTION");
        }

        if (checkInfo == null)
        {
            ErrorDialog dialog = new ErrorDialog();
            dialog.setMsg("check is empty");
            dialog.show(getSupportFragmentManager(), "ERROR");
        } else {

            CheckDataBase.insert(checkInfo,getApplicationContext());
            linearLayout.addView(new CardLayout(getApplicationContext(),
                    "Общая сумма",
                    String.valueOf(checkInfo.getTotalSum())));

            linearLayout.addView(new CardLayout(getApplicationContext(),
                    "ИНН",
                    checkInfo.getInn()));

            linearLayout.addView(new CardLayout(getApplicationContext(),
                    "Уплаченный НДС",
                    String.valueOf(checkInfo.getPaidNdsSum())));

            linearLayout.addView(new CardLayout(getApplicationContext(),
                    "Кол-во товаров",
                    String.valueOf(checkInfo.getQuantityPurchases())));

            linearLayout.addView(new CardLayout(getApplicationContext(),
                    "Адрес покупки",
                    checkInfo.getAddressOfPurchase()));

            linearLayout.addView(new CardLayout(getApplicationContext(),
                    "Время покупки",
                    checkInfo.getBuyTime()));

            linearLayout.addView(new CardLayout(getApplicationContext(),
                    "Товары",""));

            for (BoughtItem item : checkInfo.getShoppingList()) {
                linearLayout.addView(new CardLayout(getApplicationContext(),
                        item.getName(), String.valueOf(item.getPrice()/100.)));
            }
        }


    }

    class CardLayout extends LinearLayout {

        public CardLayout(Context context) {
            super(context);
        }


        public CardLayout(final Context context, String fieldName, String fieldValue) {

            super(context);

            ViewGenerator vg;
            vg = (str, size, weight) -> {
                TextView textView = new TextView(context);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                textView.setText(str);

                LayoutParams temp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                temp.weight = weight;
                temp.rightMargin = 20;

                textView.setLayoutParams(temp);
                // format: 0xAARRGGBB
                textView.setTextColor(0xFF000000);
                return textView;
            };

            this.setOrientation(HORIZONTAL);

            LayoutParams temp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            temp.bottomMargin = 20;
            this.setLayoutParams(temp);

            this.addView(vg.getTextView(fieldName, 20, 0.6f));
            this.addView(vg.getTextView(fieldValue, 20, 0.4f));
        }


    }

    interface ViewGenerator {
        TextView getTextView(String textValue, int size, float weight);
    }
}
