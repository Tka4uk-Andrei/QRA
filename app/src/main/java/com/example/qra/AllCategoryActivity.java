package com.example.qra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


/**
 * responsible for displaying all categories
 *
 * @author: Marina Alekseeva
 */
public class AllCategoryActivity extends AppCompatActivity {
    public static final String GENERAL_CATEGORIES = "generalCategories";
    public static final String SUBJECT_CATEGORIES = "subjectCategories";
    private String[] generalCategories;
    private String[] subjectEatCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);

        LinearLayout container = findViewById(R.id.btn_categories_container);

        if (getIntent().hasExtra(GENERAL_CATEGORIES)) {
            subjectEatCategories = ItemCategories.CONTENT_CATEGORY_EAT.getEatCategories();
            for (int i = 0; i < ItemCategories.CONTENT_CATEGORY_EAT.NUMBERS_EAT_CATEGORY; ++i) {
                Button button = new Button(getApplicationContext());
                button.setText(subjectEatCategories[i]);
                button.setOnClickListener(eatSubjectCategoriesBtnClickListener);
                container.addView(button);

            }
        } else {
            generalCategories = ItemCategories.getGeneralCategories();
            for (int i = 0; i < ItemCategories.NUMBERS_GENERAL_CATEGORIES; ++i) {
                Button button = new Button(getApplicationContext());
                button.setText(generalCategories[i]);
                button.setOnClickListener(generalCategoriesBtnClickListener);
                container.addView(button);
            }
        }

    }

    /**
     * handler for clicking on the general categories
     */
    private View.OnClickListener generalCategoriesBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            String buttonText = button.getText().toString();
            Intent intent = null;

            for (int i = 0; i < ItemCategories.NUMBERS_GENERAL_CATEGORIES; ++i) {
                if (buttonText.equals(generalCategories[i])) {
                    if (!(buttonText.equals(ItemCategories.CATEGORY_EAT))) {
                        intent = new Intent(getApplicationContext(), ListProductsByCategoryActivity.class);
                        intent.putExtra(GENERAL_CATEGORIES, generalCategories[i]);
                        intent.putExtra(SUBJECT_CATEGORIES, ItemCategories.NOT_DISTRIBUTED);
                        break;
                    } else {
                        // перезапускаем активити с новым интентом
                        intent = new Intent(getApplicationContext(), AllCategoryActivity.class);
                        intent.putExtra(GENERAL_CATEGORIES, generalCategories[i]);
                        break;
                    }
                }
            }
            startActivity(intent);
        }
    };


    /**
     * handler for clicking on the subject eat categories
     */
    private View.OnClickListener eatSubjectCategoriesBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            String buttonText = button.getText().toString();
            Intent intent = null;
            for (int i = 0; i < ItemCategories.CONTENT_CATEGORY_EAT.NUMBERS_EAT_CATEGORY; ++i) {
                if (buttonText.equals(subjectEatCategories[i])) {
                    intent = new Intent(getApplicationContext(), ListProductsByCategoryActivity.class);
                    intent.putExtra(GENERAL_CATEGORIES, ItemCategories.CATEGORY_EAT);
                    intent.putExtra(SUBJECT_CATEGORIES, subjectEatCategories[i]);
                    break;
                }
            }
            startActivity(intent);
        }
    };
}

