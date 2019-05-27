package com.example.qra.model.check;


import com.example.qra.CategoryDefinition;
import com.example.qra.ItemCategories;

import java.util.Objects;

/**
 * This class is responsible for storing information about the goods purchased
 * (the name of the product and its price of the product)
 *
 * @author: Marina Alekseeva
 */
public class CheckItem {

    /**
     * name of the product
     */
    private String name;

    /**
     * price of the this products
     */
    private int price;

    /**
     * number of goods with the given name
     */
    private int quantity;

    /**
     * What general category does the product belong to
     */
    private String generalCategory;

    /**
     * What subject category does the product belong to
     */
    private String subjectCategory;


    /**
     * product name for user
     */
    private String nameForUser;

    /**
     * tracking ID
     */
    private int id;


    /**
     * @return tracking ID
     */
    public int getId() {
        return id;
    }

    /**
     * @return product name for user
     */
    public String getNameForUser() {
        return nameForUser;
    }


    /**
     * This function sets product name for user
     *
     * @param nameForUser - product name for user
     */
    public void setNameForUser(String nameForUser) {
        this.nameForUser = nameForUser;
        if(Objects.equals(this.generalCategory, ItemCategories.NOT_DISTRIBUTED) && (nameForUser!=null)){
            this.generalCategory = CategoryDefinition.defineGeneralCategory(this.nameForUser);
            if(this.generalCategory.equals(ItemCategories.CATEGORY_EAT)){
                this.subjectCategory = CategoryDefinition.defineSubjectEatCategory(this.nameForUser);
            }
        }
    }


    /**
     * @param id - tracking ID
     */
    private CheckItem(int id) {
        this.id = id;

    }


    /**
     * This function sets quantity of goods with the given name
     *
     * @param quantity number items in the list of products in the order
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    /**
     * @return quantity of goods with the given name
     */
    public int getQuantity() {
        return quantity;
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
        if (this.nameForUser == null) {
            setNameForUser(name);
        }
    }

    /**
     * This function sets the price of the this products
     *
     * @param price
     */
    public void setPrice(int price) {
        this.price = price;
    }


    /**
     * @return what general category does the product belong to
     */
    public String getGeneralCategory() {
        return generalCategory;
    }


    /**
     * This function sets what general category does the product belong to
     *
     * @param generalCategory
     */
    public void setGeneralCategory(String generalCategory) {
        this.generalCategory = generalCategory;
    }

    /**
     * @return what subject category does the product belong to
     */
    public String getSubjectCategory() {
        return subjectCategory;
    }


    /**
     * This function sets what subject category does the product belong to
     *
     * @param subjectCategory
     */
    public void setSubjectCategory(String subjectCategory) {
        this.subjectCategory = subjectCategory;
    }


    public static class Builder {


        /**
         * name of the product
         */
        private String name;

        /**
         * price of the this products
         */
        private int price;

        /**
         * number of goods with the given name
         */
        private int quantity;

        /**
         * What general category does the product belong to
         */
        private String generalCategory = ItemCategories.NOT_DISTRIBUTED;

        /**
         * What subject category does the product belong to
         */
        private String subjectCategory = ItemCategories.NOT_DISTRIBUTED;


        /**
         * product name for user
         */
        private String nameForUser;

        /**
         * tracking ID
         */
        private int id = -1;


        /**
         * @param id - tracking ID
         * @return object with a filled field
         */
        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        /**
         * This function sets product name for user
         *
         * @param nameForUser - product name for user
         * @return object with a filled field
         */
        public Builder setNameForUser(String nameForUser) {
            this.nameForUser = nameForUser;
            return this;
        }


        /**
         * This function sets quantity of goods with the given name
         *
         * @param quantity number items in the list of products in the order
         * @return object with a filled field
         */
        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }


        /**
         * This function sets the name of the product.
         *
         * @param name
         * @return object with a filled field
         */
        public Builder setName(String name) {
            this.name = name;
            if (this.nameForUser == null) {
                this.nameForUser = name;
            }
            return this;
        }

        /**
         * This function sets the price of the this products
         *
         * @param price
         * @return object with a filled field
         */
        public Builder setPrice(int price) {
            this.price = price;
            return this;
        }


        /**
         * This function sets what general category does the product belong to
         *
         * @param generalCategory
         * @return object with a filled field
         */
        public Builder setGeneralCategory(String generalCategory) {
            this.generalCategory = generalCategory;
            return this;
        }


        /**
         * This function sets what subject category does the product belong to
         *
         * @param subjectCategory
         * @return object with a filled field
         */
        public Builder setSubjectCategory(String subjectCategory) {
            this.subjectCategory = subjectCategory;
            return this;
        }


        public CheckItem build() {

            CheckItem checkItemObject = new CheckItem(this.id);
            if((Objects.equals(this.generalCategory, ItemCategories.NOT_DISTRIBUTED))&&
                    (nameForUser != null)){
                this.generalCategory = CategoryDefinition.defineGeneralCategory(this.nameForUser);
                if(this.generalCategory.equals(ItemCategories.CATEGORY_EAT)){
                    this.subjectCategory = CategoryDefinition.defineSubjectEatCategory(this.nameForUser);
                }
            }
            checkItemObject.setGeneralCategory(this.generalCategory);
            checkItemObject.setSubjectCategory(this.subjectCategory);
            checkItemObject.setName(this.name);
            checkItemObject.setNameForUser(this.nameForUser);
            checkItemObject.setPrice(this.price);
            checkItemObject.setQuantity(this.quantity);

            return checkItemObject;
        }

    }
}
