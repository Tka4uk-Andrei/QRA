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

    /**
     * attribute object that stores check information
     */
    CheckInformationStorage object;


    /**
     * method responsible for counting the number of goods purchased
     *
     * @param arrayOfItems
     * @return quantity of purchases
     */
    private int countingTheNumberOfGoods(JSONArray arrayOfItems) {
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
    public ParsingJSON(String stringJSON) {
        object = new CheckInformationStorage();
        try {
            JSONObject jsonResponse = new JSONObject(stringJSON);
            JSONObject document = jsonResponse.getJSONObject("document");
            JSONObject receipt = document.getJSONObject("receipt");

            object.setTotalSum(receipt.getInt("ecashTotalSum"));
            object.setPaiedNdsSum(receipt.getInt("nds18") + receipt.getInt("nds10"));
            object.setAddresOfPurchase(receipt.getString("retailPlaceAddress"));
            object.setBuyTime(receipt.getString("dateTime"));

            JSONArray items = receipt.getJSONArray("items");

            object.setQuantityPurchases(countingTheNumberOfGoods(items));
            object.declareAnArray();

            for (int i = 0; i < object.getQuantityPurchases(); i++) {
                int quantityOfGoodsOfOneType = items.getJSONObject(i).getInt("quantity");
                for (int j = 0; j < quantityOfGoodsOfOneType; j++) {
                    object.setPriseInShoppingListArray(i, items.getJSONObject(i).getInt("price"));
                    object.setNameInShoppingListArray(i, items.getJSONObject(i).getString("name"));
                }
            }


        } catch (JSONException e) {
            //e.printStackTrace();
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            //  return errors.toString();
        }

    }


    /**
     * @return check information object
     */
    public CheckInformationStorage getObject() {
        return object;
    }
}
