package com.example.qra;

import android.content.Context;
import android.content.Intent;

import com.example.qra.model.check.CheckInformationStorage;
import com.example.qra.model.check.CheckItem;
import com.example.qra.model.parser.ParsingJson;
import com.example.qra.model.parser.ParsingJsonException;

public class Test {

    public static void test(Context context) {
        CheckDataBase.delete(context);

        String json ="{\n" +
                "  \"document\": {\n" +
                "    \"receipt\": {\n" +
                "      \"nds10\": 2355,\n" +
                "      \"ecashTotalSum\": 41090,\n" +
                "      \"rawData\": \"AwC1AREEEAA5MjUxNDQwMzAwMDAzODExDQQUADAwMDAwNDY2NTkwMzE5MjkgICAg+gMMADc4NDIwMDU4MTMgIBAEBADzIgAA9AMEAGD5SVw1BAYAMQS8lXtmDgQEACkAAAASBAQAFAEAAB4EAQAB/AMCAIKg/QMUAIGgquLroaWqrqKgIDEwMzA3MCAuIwRKAAYEKQCKrquhoOGgIKKg4CCErqriruDhqqDvIIOOkZIgNTAwoyCOjI+KIK8vrjcEAgAsZf8DAwAD6AOvBAEAAr4EAQAEEwQCACxlIwRKAAYEKQCMrqTjq+wg4aylra3rqSCAqqKg5K7gIIIxMDAtMTUgKOHioK2koODiKTcEAgBWO/8DAwAD6AOvBAEAAb4EAQAEEwQCAFY7TgQCAOQJTwQCADMJGAQSAI6OjiAikoQgiK3ipeDiruCjIvEDKwAxOTQyMTQgoy4gkaCtquItj6XipeCh4+CjIK/gLiCSruClp6AsIKQuIDg3owQHAKygo6CnqK0fBAEAASQEDAB3d3cubmFsb2cucnUHBAEAADkEAgCCoL8EAQAAwAQBAADBBAEAALkEAQAC\",\n" +
                "      \"kktRegId\": \"0000046659031929    \",\n" +
                "      \"retailPlaceAddress\": \"194214 г. Санкт-Петербург пр. Тореза, д. 87\",\n" +
                "      \"operationType\": 1,\n" +
                "      \"fiscalDocumentNumber\": 8947,\n" +
                "      \"cashTotalSum\": 0,\n" +
                "      \"dateTime\": \"2019-01-24T17:44:00\",\n" +
                "      \"operator\": \"Бактыбекова 103070 .\",\n" +
                "      \"items\": [\n" +
                "        {\n" +
                "          \"price\": 25900,\n" +
                "          \"quantity\": 1,\n" +
                "          \"name\": \"Колбаса вар Докторская ГОСТ 500г ОМПК п/о\",\n" +
                "          \"sum\": 25900\n" +
                "        },\n" +
                "        {\n" +
                "          \"price\": 15190,\n" +
                "          \"quantity\": 1,\n" +
                "          \"name\": \"Модуль сменный Аквафор В100-15 (стандарт)\",\n" +
                "          \"sum\": 15190\n" +
                "        }\n" +
                "      ],\n" +
                "      \"receiptCode\": 3,\n" +
                "      \"requestNumber\": 276,\n" +
                "      \"taxationType\": 1,\n" +
                "      \"totalSum\": 41090,\n" +
                "      \"userInn\": \"7842005813\",\n" +
                "      \"nds18\": 2532,\n" +
                "      \"fiscalDriveNumber\": \"9251440300003811\",\n" +
                "      \"shiftNumber\": 41,\n" +
                "      \"fiscalSign\": 3163913062,\n" +
                "      \"user\": \"ООО \\\"ТД Интерторг\\\"\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
       CheckInformationStorage obj = null;
        try {
            obj = ParsingJson.ParseJson(json);
        } catch (ParsingJsonException e) {
            e.printStackTrace();
        }
        try {
            CheckInformationStorage po = new CheckInformationStorage.Builder()
                    .setAddressOfPurchase("12").build();
            po.setBuyDate("1");
            CheckDataBase.insert(obj, context);
            CheckDataBase.insert(po, context);
            CheckItem one = new CheckItem.Builder()
                    .setPrice(12).build();
            one.setName("Колбаса 12кг");
            CheckItem[]arr = new  CheckItem[2];
            arr[0] = one;
            arr[1] = one;
            po.setShoppingList(arr);

            CheckDataBase.insert(po, context);
        } catch (CheckAddingException e) {
            e.printStackTrace();
        }



//
//        CheckInformationStorage ot = new CheckInformationStorage.Builder()
//                .setAddressOfPurchase("12").build();
//
//        //CheckDataBase.insert(ot,context);
//        String r;
//
//
//        CheckInformationStorage [] arr = CheckDataBase.getCheckList(context);
//        try {
//            CheckDataBase.updateAllPositionFnsCheck(context,arr[0]);
//            CheckDataBase.updateAllPositionUserCheck(context,arr[1]);
//        } catch (CheckEditingException e) {
//r = e.getErrorMessage();
//        }
        //CheckItem [] yy = CheckDataBase.getShoppingList(context, ItemCategories.NOT_DISTRIBUTED, ItemCategories.NOT_DISTRIBUTED);

        //CheckInformationStorage [] arr2 = CheckDataBase.getCheckList(context);

    }
}
