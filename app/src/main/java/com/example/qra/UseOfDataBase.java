package com.example.qra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


//я не думаю, что объединять классы, хранящие бд и использующие - хорошая идея
//на данный момент, для добавления/озврата необхдимо создать обьект класса,
//нужно исправить -> исправлено
//проверить -> it is work

/**
 * class responsible for using of data base
 *
 * @author: Marina Alekseeva
 */
public class UseOfDataBase {

    // экземпляр бд
    private static DataBase dataBase;
    private static SQLiteDatabase sqLiteDatabase;


    //используется для добавления новых строк в таблицу, каждый объект этого класса
    //представляет собой одну троку таблицы и выглядит как массив с именами столбцов и значениями
    //которые им соответствуют
    private static ContentValues contentValues;


    //    //прост нам нужен контекст
//    public UseOfDataBase(Context context) {
//        this.context = context;
//        dataBase = new DataBase(this.context);
//        //передаем этой переменной возможность редактировать бд
//        sqLiteDatabase = dataBase.getWritableDatabase();
//        contentValues = new ContentValues();
//    }
//прост нам нужен контекст

    private static void initialization(Context context) {

        dataBase = new DataBase(context);
        //передаем этой переменной возможность редактировать бд
        sqLiteDatabase = dataBase.getWritableDatabase();
        contentValues = new ContentValues();
    }

//    private void logCursor(Cursor c) {
////        if (c != null) {
////            if (c.moveToFirst()) {
////                String str;
////                do {
////                    str = "";
////                    for (String cn : c.getColumnNames()) {
////                        str = str.concat(cn + " = " + c.getString(c.getColumnIndex(cn)) + "; ");
////                    }
////                    Log.d("log", str);
////                } while (c.moveToNext());
////            }
////        } else
////            Log.d("log", "Cursor is null");
////    }

    /**
     * insert in data base
     *
     * @param object  class storing the recognized JSON string
     * @param context
     */
    public static void insert(CheckInformationStorage object, Context context) {
        initialization(context);
        //след строчка используется после изменения структуры бд, для создания таблиц
        //dataBase.onUpgrade(sqLiteDatabase,1,1);
        contentValues.put(dataBase.COLUMN_NAME_TOTAL_SUM, object.getTotalSum());
        contentValues.put(dataBase.COLUMN_NAME_INN, object.getInn());
        contentValues.put(dataBase.COLUMN_NAME_NDS, object.getPaidNdsSum());
        contentValues.put(dataBase.COLUMN_NAME_ADDRESS, object.getAddressOfPurchase());
        contentValues.put(dataBase.COLUMN_NAME_TIME, object.getBuyTime());
        contentValues.put(dataBase.COLUMN_NAME_QUANTITY_PURCHASES, object.getQuantityPurchases());

        sqLiteDatabase.insert(dataBase.TABLE_NAME_CHECK_LIST, null, contentValues);
        contentValues.clear();


        Cursor cursor = sqLiteDatabase.query(dataBase.TABLE_NAME_CHECK_LIST, null,
                null, null, null, null, null);
        cursor.moveToLast();
        int idIndex = cursor.getColumnIndex("_id");
        int _id = cursor.getInt(idIndex);


        cursor.close();

        for (int i = 0; i < object.getQuantityPurchases(); i++) {
            contentValues.put(dataBase.COLUMN_NAME_NAME, object.getNameInShoppingList(i));
            contentValues.put(dataBase.COLUMN_NAME_QUANTITY, object.getQuantityOfGoodsWithThisNameInShoppingList(i));
            contentValues.put(dataBase.COLUMN_NAME_PRISE, object.getPriseInShoppingList(i));
            contentValues.put(dataBase.COLUMN_NAME_CATEGORIES, object.getCategoryInShoppingList(i));
            contentValues.put("checkList_id", _id);

            //второй аргумент используется для вставки пустой строки - это не нужно сейчас
            sqLiteDatabase.insert(dataBase.TABLE_NAME_SHOP_LIST, null, contentValues);
            contentValues.clear();
        }

        // Log.d("log", "Data inserted");
    }


    /**
     * delete all items in date base
     *
     * @param context
     */
    public static void delete(Context context) {
        initialization(context);
        sqLiteDatabase.delete(dataBase.TABLE_NAME_SHOP_LIST, null, null);
        sqLiteDatabase.delete(dataBase.TABLE_NAME_CHECK_LIST, null, null);
        // Log.d("log", "Data deleted");
    }

//    public void showShopList() {
//
//        //данные без сортировок и групировок
//        Cursor cursor = chengerShopListWrite.query(DBShopList.SHOP, null,
//                null, null, null, null, null);
//        logCursor(cursor);
//        cursor.close();
//    }

    /**
     * get all your goods
     *
     * @param context
     * @return shoppingList
     */
    public static BoughtItem[] getShoppingList(Context context) {
        initialization(context);

        Cursor cursor = sqLiteDatabase.query(dataBase.TABLE_NAME_SHOP_LIST, null,
                null, null, null, null, null);
        BoughtItem[] shoppingList = new BoughtItem[cursor.getCount()];
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            int priseIndex = cursor.getColumnIndex(dataBase.COLUMN_NAME_PRISE);
            int nameIndex = cursor.getColumnIndex(dataBase.COLUMN_NAME_NAME);
            int quantityIndex = cursor.getColumnIndex(dataBase.COLUMN_NAME_QUANTITY);
            int categoryIndex = cursor.getColumnIndex(dataBase.COLUMN_NAME_CATEGORIES);

            shoppingList[i] = new BoughtItem(cursor.getString(nameIndex),
                    cursor.getInt(priseIndex), cursor.getInt(quantityIndex));
            shoppingList[i].setCategory(cursor.getString(categoryIndex));

            cursor.moveToNext();
        }
        return shoppingList;
    }

    /**
     * get all your checks
     *
     * @param context
     * @return checkList
     */
    public static CheckInformationStorage[] getCheckList(Context context) {
        initialization(context);

        Cursor cursor = sqLiteDatabase.query(dataBase.TABLE_NAME_CHECK_LIST, null,
                null, null, null, null, null);
        CheckInformationStorage[] checkList = new CheckInformationStorage[cursor.getCount()];
        cursor.moveToFirst();

        Cursor cursorShop = sqLiteDatabase.query(dataBase.TABLE_NAME_SHOP_LIST, null,
                null, null, null, null, null);
        cursorShop.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            int sumIndex = cursor.getColumnIndex(dataBase.COLUMN_NAME_TOTAL_SUM);
            int innIndex = cursor.getColumnIndex(dataBase.COLUMN_NAME_INN);
            int ndsIndex = cursor.getColumnIndex(dataBase.COLUMN_NAME_NDS);
            int adressIndex = cursor.getColumnIndex(dataBase.COLUMN_NAME_ADDRESS);
            int timeIndex = cursor.getColumnIndex(dataBase.COLUMN_NAME_TIME);
            int quantityALlPurchasesIndex = cursor.getColumnIndex(dataBase.COLUMN_NAME_QUANTITY_PURCHASES);

            int idIndex = cursor.getColumnIndex("_id"); //

            checkList[i] = new CheckInformationStorage();
            checkList[i].setTotalSum(cursor.getInt(sumIndex));
            checkList[i].setInn(cursor.getString(innIndex));
            checkList[i].setPaidNdsSum(cursor.getInt(ndsIndex));
            checkList[i].setAddressOfPurchase(cursor.getString(adressIndex));
            checkList[i].setBuyTime(cursor.getString(timeIndex));
            checkList[i].setQuantityPurchases(cursor.getInt(quantityALlPurchasesIndex));


            //разобраться, что буде, если позиция удалена и кол-во товаров в чеке не соответствует
            //кол-ву позиций (проблема только в том, что массив будет от большего числа)
            BoughtItem[] shoppingList = new BoughtItem[checkList[i].getQuantityPurchases()];

            int checkList_idIndex = cursorShop.getColumnIndex("checkList_id"); //

            for (int j = 0; (j < checkList[i].getQuantityPurchases()) && //; j++)// &&
                    (cursorShop.getInt(checkList_idIndex) == cursor.getInt(idIndex)); j++) {
                int priseIndex = cursorShop.getColumnIndex(dataBase.COLUMN_NAME_PRISE);
                int nameIndex = cursorShop.getColumnIndex(dataBase.COLUMN_NAME_NAME);
                int quantityIndex = cursorShop.getColumnIndex(dataBase.COLUMN_NAME_QUANTITY);
                int categoryIndex = cursorShop.getColumnIndex(dataBase.COLUMN_NAME_CATEGORIES);

                checkList_idIndex = cursorShop.getColumnIndex("checkList_id"); //

                shoppingList[j] = new BoughtItem(cursorShop.getString(nameIndex),
                        cursorShop.getInt(priseIndex), cursorShop.getInt(quantityIndex));
                shoppingList[j].setCategory(cursorShop.getString(categoryIndex));

                cursorShop.moveToNext();
            }
            checkList[i].setShoppingList(shoppingList);


            cursor.moveToNext();
        }
        return checkList;
    }


//    public void showCheck() {
//        Cursor cursor = chengerShopListWrite.query(DBShopList.CHECK, null,
//                null, null, null, null, null);
//
//        logCursor(cursor);
//        cursor.close();
//    }
}
