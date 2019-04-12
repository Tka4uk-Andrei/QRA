package com.example.qra;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;

import static java.lang.System.exit;


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
                JSONObject value = arrayOfItems.getJSONObject(quantityGoodsPurchased);
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
     * @throws ParsingJsonException an object of this class has an attribute with an error message
     */
    public ParsingJSON(String stringJSON) throws ParsingJsonException {
        String stringDocument = "document";
        String stringReceipt = "receipt";
        String stringEcashTotalSum = "ecashTotalSum";
        String stringNds18 = "nds18";
        String stringNds10 = "nds10";
        String stringRetailPlaceAddress = "retailPlaceAddress";
        String stringDateTime = "dateTime";//
        String stringItems = "items";
        String stringQuantity = "quantity";
        String stringSum = "sum";
        String stringName = "name";

        object = new CheckInformationStorage();
        JSONObject jsonResponse = null;
        try {
            jsonResponse = new JSONObject(stringJSON);
            JSONObject document = jsonResponse.getJSONObject(stringDocument);
            JSONObject receipt = document.getJSONObject(stringReceipt);

            object.setTotalSum(receipt.getInt(stringEcashTotalSum));
            object.setPaiedNdsSum(receipt.getInt(stringNds18) + receipt.getInt(stringNds10));
            object.setAddresOfPurchase(receipt.getString(stringRetailPlaceAddress));
            object.setBuyTime(receipt.getString(stringDateTime));

            JSONArray items = receipt.getJSONArray(stringItems);

            object.setQuantityPurchases(countingTheNumberOfGoods(items));
            object.declareAnArray();

            for (int i = 0; i < object.getQuantityPurchases(); i++) {
                object.setNameInShoppingListArray(i, items.getJSONObject(i).getString(stringName));
                object.setQuantityOfGoodsWithThisNameInShoppingListArray(i,
                        items.getJSONObject(i).getInt(stringQuantity));
                object.setPriseInShoppingListArray(i, items.getJSONObject(i).getInt(stringSum));
            }
        } catch (JSONException e) {
            //e.printStackTrace();
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            String mesegeError[] = errors.toString().split(":", 2);
            String mesegeErrorFinal[] = mesegeError[1].split("\n", 2);

            throw new ParsingJsonException(mesegeErrorFinal[0]); //здесь будет выведено, где
            //возникла ошибка и будет брошено исключение, с полем, содержащем информацию об этом


        }

//            StringWriter errors = new StringWriter();
//            e.printStackTrace(new PrintWriter(errors));
//           return errors.toString();
    }


    /**
     * @return check information object
     */
    public CheckInformationStorage getObject() {
        return object;
    }
}
