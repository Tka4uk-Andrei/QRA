package com.example.qra;

/**
 * this class is responsible for storing check information
 */
public class CheckInformationStorage {

    /**
     * attribute total sum your shopping
     */
    private int totalSum;

    /**
     * attribute paied nds sum (nds10% + nds18%)
     */
    private int paiedNdsSum;

    /**
     * attribute number of products which you bought
     */
    private int quantityPurchases;

    /**
     * attribute store address
     */
    private String addresOfPurchase;

    /**
     * attribute buying time
     */
    private String buyTime;

    /**
     * attribute array of your products list
     */
    private ShoppingList[] ShoppingListArray;


    /**
     * @return total sum your shopping
     */
    public int getTotalSum() {
        return totalSum;
    }

    /**
     * @return paied nds sum (nds10% + nds18%)
     */
    public int getPaiedNdsSum() {
        return paiedNdsSum;
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
    public String getAddresOfPurchase() {
        return addresOfPurchase;
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
    public ShoppingList[] getShoppingListArray() {
        return ShoppingListArray;
    }


    /**
     * @param numberInTheListOfProducts
     * @return price of this item
     */
    public int getPriseInShoppingListArray(int numberInTheListOfProducts) {
        return ShoppingListArray[numberInTheListOfProducts].getPrice();
    }

    /**
     * @param numberInTheListOfProducts
     * @return name of this item
     */
    public String getNameInShoppingListArray(int numberInTheListOfProducts) {
        return ShoppingListArray[numberInTheListOfProducts].getName();
    }


    /**
     * This method allows you to set store address
     *
     * @param addresOfPurchase
     */
    public void setAddresOfPurchase(String addresOfPurchase) {
        this.addresOfPurchase = addresOfPurchase;
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
     * @param paiedNdsSum
     */
    public void setPaiedNdsSum(int paiedNdsSum) {
        this.paiedNdsSum = paiedNdsSum;
    }

    /**
     * This method allows you to set number of products which you bought
     *
     * @param quantityPurchases
     */
    public void setQuantityPurchases(int quantityPurchases) {
        this.quantityPurchases = quantityPurchases;
    }


    /**
     * array ShoppingListArray initialization
     */
    public void declareAnArray() {
        ShoppingListArray = new ShoppingList[quantityPurchases];
        for (int i = 0; i < quantityPurchases; i++) {
            ShoppingListArray[i] = new ShoppingList();
        }
    }


    /**
     * This method allows you to set array of your products list
     *
     * @param shoppingListArray
     */
    public void setShoppingListArray(ShoppingList[] shoppingListArray) {
        ShoppingListArray = shoppingListArray;
    }

    /**
     * This method allows you to set name of this item
     *
     * @param numberInTheListOfProducts
     * @param prise
     */
    public void setPriseInShoppingListArray(int numberInTheListOfProducts, int prise) {
        ShoppingListArray[numberInTheListOfProducts].setPrice(prise);
    }


    /**
     * This method allows you to set name of this item
     *
     * @param numberInTheListOfProducts
     * @param name
     */
    public void setNameInShoppingListArray(int numberInTheListOfProducts, String name) {
        ShoppingListArray[numberInTheListOfProducts].setName(name);
    }


    /**
     * This method allows you to set total sum your shopping
     *
     * @param totalSum
     */
    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }


}