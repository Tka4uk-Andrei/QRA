package com.example.qra.presenter.interfaces;

public interface ICreateCheckItemPresenter extends IPresenter {

    /**
     * Method that add new item in check
     *
     * @param position position of item in check
     * @param name name of item
     * @param category category of item
     * @param quantity quantity of item
     * @param price price of item
     */
    void addItem(int position, String name, String category, String quantity, String price) throws Exception;
}
