package com.example.qra.view.interfaces;

import com.example.qra.model.check.BoughtItem;

public interface IShowItemsInCheckView extends IView, ILogOutView {

    /**
     * Method that allows to change the item's info \\
     *
     * @param index position of item in check\\
     */
    void showInputBox(final int index);

    /**
     * Method that update information on the screen \\
     */
    void update(BoughtItem[] items);

    void showErrorMessage(String msg);
}
