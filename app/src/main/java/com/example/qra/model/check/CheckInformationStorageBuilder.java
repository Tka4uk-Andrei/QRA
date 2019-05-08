package com.example.qra.model.check;

public class CheckInformationStorageBuilder {
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
    private int id = -1;


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
     * This method allows you to set fiscal document number
     *
     * @param fiscalDocumentNumber
     * @return object with a filled field
     */
    public CheckInformationStorageBuilder setFiscalDocumentNumber(int fiscalDocumentNumber) {
        this.fiscalDocumentNumber = fiscalDocumentNumber;
        return this;
    }

    /**
     * This method allows you to set fiscal drive number
     *
     * @param fiscalDriveNumber
     * @return object with a filled field
     */
    public CheckInformationStorageBuilder setFiscalDriveNumber(String fiscalDriveNumber) {
        this.fiscalDriveNumber = fiscalDriveNumber;
        return this;
    }

    /**
     * This method allows you to set fiscal sign
     *
     * @param fiscalSign
     * @return object with a filled field
     */
    public CheckInformationStorageBuilder setFiscalSign(int fiscalSign) {
        this.fiscalSign = fiscalSign;
        return this;
    }

    /**
     * This method allows you to set tracking ID
     *
     * @param id
     * @return object with a filled field
     */
    public CheckInformationStorageBuilder setId(int id) {
        this.id = id;
        return this;
    }


    /**
     * This method allows you to set method of obtaining
     *
     * @param obtainingMethod
     * @return object with a filled field
     */
    public CheckInformationStorageBuilder setObtainingMethod(String obtainingMethod) {
        this.obtainingMethod = obtainingMethod;
        return this;
    }


    /**
     * This method allows you to set store address
     *
     * @param addressOfPurchase
     * @return object with a filled field
     */
    public CheckInformationStorageBuilder setAddressOfPurchase(String addressOfPurchase) {
        this.addressOfPurchase = addressOfPurchase;
        return this;
    }


    /**
     * This method allows you to set buying time
     *
     * @param buyTime
     * @return object with a filled field
     */
    public CheckInformationStorageBuilder setBuyTime(String buyTime) {
        this.buyTime = buyTime;
        return this;
    }

    /**
     * This method allows you to set paied nds sum (nds10% + nds18%)
     *
     * @param paidNdsSum paied nds sum (nds10% + nds18%)
     * @return object with a filled field
     */
    public CheckInformationStorageBuilder setPaidNdsSum(int paidNdsSum) {
        this.paidNdsSum = paidNdsSum;
        return this;
    }

    /**
     * This method allows you to set number of products which you bought
     *
     * @param quantityPurchases number of products which you bought
     * @return object with a filled field
     */
    public CheckInformationStorageBuilder setQuantityPurchases(int quantityPurchases) {
        this.quantityPurchases = quantityPurchases;
        return this;
    }


    /**
     * This method allows you to set array of your products list
     *
     * @param shoppingList array of your products list
     * @return object with a filled field
     */
    public CheckInformationStorageBuilder setShoppingList(BoughtItem[] shoppingList) {
        this.shoppingList = shoppingList;
        return this;
    }

    /**
     * This method allows you to set total sum your shopping
     *
     * @param totalSum total sum your shopping
     * @return object with a filled field
     */
    public CheckInformationStorageBuilder setTotalSum(int totalSum) {
        this.totalSum = totalSum;
        return this;
    }

    /**
     * @param inn Tax Identification Number
     * @return object with a filled field
     */
    public CheckInformationStorageBuilder setInn(String inn) {
        this.inn = inn;
        return this;
    }

    public CheckInformationStorage build() {
        CheckInformationStorage checkObject = new CheckInformationStorage(this.id);
        checkObject.setTotalSum(this.totalSum);
        checkObject.setInn(this.inn);
        checkObject.setPaidNdsSum(this.paidNdsSum);
        checkObject.setAddressOfPurchase(this.addressOfPurchase);
        checkObject.setBuyTime(this.buyTime);
        checkObject.setFiscalDocumentNumber(this.fiscalDocumentNumber);
        checkObject.setFiscalDriveNumber(this.fiscalDriveNumber);
        checkObject.setFiscalSign(this.fiscalSign);
        checkObject.setQuantityPurchases(this.quantityPurchases);
        checkObject.setShoppingList(this.shoppingList);
        checkObject.setObtainingMethod(this.obtainingMethod);

        return checkObject;
    }
}
