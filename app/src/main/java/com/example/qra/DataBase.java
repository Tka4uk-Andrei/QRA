package com.example.qra;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


////метод onCreate этого класса создает таблицу покупок без ссылок на чек и категорий.

/**
 * class responsible for storage of data base
 *
 * @author: Marina Alekseeva
 */
public class DataBase extends SQLiteOpenHelper {

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


    public DataBase(Context context) {
        super(context, DATABASE_Name, null, DATABASE_VERSION);
    }


    //возвращает бд для чтения -getReadableDatabase()
    //возвращает бд чтения и записи - getWritableDatabase()
    //при открытии - onOpen()
    //


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
