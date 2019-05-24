package com.example.qra.view.interfaces;

public interface IShowItemsInCheckView extends IView {

    /**
     * Method that allows to change the item's info \\
     *
     * @param index position of item in check\\
     */
    void showInputBox(final int index);

    /**
     * Method that update information on the screen \\
     */
    void update();

}
