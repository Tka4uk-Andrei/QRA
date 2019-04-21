package com.example.qra.data.check;

/**
 * this class is responsible for storing check information
 *
 * @author: Marina Alekseeva
 */
public class CheckInformationStorage {

    //TODO iterator

    /**
     * total sum your shopping
     */
    private int totalSum;

    /**
     * Tax Identification Number
     */
    private String inn;

    /**
     * paied nds sum (nds10% + nds18%)
     */
    private int paidNdsSum;

    /**
     * number of products which you bought
     */
    private int quantityPurchases;

    /**
     * store address
     */
    private String addressOfPurchase;

    /**
     * buying time
     */
    private String buyTime;

    /**
     * array of your products list
     */
    private BoughtItem[] shoppingList;


    /**
     * @return total sum your shopping
     */
    public int getTotalSum() {
        return totalSum;
    }

    /**
     * @return Tax Identification Number
     */
    public String getInn() {
        return inn;
    }

    /**
     * @return paied nds sum (nds10% + nds18%)
     */
    public int getPaidNdsSum() {
        return paidNdsSum;
    }

    /**
     * @return number of products which you bought
     */
    public int getQuantityPurchases() {
        return quantityPurchases;
    }

    /**
     * @return store address
     */
    public String getAddressOfPurchase() {
        return addressOfPurchase;
    }

    /**
     * @return buying time
     */
    public String getBuyTime() {
        return buyTime;
    }


    /**
     * @return array of your products list
     */
    public BoughtItem[] getShoppingList() {
        return shoppingList;
    }


    /**
     * @param numberInTheListOfProducts number items in the list of products in the order
     * @return price of this item
     */
    public int getPriseInShoppingList(int numberInTheListOfProducts) {
        return shoppingList[numberInTheListOfProducts].getPrice();
    }

    /**
     * @param numberInTheListOfProducts number items in the list of products in the order
     * @return name of this item
     */
    public String getNameInShoppingList(int numberInTheListOfProducts) {
        return shoppingList[numberInTheListOfProducts].getName();
    }


    /**
     * @param numberInTheListOfProducts number items in the list of products in the order
     * @return quantity of similar products with the given name.
     */
    public int getQuantityOfGoodsWithThisNameInShoppingList(int numberInTheListOfProducts) {
        return shoppingList[numberInTheListOfProducts].getQuantity();
    }


    /**
     * This method allows you to set store address
     *
     * @param addressOfPurchase
     */
    public void setAddressOfPurchase(String addressOfPurchase) {
        this.addressOfPurchase = addressOfPurchase;
    }


    /**
     * This method allows you to set buying time
     *
     * @param buyTime
     */
    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    /**
     * This method allows you to set paied nds sum (nds10% + nds18%)
     *
     * @param paidNdsSum paied nds sum (nds10% + nds18%)
     */
    public void setPaidNdsSum(int paidNdsSum) {
        this.paidNdsSum = paidNdsSum;
    }

    /**
     * This method allows you to set number of products which you bought
     *
     * @param quantityPurchases number of products which you bought
     */
    public void setQuantityPurchases(int quantityPurchases) {
        this.quantityPurchases = quantityPurchases;
    }


    /**
     * This method allows you to set array of your products list
     *
     * @param shoppingList array of your products list
     */
    public void setShoppingList(BoughtItem[] shoppingList) {
        this.shoppingList = shoppingList;
    }

    /**
     * This method allows you to set name of this item
     *
     * @param numberInTheListOfProducts number items in the list of products in the order
     * @param prise
     */
    public void setPriseInShoppingList(int numberInTheListOfProducts, int prise) {
        shoppingList[numberInTheListOfProducts].setPrice(prise);
    }

    /**
     * This method allows you to set quantity of similar products with the given name.
     *
     * @param numberInTheListOfProducts   number items in the list of products in the order
     * @param quantityOfGoodsWithThisName number of goods with the given name
     */
    public void setQuantityOfGoodsWithThisNameInShoppingList
    (int numberInTheListOfProducts, int quantityOfGoodsWithThisName) {
        shoppingList[numberInTheListOfProducts].setQuantity(quantityOfGoodsWithThisName);
    }


    /**
     * This method allows you to set name of this item
     *
     * @param numberInTheListOfProducts number items in the list of products in the order
     * @param name
     */
    public void setNameInShoppingList(int numberInTheListOfProducts, String name) {
        shoppingList[numberInTheListOfProducts].setName(name);
    }


    /**
     * This method allows you to set total sum your shopping
     *
     * @param totalSum total sum your shopping
     */
    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }

    /**
     * @param inn Tax Identification Number
     */
    public void setInn(String inn) {
        this.inn = inn;
    }
}