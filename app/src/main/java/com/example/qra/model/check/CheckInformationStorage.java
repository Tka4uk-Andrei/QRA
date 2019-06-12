package com.example.qra.model.check;

/**
 * this class is responsible for storing check information
 *
 * @author: Marina Alekseeva
 */
public class CheckInformationStorage {

    public static final String OBTAIN_METHOD_FNS = "FNS";
    public static final String OBTAIN_METHOD_USER = "user";
    public static final String CHECK_NAME_UNDEFINED = "undefined";

    /**
     * name of check
     */
    private String checkName;

    /**
     * method of obtaining (FNS or user) on default is user
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
     * buying date
     */
    private String buyDate;

    /**
     * array of your products list
     */
    private CheckItem[] shoppingList;


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
     * @return name of check
     */
    public String getCheckName() {
        return checkName;
    }

    /**
     * This method allows you to set name of check
     *
     * @param checkName
     */
    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

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
    private void setFiscalDocumentNumber(int fiscalDocumentNumber) {
        this.fiscalDocumentNumber = fiscalDocumentNumber;
    }

    /**
     * This method allows you to set fiscal drive number
     *
     * @param fiscalDriveNumber
     */
    private void setFiscalDriveNumber(String fiscalDriveNumber) {
        this.fiscalDriveNumber = fiscalDriveNumber;
    }

    /**
     * This method allows you to set fiscal sign
     *
     * @param fiscalSign
     */
    private void setFiscalSign(int fiscalSign) {
        this.fiscalSign = fiscalSign;
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
     * @param method method of obtaining
     */
    private void setObtainingMethod(String method) {
        this.obtainingMethod = method;
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
     * @return buying date
     */
    public String getBuyDate() {
        return buyDate;
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
    public CheckItem[] getShoppingList() {
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
     * This method allows you to set buying date
     *
     * @param buyDate
     */
    public void setBuyDate(String buyDate) {
        if (checkName == CHECK_NAME_UNDEFINED) {
            checkName = buyDate;
        }
        this.buyDate = buyDate;
    }

    /**
     * This method allows you to set buying time
     *
     * @param buyTime
     */
    public void setBuyTime(String buyTime) {
        if (checkName == CHECK_NAME_UNDEFINED) {
            checkName = buyTime;
        }
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
    private void setQuantityPurchases(int quantityPurchases) {
        this.quantityPurchases = quantityPurchases;
    }


    /**
     * This method allows you to set array of your products list
     *
     * @param shoppingList array of your products list
     */
    public void setShoppingList(CheckItem[] shoppingList) {
        this.shoppingList = shoppingList;
        if (shoppingList != null) {
            this.quantityPurchases = shoppingList.length;
        }
    }

    /**
     * This method allows you to set total sum your shopping
     *
     * @param totalSum total sum your shopping
     */
    private void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }

    /**
     * @param inn Tax Identification Number
     */
    public void setInn(String inn) {
        this.inn = inn;
    }

    /**
     * protected constructor
     *
     * @param id - tracking ID
     */
    private CheckInformationStorage(int id) {
        this.id = id;
    }

    public static class Builder {
        /**
         * method of obtaining (FNS or user)
         */
        private String obtainingMethod = OBTAIN_METHOD_USER;

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
         * buying date
         */
        private String buyDate;

        /**
         * name of check
         */
        private String checkName;

        /**
         * array of your products list
         */
        private CheckItem[] shoppingList;


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
        public Builder setFiscalDocumentNumber(int fiscalDocumentNumber) {
            this.fiscalDocumentNumber = fiscalDocumentNumber;
            return this;
        }

        /**
         * This method allows you to set fiscal drive number
         *
         * @param fiscalDriveNumber
         * @return object with a filled field
         */
        public Builder setFiscalDriveNumber(String fiscalDriveNumber) {
            this.fiscalDriveNumber = fiscalDriveNumber;
            return this;
        }

        /**
         * This method allows you to set fiscal sign
         *
         * @param fiscalSign
         * @return object with a filled field
         */
        public Builder setFiscalSign(int fiscalSign) {
            this.fiscalSign = fiscalSign;
            return this;
        }

        /**
         * This method allows you to set tracking ID
         *
         * @param id
         * @return object with a filled field
         */
        public Builder setId(int id) {
            this.id = id;
            return this;
        }


        /**
         * This method allows you to set method of obtaining
         *
         * @param method method of obtaining
         * @return object with a filled field
         */
        public Builder setObtainingMethod(String method) {
            this.obtainingMethod = method;
            return this;
        }

        /**
         * This method allows you to set store address
         *
         * @param addressOfPurchase
         * @return object with a filled field
         */
        public Builder setAddressOfPurchase(String addressOfPurchase) {
            this.addressOfPurchase = addressOfPurchase;
            return this;
        }


        /**
         * This method allows you to set buying time
         *
         * @param buyTime
         * @return object with a filled field
         */
        public Builder setBuyTime(String buyTime) {
            this.buyTime = buyTime;
            return this;
        }

        /**
         * This method allows you to set buying date
         *
         * @param buyDate
         * @return object with a filled field
         */
        public Builder setBuyDate(String buyDate) {
            this.buyDate = buyDate;
            return this;
        }

        /**
         * This method allows you to set name of check
         *
         * @param checkName
         * @return object with a filled field
         */
        public Builder setCheckName(String checkName) {
            this.checkName = checkName;
            return this;
        }

        /**
         * This method allows you to set paied nds sum (nds10% + nds18%)
         *
         * @param paidNdsSum paied nds sum (nds10% + nds18%)
         * @return object with a filled field
         */
        public Builder setPaidNdsSum(int paidNdsSum) {
            this.paidNdsSum = paidNdsSum;
            return this;
        }

        /**
         * This method allows you to set number of products which you bought
         *
         * @param quantityPurchases number of products which you bought
         * @return object with a filled field
         */
        public Builder setQuantityPurchases(int quantityPurchases) {
            this.quantityPurchases = quantityPurchases;
            return this;
        }


        /**
         * This method allows you to set array of your products list
         *
         * @param shoppingList array of your products list
         * @return object with a filled field
         */
        public Builder setShoppingList(CheckItem[] shoppingList) {
            this.shoppingList = shoppingList;
            if (shoppingList != null) {
                this.quantityPurchases = shoppingList.length;
            }
            return this;
        }

        /**
         * This method allows you to set total sum your shopping
         *
         * @param totalSum total sum your shopping
         * @return object with a filled field
         */
        public Builder setTotalSum(int totalSum) {
            this.totalSum = totalSum;
            return this;
        }

        /**
         * @param inn Tax Identification Number
         * @return object with a filled field
         */
        public Builder setInn(String inn) {
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
            checkObject.setBuyDate(this.buyDate);
            checkObject.setFiscalDocumentNumber(this.fiscalDocumentNumber);
            checkObject.setFiscalDriveNumber(this.fiscalDriveNumber);
            checkObject.setFiscalSign(this.fiscalSign);
            checkObject.setQuantityPurchases(this.quantityPurchases);
            checkObject.setShoppingList(this.shoppingList);
            checkObject.setObtainingMethod(this.obtainingMethod);
            if (this.checkName == null){
                if((this.buyDate!= null)&& (this.buyTime!=null)) {
                    this.checkName = this.buyDate + ", " + this.buyTime;
                }else if(this.buyDate!= null){
                    this.checkName = this.buyDate;
                }else if(this.buyTime!=null){
                    this.checkName = this.buyTime;
                }else{
                    this.checkName = CHECK_NAME_UNDEFINED;
                }
            }
            checkObject.setCheckName(this.checkName);
            return checkObject;
        }
    }


}