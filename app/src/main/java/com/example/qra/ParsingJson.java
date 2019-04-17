package com.example.qra;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * class responsible for recognizing JSON strings
 *
 * @author: Marina Alekseeva
 */


//I added only what I thought was necessary (for example, I did not add the name of the cashier)


public class ParsingJson {

    /**
     * constants for calling a check in the format JSON
     */
    private static final String STRING_DOCUMENT = "document";
    private static final String STRING_RECEIPT = "receipt";
    private static final String STRING_TOTAL_SUM = "totalSum";
    private static final String USER_INN = "userInn";
    private static final String STRING_NDS_18 = "nds18";
    private static final String STRING_NDS_10 = "nds10";
    private static final String STRING_RETAIL_PLACE_ADDRESS = "retailPlaceAddress";
    private static final String STRING_DATE_TIME = "dateTime";//
    private static final String STRING_ITEMS = "items";
    private static final String STRING_QUANTITY = "quantity";
    private static final String STRING_SUM = "sum";
    private static final String STRING_NAME = "name";

    /**
     * @param stringJSON
     * @return the object that stores check information
     * @throws ParsingJsonException an object of this class has an attribute with an error message
     */
    public static CheckInformationStorage GetObjectFromParsingJsonString(String stringJSON) throws ParsingJsonException {
        CheckInformationStorage tempObject;

        tempObject = new CheckInformationStorage();
        JSONObject jsonResponse = null;
        try {
            jsonResponse = new JSONObject(stringJSON);
            JSONObject document = jsonResponse.getJSONObject(STRING_DOCUMENT);
            JSONObject receipt = document.getJSONObject(STRING_RECEIPT);

            tempObject.setTotalSum(receipt.getInt(STRING_TOTAL_SUM));
            tempObject.setInn(receipt.getString(USER_INN));
            tempObject.setPaiedNdsSum(receipt.getInt(STRING_NDS_18) + receipt.getInt(STRING_NDS_10));
            tempObject.setAddresOfPurchase(receipt.getString(STRING_RETAIL_PLACE_ADDRESS));
            tempObject.setBuyTime(receipt.getString(STRING_DATE_TIME));

            JSONArray items = receipt.getJSONArray(STRING_ITEMS);

            tempObject.setQuantityPurchases(items.length());

            ItemsList[] shopingList = new ItemsList[items.length()];
            for (int i = 0; i < tempObject.getQuantityPurchases(); i++) {
                shopingList[i] = new ItemsList();
            }

//            for (int i = 0; i < object.getQuantityPurchases(); i++) {
//                object.setNameInShoppingListArray(i, items.getJSONObject(i).getString(STRING_NAME));
//                object.setQuantityOfGoodsWithThisNameInShoppingListArray(i,
//                        items.getJSONObject(i).getInt(STRING_QUANTITY));
//                object.setPriseInShoppingListArray(i, items.getJSONObject(i).getInt(STRING_SUM));
//            }
            for (int i = 0; i < tempObject.getQuantityPurchases(); i++) {
                shopingList[i].setName(items.getJSONObject(i).getString(STRING_NAME));
                shopingList[i].setPrice(items.getJSONObject(i).getInt(STRING_SUM));
                shopingList[i].setQuantityOfGoodsWithThisName(
                        items.getJSONObject(i).getInt(STRING_QUANTITY));
            }
            tempObject.setShoppingList(shopingList);

        } catch (JSONException e) {
            e.printStackTrace();
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            String mesegeError[] = errors.toString().split(":", 2);
            String mesegeErrorFinal[] = mesegeError[1].split("\n", 2);

            // здесь будет выведено, где возникла ошибка и будет брошено
            // исключение, с полем, содержащем информацию об этом
            throw new ParsingJsonException(mesegeErrorFinal[0]);

        }
        return tempObject;


    }


}
