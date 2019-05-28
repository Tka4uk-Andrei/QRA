package com.example.qra.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.qra.R;
import com.example.qra.model.check.CheckItem;

import java.util.List;


/**
 * product information adapter
 *
 * @author: Marina Alekseeva
 */
public class CheckItemAdapter extends BaseAdapter {
    private List<CheckItem> checkItemList;
    private LayoutInflater layoutInflater;

    public CheckItemAdapter(Context context, List<CheckItem> checkItemList) {
        this.checkItemList = checkItemList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return checkItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return checkItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.check_item_layout, parent, false);
        }

        CheckItem checkItem = getCheckItem(position);

        TextView textView = view.findViewById(R.id.check_prise_text);
        textView.setText(Integer.toString(checkItem.getPrice()));
        textView = view.findViewById(R.id.check_item_name_for_user_text);
        textView.setText(checkItem.getNameForUser());
        textView = view.findViewById(R.id.check_quantity_text);
        textView.setText(Integer.toString(checkItem.getQuantity()));


        return view;
    }

    private CheckItem getCheckItem(int position) {
        return (CheckItem) getItem(position);
    }
}
