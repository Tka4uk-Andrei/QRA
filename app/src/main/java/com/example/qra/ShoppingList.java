package com.example.qra;


/**
 * This class is responsible for storing information about the goods purchased
 * (the name of the product and its price of the product)
 * Author: Marina Alekseeva
 */
public class ShoppingList{
     private String name;
    private int price;
    //namesAndPrices(){ name = "-"; price =0;}

    /**
     * @return price of the product
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
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This function sets the price of the product.
     * @param price
     */
    public void setPrice(int price) {
        this.price = price;
    }
}