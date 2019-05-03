package com.example.qra;

/**
 * this class is responsible for storing check information
 *
 * @author: Marina Alekseeva
 */
public class CheckInformationStorage {

    /**
     * method of obtaining (FNS or user)
     */
    private String obtainingMethod;

    /**
     * total sum your shopping
     */
    private int totalSum;

    /**
     * Tax Identification Number
     */
    private String inn;

    /**
     * paied nds sum (nds10% + nds18% + nds20%)
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
     * tracking ID
     */
    private int id;


    /**
     * fiscal document number
     */
    private int fiscalDocumentNumber;

    /**
     * fiscal drive number
     */
    private String fiscalDriveNumber;

    /**
     * fiscal sign
     */
    private int fiscalSign;

    /**
     * @return fiscal document number
     */
    public int getFiscalDocumentNumber() {
        return fiscalDocumentNumber;
    }

    /**
     * @return fiscal sign
     */
    public int getFiscalSign() {
        return fiscalSign;
    }

    /**
     * @return fiscal drive number
     */
    public String getFiscalDriveNumber() {
        return fiscalDriveNumber;
    }

    /**
     * This method allows you to set fiscal document number
     *
     * @param fiscalDocumentNumber
     */
    public void setFiscalDocumentNumber(int fiscalDocumentNumber) {
        this.fiscalDocumentNumber = fiscalDocumentNumber;
    }

    /**
     * This method allows you to set fiscal drive number
     *
     * @param fiscalDriveNumber
     */
    public void setFiscalDriveNumber(String fiscalDriveNumber) {
        this.fiscalDriveNumber = fiscalDriveNumber;
    }

    /**
     * This method allows you to set fiscal sign
     *
     * @param fiscalSign
     */
    public void setFiscalSign(int fiscalSign) {
        this.fiscalSign = fiscalSign;
    }

    /**
     * This method allows you to set tracking ID
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return tracking ID
     */
    public int getId() {
        return id;
    }

    /**
     * @return method of obtaining (FNS or user)
     */
    public String getObtainingMethod() {
        return obtainingMethod;
    }


    /**
     * This method allows you to set method of obtaining
     *
     * @param obtainingMethod
     */
    public void setObtainingMethod(String obtainingMethod) {
        this.obtainingMethod = obtainingMethod;
    }

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