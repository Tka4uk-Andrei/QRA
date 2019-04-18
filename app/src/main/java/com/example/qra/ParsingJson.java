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
    private static final String STRING_DOCUMENT_JSON_FIELD = "document";
    private static final String STRING_RECEIPT_JSON_FIELD = "receipt";
    private static final String STRING_TOTAL_SUM_JSON_FIELD = "totalSum";
    private static final String USER_INN_JSON_FIELD = "userInn";
    private static final String STRING_NDS_18_JSON_FIELD = "nds18";
    private static final String STRING_NDS_10_JSON_FIELD = "nds10";
    private static final String STRING_RETAIL_PLACE_ADDRESS_JSON_FIELD = "retailPlaceAddress";
    private static final String STRING_DATE_TIME_JSON_FIELD = "dateTime";//
    private static final String STRING_ITEMS_JSON_FIELD = "items";
    private static final String STRING_QUANTITY_JSON_FIELD = "quantity";
    private static final String STRING_SUM_JSON_FIELD = "sum";
    private static final String STRING_NAME_JSON_FIELD = "name";

    /**
     * @param stringJSON
     * @return the object that stores check information
     * @throws ParsingJsonException an object of this class has an attribute with an error message
     */
    public static CheckInformationStorage ParseJson(String stringJSON) throws ParsingJsonException {

        CheckInformationStorage tempObject = new CheckInformationStorage();
        JSONObject jsonResponse;

        try {
            jsonResponse = new JSONObject(stringJSON);
            JSONObject document = jsonResponse.getJSONObject(STRING_DOCUMENT_JSON_FIELD);
            JSONObject receipt = document.getJSONObject(STRING_RECEIPT_JSON_FIELD);

            tempObject.setTotalSum(receipt.getInt(STRING_TOTAL_SUM_JSON_FIELD));
            tempObject.setInn(receipt.getString(USER_INN_JSON_FIELD));
            tempObject.setPaidNdsSum(receipt.getInt(STRING_NDS_18_JSON_FIELD) +
                    receipt.getInt(STRING_NDS_10_JSON_FIELD));
            tempObject.setAddressOfPurchase(receipt.getString(STRING_RETAIL_PLACE_ADDRESS_JSON_FIELD));
            tempObject.setBuyTime(receipt.getString(STRING_DATE_TIME_JSON_FIELD));

            JSONArray items = receipt.getJSONArray(STRING_ITEMS_JSON_FIELD);

            tempObject.setQuantityPurchases(items.length());

            BoughtItem[] shoppingList = new BoughtItem[items.length()];
            for (int i = 0; i < tempObject.getQuantityPurchases(); i++) {
                shoppingList[i] = new BoughtItem(
                        items.getJSONObject(i).getString(STRING_NAME_JSON_FIELD),
                        items.getJSONObject(i).getInt(STRING_SUM_JSON_FIELD),
                        items.getJSONObject(i).getInt(STRING_QUANTITY_JSON_FIELD));
            }

            tempObject.setShoppingList(shoppingList);

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
