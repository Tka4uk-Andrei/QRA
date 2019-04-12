package com.example.qra;


/**
 * This class is responsible for storing information about the goods purchased
 * (the name of the product and its price of the product)
 * Author: Marina Alekseeva
 */
public class ShoppingList {

    /**
     * attribute name of the product
     */
    private String name;

    /**
     * attribute price of the this products
     */
    private int price;


    /**
     * attribute number of goods with the given name
     */
    private int quantityOfGoodsWithThisName;


    /**
     * This function sets tquantity of goods with the given name
     *
     * @param numberOfGoodsWithThisName
     */
    public void setQuantityOfGoodsWithThisName(int numberOfGoodsWithThisName) {
        this.quantityOfGoodsWithThisName = numberOfGoodsWithThisName;
    }


    /**
     * @return quantity of goods with the given name
     */
    public int getQuantityOfGoodsWithThisName() {
        return quantityOfGoodsWithThisName;
    }

    /**
     * @return price of the this products
     */
    public int getPrice() {
        return price;
    }

    /**
     * @return name of the product
     */
    public String getName() {
        return name;
    }


    /**
     * This function sets the name of the product.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This function sets the price of the this products
     *
     * @param price
     */
    public void setPrice(int price) {
        this.price = price;
    }
}