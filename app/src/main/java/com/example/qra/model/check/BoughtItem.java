package com.example.qra.model.check;


import com.example.qra.ItemCategories;

/**
 * This class is responsible for storing information about the goods purchased
 * (the name of the product and its price of the product)
 *
 * @author: Marina Alekseeva
 */
public class BoughtItem {

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
    }


    /**
     * @param name     - product name
     * @param price    - price of the this products
     * @param quantity - number of goods with the given name
     */
    public BoughtItem(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.generalCategory = ItemCategories.NOT_DISTRIBUTED;
        this.subjectCategory = ItemCategories.NOT_DISTRIBUTED;
        this.nameForUser = name; //TODO возможно, здесь нужно выывать функцию, котрая сопоставит имя товара имени для пользователя
    }

    /**
     * @param id              - tracking ID
     * @param name            - product name
     * @param nameForUser     - product name for user
     * @param price           - price of the this products
     * @param quantity        - number of goods with the given name
     * @param generalCategory - what general category does the product belong to
     * @param subjectCategory - what subject category does the product belong to
     */
    public BoughtItem(int id, String name, String nameForUser, int price, int quantity,
                      String generalCategory, String subjectCategory) {
        this.id = id;
        this.name = name;
        this.nameForUser = nameForUser;
        this.price = price;
        this.quantity = quantity;
        this.generalCategory = generalCategory;
        this.subjectCategory = subjectCategory;
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
}