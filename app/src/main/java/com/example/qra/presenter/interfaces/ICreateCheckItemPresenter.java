package com.example.qra.presenter.interfaces;

public interface ICreateCheckItemPresenter extends IPresenter {

    /**
     * Method that add new item in check
     *
     * @param name name of item
     * @param category category of item
     * @param quantity quantity of item
     * @param price price of item
     */
    boolean tryAddItem(String name, String category, String quantity, String price);
}
