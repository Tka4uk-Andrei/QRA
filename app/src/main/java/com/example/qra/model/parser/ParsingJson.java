package com.example.qra.model.parser;


import com.example.qra.model.check.CheckItem;
import com.example.qra.model.check.CheckInformationStorage;

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
    private static final String STRING_NDS_20_JSON_FIELD = "nds20";
    private static final String STRING_NDS_18_JSON_FIELD = "nds18";
    private static final String STRING_NDS_10_JSON_FIELD = "nds10";
    private static final String STRING_RETAIL_PLACE_ADDRESS_JSON_FIELD = "retailPlaceAddress";
    private static final String STRING_DATE_TIME_JSON_FIELD = "dateTime";//
    private static final String STRING_ITEMS_JSON_FIELD = "items";
    private static final String STRING_QUANTITY_JSON_FIELD = "quantity";
    private static final String STRING_SUM_JSON_FIELD = "sum";
    private static final String STRING_NAME_JSON_FIELD = "name";

    private static final String STRING_FISCAL_DOCUMENT_NUMBER_JSON_FIELD = "fiscalDocumentNumber";
    private static final String STRING_FISCAL_DRIVE_NUMBER_JSON_FIELD = "fiscalDriveNumber";
    private static final String STRING_FISCAL_SIGN_JSON_FIELD = "fiscalSign";

    /**
     * @param stringJSON
     * @return the object that stores check information
     * @throws ParsingJsonException an object of this class has an attribute with an error message
     */
    public static CheckInformationStorage ParseJson(String stringJSON) throws ParsingJsonException {

        CheckInformationStorage tempObject;
        JSONObject jsonResponse;

        try {
            jsonResponse = new JSONObject(stringJSON);
            JSONObject document = jsonResponse.getJSONObject(STRING_DOCUMENT_JSON_FIELD);
            JSONObject receipt = document.getJSONObject(STRING_RECEIPT_JSON_FIELD);

            JSONArray items = receipt.getJSONArray(STRING_ITEMS_JSON_FIELD);
            CheckItem[] shoppingList = new CheckItem[items.length()];
            for (int i = 0; i < items.length(); i++) {
                shoppingList[i] = new CheckItem.Builder()
                        .setName(items.getJSONObject(i).getString(STRING_NAME_JSON_FIELD))
                        .setPrice(items.getJSONObject(i).getInt(STRING_SUM_JSON_FIELD))
                        .setQuantity(items.getJSONObject(i).getInt(STRING_QUANTITY_JSON_FIELD))
                .build();
            }

            int nds = 0;
            try {
                nds += receipt.getInt(STRING_NDS_20_JSON_FIELD);
            } catch (JSONException e) {
            }
            try {
                nds += receipt.getInt(STRING_NDS_18_JSON_FIELD);
            } catch (JSONException e) {
            }
            try {
                nds += receipt.getInt(STRING_NDS_10_JSON_FIELD);
            } catch (JSONException e) {
            }
            // split("T", 2) split line "2019-04-20T12:51:00" by character "T"
            String[] timeDate = receipt.getString(STRING_DATE_TIME_JSON_FIELD).split("T", 2);

            tempObject = new CheckInformationStorage.Builder()
                    .setTotalSum(receipt.getInt(STRING_TOTAL_SUM_JSON_FIELD))
                    .setInn(receipt.getString(USER_INN_JSON_FIELD))
                    .setPaidNdsSum(nds)
                    .setAddressOfPurchase(receipt.getString(STRING_RETAIL_PLACE_ADDRESS_JSON_FIELD))
                    .setBuyTime(timeDate[1])
                    .setBuyDate(timeDate[0])
                    .setFiscalDocumentNumber(receipt.getInt(STRING_FISCAL_DOCUMENT_NUMBER_JSON_FIELD))
                    .setFiscalDriveNumber(receipt.getString(STRING_FISCAL_DRIVE_NUMBER_JSON_FIELD))
                    .setFiscalSign(receipt.getInt(STRING_FISCAL_SIGN_JSON_FIELD))
                    .setQuantityPurchases(items.length())
                    .setShoppingList(shoppingList)
                    .setObtainingMethod(CheckInformationStorage.OBTAIN_METHOD_FNS)
                    .build();

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
