package com.example.qra;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * class responsible for recognizing JSON strings
 * Author: Marina Alekseeva
 */


//I added only what I thought was necessary (for example, I did not add the name of the cashier)

public class ParsingJSON {


    private int totalSum;
    private int paiedNdsSum;
    private int quantityPurchases;
    private String addresOfPurchase;
    private String buyTime;
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
        return totalSum;
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
     * method responsible for counting the number of goods purchased
     *
     * @param arrayOfItems
     * @return quantity of purchases
     */
    public int countingTheNumberOfGoods(JSONArray arrayOfItems) {
        int quantityGoodsPurchased = 0;
        try {
            for (; quantityGoodsPurchased > -1; quantityGoodsPurchased++) { // цикл пока не закончатся объекты
                JSONObject val = arrayOfItems.getJSONObject(quantityGoodsPurchased);
                quantityGoodsPurchased += val.getInt("quantity") - 1;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return quantityGoodsPurchased;
    }

    /**
     * field constructor
     *
     * @param stringJSON
     */
    ParsingJSON(String stringJSON) {
        try {
            JSONObject jsonResponse = new JSONObject(stringJSON);
            JSONObject document = jsonResponse.getJSONObject("document");
            JSONObject receipt = document.getJSONObject("receipt");

            totalSum = receipt.getInt("ecashTotalSum");
            paiedNdsSum = receipt.getInt("nds18") + receipt.getInt("nds10");
            addresOfPurchase = receipt.getString("retailPlaceAddress");
            buyTime = receipt.getString("dateTime");

            JSONArray items = receipt.getJSONArray("items");
            quantityPurchases = countingTheNumberOfGoods(items);


            ShoppingListArray = new ShoppingList[quantityPurchases];
            for (int i = 0; i < quantityPurchases; i++) {
                ShoppingListArray[i] = new ShoppingList();
            }

            for (int i = 0; i < quantityPurchases; i++) {
                int quantityOfGoodsOfOneType = items.getJSONObject(i).getInt("quantity");
                for (int j = 0; j < quantityOfGoodsOfOneType; j++) {
                    ShoppingListArray[i].setPrice(items.getJSONObject(i).getInt("price"));
                    ShoppingListArray[i].setName(items.getJSONObject(i).getString("name"));
                }
            }


        } catch (JSONException e) {
            //e.printStackTrace();
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            //  return errors.toString();
        }

    }


}
