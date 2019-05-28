package com.example.qra.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;


import com.example.qra.R;
import com.example.qra.model.CheckDataBase;
import com.example.qra.model.ItemCategories;
import com.example.qra.model.check.CheckItem;
import com.example.qra.view.AllCategoryActivity;
import com.example.qra.view.adapter.CheckItemAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * displaying a list of products of a specific category
 *
 * @author: Marina Alekseeva
 */
public class ListProductsByCategoryActivity extends AppCompatActivity {

    private String generalCategory;
    private String subjectCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products_by_category);

        generalCategory = getIntent().getStringExtra(AllCategoryActivity.GENERAL_CATEGORIES);
        subjectCategory = getIntent().getStringExtra(AllCategoryActivity.SUBJECT_CATEGORIES);

        TextView category = findViewById(R.id.text_categories);
        if (subjectCategory.equals(ItemCategories.NOT_DISTRIBUTED)) {
            category.setText(generalCategory);
        } else {
            String allCategory = generalCategory + " -> " + subjectCategory;
            category.setText(allCategory);
        }

        ListView listView = findViewById(R.id.items_list_for_categories);

        CheckItemAdapter adapter = new CheckItemAdapter(getApplicationContext(), initData());
        listView.setAdapter(adapter);
    }


    private List<CheckItem> initData() {
        List<CheckItem> checkItemList = new ArrayList<CheckItem>();
        CheckItem[] shopList = CheckDataBase.getShoppingList(getApplicationContext(), generalCategory, subjectCategory);
        for (int i = 0; i < shopList.length; i++) {
            checkItemList.add(shopList[i]);
        }
        return checkItemList;
    }

}




