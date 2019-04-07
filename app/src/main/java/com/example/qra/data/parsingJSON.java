package com.example.qra.data;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * class responsible for recognizing JSON strings
 * the output will be filled in fields with information about the check:
 *   totalSum - the amount of the purchase;
 *   nds - tax amount;
 *   quantity Purchases - number of products;
 *   address - the address of purchase;
 *   buyTime - the time of purchase;
 *   namesAndPricesArrey[i].name - the name of the product
 *   namesAndPricesArrey[i].price - its price
 *  Author: Marina Alekseeva
 */


//I added only what I thought was necessary (for example, I did not add the name of the cashier)

public class parsingJSON {

    public class namesAndPrices{
        public String name;
        public int price;
        namesAndPrices(){ name = "-"; price =0;}
    }

    public int totalSum;
    public int nds;
    public int quantityPurchases;
    public String adress;
    public String buyTime;
    namesAndPrices[] namesAndPricesArrey;


    /**
     * method responsible for counting the number of goods purchased
     * @param arrayOfItems
     * @return length of purchases
     */
    public int length(JSONArray arrayOfItems) {
   int len = 0;
    try {
        for(len = 0; len > -1; len++){ // цикл пока не закончатся объекты
            JSONObject val =  arrayOfItems.getJSONObject(len);
            len += val.getInt("quantity") - 1;
        }

    } catch (JSONException e) {
        e.printStackTrace();
    }
    return len;
}

    /**
     * field constructor
     * @param stringJSON
     */
    parsingJSON(String stringJSON){
        try {
            JSONObject jsonResponse = new JSONObject(stringJSON);
            JSONObject document = jsonResponse.getJSONObject("document");
            JSONObject receipt = document.getJSONObject("receipt");

            totalSum = receipt.getInt("ecashTotalSum");
            nds = receipt.getInt("nds18") + receipt.getInt("nds10");
            adress = receipt.getString("retailPlaceAddress");
            buyTime = receipt.getString("dateTime");

            JSONArray items = receipt.getJSONArray("items");
            quantityPurchases = length(items);


            namesAndPricesArrey = new namesAndPrices[quantityPurchases];
            for (int i = 0; i < quantityPurchases; i++){
                namesAndPricesArrey[i] = new namesAndPrices();
            }

            for (int i = 0;i < quantityPurchases; i++) {
                int quant = items.getJSONObject(i).getInt("quantity");
                for (int j = 0; j < quant; j ++){
                    namesAndPricesArrey[i].price = items.getJSONObject(i).getInt("price");
                    namesAndPricesArrey[i].name = items.getJSONObject(i).getString("name");
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
