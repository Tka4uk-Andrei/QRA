package com.example.qra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.qra.model.check.BoughtItem;
import com.example.qra.model.check.CheckInformationStorage;


/**
 * class responsible for using of data base
 *
 * @author: Marina Alekseeva
 */
public class CheckDataBase {
    //сделала класс, хранящий бд внутренним
    private static class StorageCheckDataBase extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_Name = "dataBase";

        public static final String TABLE_NAME_SHOP_LIST = "shopList";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PRISE = "prise";
        public static final String COLUMN_NAME_QUANTITY = "quantity";


        public static final String TABLE_NAME_CHECK_LIST = "checkList";
        public static final String COLUMN_NAME_TOTAL_SUM = "totalSum";
        public static final String COLUMN_NAME_INN = "Inn";
        public static final String COLUMN_NAME_NDS = "paidNdsSum";
        public static final String COLUMN_NAME_ADDRESS = "addressOfPurchase";
        public static final String COLUMN_NAME_TIME = "buyTime";
        public static final String COLUMN_NAME_QUANTITY_PURCHASES = "quantityPurchases";
        public static final String COLUMN_NAME_CATEGORIES = "categories";


        public StorageCheckDataBase(Context context) {
            super(context, DATABASE_Name, null, DATABASE_VERSION);
        }

        // вызывается при первом создании бд
        @Override
        public void onCreate(SQLiteDatabase db) {

            //primary key autoincrement, это поле будет автоматически заполнятся самй бд
            db.execSQL("create table " + TABLE_NAME_CHECK_LIST + "(_id integer primary key autoincrement, "
                    + COLUMN_NAME_QUANTITY_PURCHASES + " integer, " + COLUMN_NAME_TOTAL_SUM + " integer, "
                    + COLUMN_NAME_NDS + " integer, " + COLUMN_NAME_TIME + " text, " + COLUMN_NAME_ADDRESS
                    + " text, " + COLUMN_NAME_INN + " text" + ")");


            //пока id в SHOP не нужно, но кто знает, что будет потом
            //внешний ключ привязан к первому полю чека (_id)
            db.execSQL("create table " + TABLE_NAME_SHOP_LIST + "(_id integer primary key autoincrement, "
                    + COLUMN_NAME_NAME + " text, " + COLUMN_NAME_QUANTITY + " integer, " + COLUMN_NAME_PRISE
                    + " integer," + COLUMN_NAME_CATEGORIES + " text, " + "checkList_id integer,"
                    + " foreign key(checkList_id) references  checkList(_id)" + ")");

        }

        // при изменении
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + TABLE_NAME_SHOP_LIST);
            db.execSQL("drop table if exists " + TABLE_NAME_CHECK_LIST);
            onCreate(db);

        }
    }

    // экземпляр бд
    private static StorageCheckDataBase dataBase;
    private static SQLiteDatabase sqLiteDatabase;

    //используется для добавления новых строк в таблицу, каждый объект этого класса
    //представляет собой одну троку таблицы и выглядит как массив с именами столбцов и значениями
    //которые им соответствуют
    private static ContentValues contentValues;


    private static void initialization(Context context) {

        dataBase = new StorageCheckDataBase(context);
        //передаем этой переменной возможность редактировать бд
        sqLiteDatabase = dataBase.getWritableDatabase();
        contentValues = new ContentValues();
    }


    /**
     * insert in data base
     *
     * @param checkObject class storing the recognized JSON string
     * @param context
     */
    public static void insert(CheckInformationStorage checkObject, Context context) {
        initialization(context);
        //след строчка используется после изменения структуры бд, для создания таблиц
        //dataBase.onUpgrade(sqLiteDatabase,1,1);
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_TOTAL_SUM, checkObject.getTotalSum());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_INN, checkObject.getInn());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_NDS, checkObject.getPaidNdsSum());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_ADDRESS, checkObject.getAddressOfPurchase());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_TIME, checkObject.getBuyTime());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_QUANTITY_PURCHASES, checkObject.getQuantityPurchases());

        sqLiteDatabase.insert(StorageCheckDataBase.TABLE_NAME_CHECK_LIST, null, contentValues);
        contentValues.clear();


        Cursor cursor = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_CHECK_LIST, null,
                null, null, null, null, null);
        cursor.moveToLast();

        int checkId = cursor.getInt(cursor.getColumnIndex("_id"));

        cursor.close();

        for (int i = 0; i < checkObject.getQuantityPurchases(); i++) {
            contentValues.put(StorageCheckDataBase.COLUMN_NAME_NAME, checkObject.getShoppingList()[i].getName());
            contentValues.put(StorageCheckDataBase.COLUMN_NAME_QUANTITY, checkObject.getShoppingList()[i].getQuantity());
            contentValues.put(StorageCheckDataBase.COLUMN_NAME_PRISE, checkObject.getShoppingList()[i].getPrice());
            contentValues.put(StorageCheckDataBase.COLUMN_NAME_CATEGORIES, checkObject.getShoppingList()[i].getCategory());
            contentValues.put("checkList_id", checkId);

            //второй аргумент используется для вставки пустой строки - это не нужно сейчас
            sqLiteDatabase.insert(StorageCheckDataBase.TABLE_NAME_SHOP_LIST, null, contentValues);
            contentValues.clear();
        }

    }


    /**
     * delete all items in date base
     *
     * @param context
     */
    public static void delete(Context context) {
        initialization(context);
        sqLiteDatabase.delete(StorageCheckDataBase.TABLE_NAME_SHOP_LIST, null, null);
        sqLiteDatabase.delete(StorageCheckDataBase.TABLE_NAME_CHECK_LIST, null, null);
    }


    /**
     * get all your goods
     *
     * @param context
     * @return shopping list
     */
    public static BoughtItem[] getShoppingList(Context context) {
        initialization(context);

        Cursor cursor = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_SHOP_LIST, null,
                null, null, null, null, null);
        BoughtItem[] shoppingList = new BoughtItem[cursor.getCount()];
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {

            shoppingList[i] = new BoughtItem(
                    cursor.getString(cursor.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_NAME)),
                    cursor.getInt(cursor.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_PRISE)),
                    cursor.getInt(cursor.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_QUANTITY)),
                    cursor.getString(cursor.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_CATEGORIES)));

            cursor.moveToNext();
        }

        cursor.close();
        return shoppingList;
    }

    /**
     * get all your checks
     *
     * @param context
     * @return check list
     */
    public static CheckInformationStorage[] getCheckList(Context context) {
        initialization(context);

        Cursor cursorCheck = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_CHECK_LIST, null,
                null, null, null, null, null);
        CheckInformationStorage[] checkList = new CheckInformationStorage[cursorCheck.getCount()];
        cursorCheck.moveToFirst();

        Cursor cursorShop = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_SHOP_LIST, null,
                null, null, null, null, null);
        cursorShop.moveToFirst();

        for (int i = 0; i < cursorCheck.getCount(); i++) {

            checkList[i] = new CheckInformationStorage();
            checkList[i].setTotalSum(cursorCheck.getInt(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_TOTAL_SUM)));
            checkList[i].setInn(cursorCheck.getString(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_INN)));
            checkList[i].setPaidNdsSum(cursorCheck.getInt(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_NDS)));
            checkList[i].setAddressOfPurchase(cursorCheck.getString(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_ADDRESS)));
            checkList[i].setBuyTime(cursorCheck.getString(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_TIME)));
            checkList[i].setQuantityPurchases(cursorCheck.getInt(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_QUANTITY_PURCHASES)));

            /*TODO
             разобраться, что буде, если позиция удалена и кол-во товаров в чеке не соответствует
            кол-ву позиций (проблема только в том, что массив будет от большего числа) */
            BoughtItem[] shoppingList = new BoughtItem[checkList[i].getQuantityPurchases()];

            //второе условие - соответствие "_id" поля чека и поля товара "checkList_id",
            //привязанного к своему чеку
            for (int j = 0; (j < checkList[i].getQuantityPurchases()) &&
                    (cursorShop.getInt(cursorShop.getColumnIndex("checkList_id")) ==
                            cursorCheck.getInt(cursorCheck.getColumnIndex("_id")));
                 j++) {

                shoppingList[j] = new BoughtItem(
                        cursorShop.getString(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_NAME)),
                        cursorShop.getInt(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_PRISE)),
                        cursorShop.getInt(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_QUANTITY)),
                        cursorShop.getString(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_CATEGORIES)));

                cursorShop.moveToNext();
            }
            checkList[i].setShoppingList(shoppingList);


            cursorCheck.moveToNext();
        }

        cursorCheck.close();
        cursorShop.close();

        return checkList;
    }

}
