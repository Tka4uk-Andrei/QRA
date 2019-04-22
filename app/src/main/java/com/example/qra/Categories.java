package com.example.qra;

/**
 * class responsible for defining Category
 *
 * @author: Marina Alekseeva
 */
public class Categories {
    public static final String FRUITS = "fruits";
    public static final String BERRIES = "berries";
    public static final String VEGETABLES = "vegetables";
    public static final String GROCERY = "grocery";
    public static final String CANDIES = "candies";
    public static final String ALCOGOLIC_AND_ENERGY_DRINCS = "alcoholic and energy drinks";
    public static final String MILK = "milk";
    public static final String DRINKS_HOT = "hot drinks";
    public static final String MEAT = "meat";

    //дописать распределение по категориям
    public static String defineCategory(String name) {
        return FRUITS;
    }
}
