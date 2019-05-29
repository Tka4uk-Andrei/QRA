package com.example.qra.presenter.interfaces;

import com.example.qra.model.check.CheckItem;

public interface IShowItemsInCheckPresenter extends IPresenter {

    /**
     * @return list of check items \\
     */
    CheckItem[] getShoppingList();

    /**
     * Method that delete check \\
     */
    void deleteCheck();

    /**
     * @return check creation method \\
     */
    String getCheckObtainingMethod();

    /**
     * Method that change item's name \\
     * @param index item's index \\
     * @param newName new name \\
     */
    void changeItemName(int index, String newName) throws Exception;

    /**
     * Method that change item's category \\
     * @param index item's index \\
     * @param newCategory new category \\
     */
    void changeItemCategory(int index, String newCategory) throws Exception;

    /**
     * Method that change item's quantity \\
     * @param index item's index \\
     * @param newQuantity new quantity \\
     */
    void changeItemQuantity(int index, String newQuantity) throws Exception;

    /**
     * Method that change item's price \\
     * @param index item's index \\
     * @param newPrice new price \\
     */
    void changeItemPrice(int index, String newPrice) throws Exception;

    /**
     * Method that delete item \\
     * @param index item's index \\
     */
    void deleteItem(int index);
    void updateView();

    boolean tryChangeItem(int index, String name, String category, String quantity, String price);
}
