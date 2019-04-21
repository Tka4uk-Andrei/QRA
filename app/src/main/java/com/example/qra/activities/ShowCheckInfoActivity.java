package com.example.qra.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qra.model.parser.ParsingJson;
import com.example.qra.model.parser.ParsingJsonException;
import com.example.qra.R;
import com.example.qra.model.check.BoughtItem;
import com.example.qra.model.check.CheckInformationStorage;

public class ShowCheckInfoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_check_info);

        LinearLayout linearLayout = findViewById(R.id.card_list);

        String msg = getIntent().getStringExtra(WebRequestActivity.JSON_DATA);

//        String msg = "{\"document\":{\"receipt\":{\"senderAddress\":\"\",\"userInn\":\"7826087713\",\"receiptCode\":3,\"addressToCheckFiscalSign\":\"\",\"shiftNumber\":362,\"taxationType\":1,\"operationType\":1,\"fiscalDocumentNumber\":137871,\"buyerAddress\":\"\",\"cashTotalSum\":0,\"requestNumber\":212,\"rawData\":\"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\",\"kktRegId\":\"0000110452032499\",\"user\":\"ООО \\\"О'КЕЙ\\\"\",\"fiscalDriveNumber\":\"9282000100042754\",\"nds10\":18159,\"operator\":\"Михайлова  Е. А.\",\"fiscalSign\":2055518682,\"totalSum\":780897,\"retailPlaceAddress\":\"194356, г. Санкт-Петербург, Выборгское ш.,3,1\",\"ecashTotalSum\":780897,\"items\":[{\"name\":\"Печенье Восторг сдобное 730г С\",\"quantity\":1,\"sum\":23400,\"price\":23400},{\"name\":\"Печенье Восторг сдобное 730г С\",\"quantity\":1,\"sum\":23400,\"price\":23400},{\"name\":\"Полотенца бумажные ОКЕЙ с рису\",\"quantity\":1,\"sum\":6599,\"price\":6599},{\"name\":\"Коктейль утп Чудо шоколад 3% 2\",\"quantity\":1,\"sum\":3169,\"price\":3169},{\"name\":\"Зубная паста Новый Жемчуг Фтор\",\"quantity\":1,\"sum\":5249,\"price\":5249},{\"name\":\"Котлеты по-киевски Мираторг 40\",\"quantity\":1,\"sum\":20300,\"price\":20300},{\"name\":\"Спрей чистящий д/кухни Cillit\",\"quantity\":1,\"sum\":25400,\"price\":25400},{\"name\":\"Капсулы д/стирки Ariel Color 1\",\"quantity\":1,\"sum\":18900,\"price\":18900},{\"name\":\"Капсулы д/стирки Ariel Color 1\",\"quantity\":1,\"sum\":18900,\"price\":18900},{\"name\":\"Мука Предпортовая пшеничная в/\",\"quantity\":1,\"sum\":8799,\"price\":8799},{\"name\":\"Конфеты Ромашки 250г Рот Фронт\",\"quantity\":1,\"sum\":6999,\"price\":6999},{\"name\":\"Конфеты Ромашки 250г Рот Фронт\",\"quantity\":1,\"sum\":6999,\"price\":6999},{\"name\":\"Средство чистящее д/различных\",\"quantity\":1,\"sum\":9999,\"price\":9999},{\"name\":\"Капсулы д/стирки Ariel Горный\",\"quantity\":1,\"sum\":18900,\"price\":18900},{\"name\":\"Капсулы д/стирки Ariel Горный\",\"quantity\":1,\"sum\":18900,\"price\":18900},{\"name\":\"CILLT BANG Ср-во д/чистки стек\",\"quantity\":1,\"sum\":29900,\"price\":29900},{\"name\":\"Кондиционер д/белья Lenor Свеж\",\"quantity\":1,\"sum\":33900,\"price\":33900},{\"name\":\"Полотенца бумажные ОКЕЙ с рису\",\"quantity\":1,\"sum\":6599,\"price\":6599},{\"name\":\"Кетчуп Heinz томатный 0,57л п/\",\"quantity\":1,\"sum\":13400,\"price\":13400},{\"name\":\"Таблетки д/пмм Finish All in o\",\"quantity\":1,\"sum\":99900,\"price\":99900},{\"name\":\"Печенье Oreo в молочном шокола\",\"quantity\":1,\"sum\":24900,\"price\":24900},{\"name\":\"Печенье Oreo в молочном шокола\",\"quantity\":1,\"sum\":24900,\"price\":24900},{\"name\":\"Котлеты по-киевски Мираторг 40\",\"quantity\":1,\"sum\":20300,\"price\":20300},{\"name\":\"Сок Rich апельсин 1л т/п\",\"quantity\":1,\"sum\":12400,\"price\":12400},{\"name\":\"Зубная паста Rocs активный кал\",\"quantity\":1,\"sum\":24400,\"price\":24400},{\"name\":\"Зубная паста Rocs Sensitive во\",\"quantity\":1,\"sum\":24900,\"price\":24900},{\"name\":\"Масло подсолнечное Золотая Сем\",\"quantity\":1,\"sum\":6990,\"price\":6990},{\"name\":\"Макароны Макфа спагетти 500г\",\"quantity\":1,\"sum\":4799,\"price\":4799},{\"name\":\"Котлеты по-киевски Мираторг 40\",\"quantity\":1,\"sum\":20300,\"price\":20300},{\"name\":\"Ополаскиватель д/пмм Finish Бл\",\"quantity\":1,\"sum\":26900,\"price\":26900},{\"name\":\"Готовый Завтрак Nesquik пак 70\",\"quantity\":1,\"sum\":32900,\"price\":32900},{\"name\":\"Каша Овсяная ЯС Ассорти Абрико\",\"quantity\":1,\"sum\":9499,\"price\":9499},{\"name\":\"Каша Овсяная ЯС Ассорти Абрико\",\"quantity\":1,\"sum\":9499,\"price\":9499},{\"name\":\"Приправа Kamis к мясу 25г\",\"quantity\":1,\"sum\":4449,\"price\":4449},{\"name\":\"Приправа Kamis д/мясного фарша\",\"quantity\":1,\"sum\":4449,\"price\":4449},{\"name\":\"Макароны Barilla Piccolini Min\",\"quantity\":1,\"sum\":8399,\"price\":8399},{\"name\":\"Шок.батончик Bounty 7х27,5г\",\"quantity\":1,\"sum\":14400,\"price\":14400},{\"name\":\"Шок.батончик Bounty 7х27,5г\",\"quantity\":1,\"sum\":14400,\"price\":14400},{\"name\":\"Пирожное бисквитное Медвежонок\",\"quantity\":1,\"sum\":6499,\"price\":6499},{\"name\":\"Пирожное бисквитное Медвежонок\",\"quantity\":1,\"sum\":6499,\"price\":6499},{\"name\":\"Пирожное бисквитное Медвежонок\",\"quantity\":1,\"sum\":6499,\"price\":6499},{\"name\":\"Разрыхлитель теста Kamis 10г\",\"quantity\":1,\"sum\":708,\"price\":708},{\"name\":\"Батарейка Energizer MAX AA 6 ш\",\"quantity\":1,\"sum\":29900,\"price\":29900},{\"name\":\"Каша Овсяная ЯС Ассорти Абрико\",\"quantity\":1,\"sum\":9499,\"price\":9499},{\"name\":\"Макароны Barilla Piccolini Min\",\"quantity\":1,\"sum\":8399,\"price\":8399},{\"name\":\"Молоко сгущенное Алексеевское\",\"quantity\":1,\"sum\":14900,\"price\":14900},{\"name\":\"Макароны Макфа спагетти 500г\",\"quantity\":1,\"sum\":4799,\"price\":4799},{\"name\":\"Макароны Макфа спагетти 500г\",\"quantity\":1,\"sum\":4799,\"price\":4799}],\"dateTime\":\"2019-03-25T15:22:00\",\"nds18\":96859}}}";

        CheckInformationStorage checkInfo = null;
        try {
            checkInfo = ParsingJson.ParseJson(msg);
        } catch (ParsingJsonException e) {
            Toast.makeText(getApplicationContext(), "json data corrupted", Toast.LENGTH_LONG).show();
        }

        if (checkInfo == null)
        {
            Toast.makeText(getApplicationContext(), "check is empty", Toast.LENGTH_LONG).show();
            finish();
        } else {

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
