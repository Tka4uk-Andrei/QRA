package com.example.qra.model;


import com.example.qra.model.check.CheckItem;

@Deprecated
public class DefaultCheckItemCategorizer {

    public void defineCategory(CheckItem item){
        item.setGeneralCategory(ItemCategories.NOT_DISTRIBUTED);
        item.setGeneralCategory(ItemCategories.NOT_DISTRIBUTED);
    }
}

