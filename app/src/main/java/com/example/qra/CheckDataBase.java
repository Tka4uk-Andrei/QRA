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
        public static final String COLUMN_NAME_NAME_FOR_USER = "nameForUser";
        public static final String COLUMN_NAME_PRISE = "prise";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        public static final String COLUMN_NAME_GENERAL_CATEGORIES = "generalCategories";
        public static final String COLUMN_NAME_SUBJECT_CATEGORIES = "subjectCategories";


        public static final String TABLE_NAME_CHECK_LIST = "checkList";
        public static final String COLUMN_NAME_OBTAINING_METHOD = "obtainingMethod";
        public static final String COLUMN_NAME_TOTAL_SUM = "totalSum";
        public static final String COLUMN_NAME_INN = "Inn";
        public static final String COLUMN_NAME_NDS = "paidNdsSum";
        public static final String COLUMN_NAME_ADDRESS = "addressOfPurchase";
        public static final String COLUMN_NAME_TIME = "buyTime";
        public static final String COLUMN_NAME_QUANTITY_PURCHASES = "quantityPurchases";
        public static final String COLUMN_NAME_FISCAL_DOCUMENT_NUMBER = "fiscalDocumentNumber";
        public static final String COLUMN_NAME_FISCAL_DRIVE_NUMBER = "fiscalDriveNumber";
        public static final String COLUMN_NAME_FISCAL_SIGN = "fiscalSign";


        public StorageCheckDataBase(Context context) {
            super(context, DATABASE_Name, null, DATABASE_VERSION);
        }

        // вызывается при первом создании бд
        @Override
        public void onCreate(SQLiteDatabase db) {


            //primary key autoincrement, это поле будет автоматически заполнятся самй бд
            db.execSQL("create table " + TABLE_NAME_CHECK_LIST
                    + "(_id integer primary key autoincrement, "
                    + COLUMN_NAME_OBTAINING_METHOD + " text, "
                    + COLUMN_NAME_QUANTITY_PURCHASES + " integer, "
                    + COLUMN_NAME_TOTAL_SUM + " integer, "
                    + COLUMN_NAME_NDS + " integer, "
                    + COLUMN_NAME_TIME + " text, "
                    + COLUMN_NAME_ADDRESS + " text, "
                    + COLUMN_NAME_INN + " text, "
                    + COLUMN_NAME_FISCAL_DOCUMENT_NUMBER + " integer, "
                    + COLUMN_NAME_FISCAL_DRIVE_NUMBER + " text, "
                    + COLUMN_NAME_FISCAL_SIGN + " integer"
                    + ")");


            //пока id в SHOP не нужно, но кто знает, что будет потом
            //внешний ключ привязан к первому полю чека (_id)
            db.execSQL("create table " + TABLE_NAME_SHOP_LIST
                    + "(_id integer primary key autoincrement, "
                    + COLUMN_NAME_NAME + " text, "
                    + COLUMN_NAME_NAME_FOR_USER + " text, "
                    + COLUMN_NAME_QUANTITY + " integer, "
                    + COLUMN_NAME_PRISE + " integer,"
                    + COLUMN_NAME_GENERAL_CATEGORIES + " text, "
                    + COLUMN_NAME_SUBJECT_CATEGORIES + " text, "
                    + "checkList_id integer,"
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
        //  dataBase.onUpgrade(sqLiteDatabase,1,1);

        contentValues.put(StorageCheckDataBase.COLUMN_NAME_OBTAINING_METHOD, checkObject.getObtainingMethod());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_TOTAL_SUM, checkObject.getTotalSum());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_INN, checkObject.getInn());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_NDS, checkObject.getPaidNdsSum());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_ADDRESS, checkObject.getAddressOfPurchase());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_TIME, checkObject.getBuyTime());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_QUANTITY_PURCHASES, checkObject.getQuantityPurchases());

        contentValues.put(StorageCheckDataBase.COLUMN_NAME_FISCAL_DOCUMENT_NUMBER, checkObject.getFiscalDocumentNumber());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_FISCAL_DRIVE_NUMBER, checkObject.getFiscalDriveNumber());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_FISCAL_SIGN, checkObject.getFiscalSign());


        sqLiteDatabase.insert(StorageCheckDataBase.TABLE_NAME_CHECK_LIST, null, contentValues);
        contentValues.clear();


        Cursor cursor = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_CHECK_LIST, null,
                null, null, null, null, null);
        cursor.moveToLast();

        int checkId = cursor.getInt(cursor.getColumnIndex("_id"));

        cursor.close();

        for (int i = 0; i < checkObject.getQuantityPurchases(); i++) {
            contentValues.put(StorageCheckDataBase.COLUMN_NAME_NAME, checkObject.getShoppingList()[i].getName());
            contentValues.put(StorageCheckDataBase.COLUMN_NAME_NAME_FOR_USER, checkObject.getShoppingList()[i].getNameForUser());
            contentValues.put(StorageCheckDataBase.COLUMN_NAME_QUANTITY, checkObject.getShoppingList()[i].getQuantity());
            contentValues.put(StorageCheckDataBase.COLUMN_NAME_PRISE, checkObject.getShoppingList()[i].getPrice());
            contentValues.put(StorageCheckDataBase.COLUMN_NAME_GENERAL_CATEGORIES, checkObject.getShoppingList()[i].getGeneralCategory());
            contentValues.put(StorageCheckDataBase.COLUMN_NAME_SUBJECT_CATEGORIES, checkObject.getShoppingList()[i].getSubjectCategory());
            contentValues.put("checkList_id", checkId);

            //второй аргумент используется для вставки пустой строки - это не нужно сейчас
            sqLiteDatabase.insert(StorageCheckDataBase.TABLE_NAME_SHOP_LIST, null, contentValues);
            contentValues.clear();
        }

    }

    /**
     * This method checks how to create a check.
     *
     * @param id - tracking ID
     * @return true if the check is recognized from JSON
     */
    private static boolean checkObtainingMethodForCheck(int id) {
        //у нас есть id  чека, который нужно проверить
        //получение способа создания чека
        Cursor cursor = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_CHECK_LIST,
                null, "_id=" + id, null, null, null, null);
        cursor.moveToFirst();
        String obtainingMethod = cursor.getString(cursor.getColumnIndex
                (StorageCheckDataBase.COLUMN_NAME_OBTAINING_METHOD));
        cursor.close();
        final String FNS = CheckInformationStorage.OBTAIN_METHOD_FNS;
        return (FNS.equals(obtainingMethod));
    }

    /**
     * This method checks how to create a item.
     *
     * @param id - tracking ID
     * @return true if the item belongs to check which is recognized from JSON
     */
    private static boolean checkObtainingMethodForBoughItem(int id) {
        //получение id чека, которому принадлежит этот товар
        Cursor cursorShop = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_SHOP_LIST,
                null, "_id=" + id, null, null, null, null);
        cursorShop.moveToFirst();
        int checkListId = cursorShop.getInt(cursorShop.getColumnIndex("checkList_id"));
        cursorShop.close();
        return (checkObtainingMethodForCheck(checkListId));
    }


    /**
     * This method allows you to change name product for user
     * This method can be used by products which belongs to all checks
     *
     * @param id             - tracking ID
     * @param newNameForUser - new name product for user
     * @param context
     */
    public static void updateNameForUser(int id, String newNameForUser, Context context) {

        initialization(context);
        //Редактирование записи
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_SHOP_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_NAME_FOR_USER + "='" + newNameForUser
                + "' where _id=" + id);
    }


    /**
     * This method allows you to change name the product
     * This method can be used by products which belongs to only user check
     *
     * @param id      - tracking ID
     * @param newName - new name the product
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the products which belongs to check is recognized from JSON
     */
    public static void updateName(int id, String newName, Context context) throws CheckEditingException {

        initialization(context);

        if (checkObtainingMethodForBoughItem(id)) {
            throw new CheckEditingException();
        }

        //Редактирование записи
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_SHOP_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_NAME + "='" + newName
                + "' where _id=" + id);
    }


    /**
     * This method allows you to change number of goods with the given name
     * This method can be used by products which belongs to only user check
     *
     * @param id          - tracking ID
     * @param newQuantity - new number of goods with the given name
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the products which belongs to check is recognized from JSON
     */
    public static void updateQuantyty(int id, int newQuantity, Context context) throws CheckEditingException {

        initialization(context);
        if (checkObtainingMethodForBoughItem(id)) {
            throw new CheckEditingException();
        }

        //Редактирование записи
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_SHOP_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_QUANTITY + "='" + newQuantity
                + "' where _id=" + id);
    }


    /**
     * This method allows you to change price of the products also change total sum
     * This method can be used by products which belongs to only user check
     *
     * @param id       - tracking ID
     * @param newPrise - new price of the products
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the products which belongs to check is recognized from JSON
     */
    public static void updatePrise(int id, int newPrise, Context context) throws CheckEditingException {

        initialization(context);

        if (checkObtainingMethodForBoughItem(id)) {
            throw new CheckEditingException();
        }

        //получение id чека, в котором необходимо поменять итогувую сумму и старой цены
        Cursor cursorShop = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_SHOP_LIST,
                null, "_id=" + id, null, null, null, null);
        cursorShop.moveToFirst();
        int checkListId = cursorShop.getInt(cursorShop.getColumnIndex("checkList_id"));
        int oldPrise = cursorShop.getInt(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_PRISE));
        cursorShop.close();

        //получение старой итоговой суммыи вычисление новой
        Cursor cursor = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_CHECK_LIST,
                null, "_id=" + checkListId, null, null, null, null);
        cursor.moveToFirst();
        int oldTotalSum = cursor.getInt(cursor.getColumnIndex
                (StorageCheckDataBase.COLUMN_NAME_TOTAL_SUM));
        cursor.close();

        //Редактирование итоговой суммы
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_CHECK_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_TOTAL_SUM + "='"
                + (oldTotalSum + (newPrise - oldPrise)) + "' where _id=" + checkListId);


        //Редактирование самой цены
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_SHOP_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_PRISE + "='" + newPrise
                + "' where _id=" + id);


    }


    /**
     * This method allows you to change general category which the product belong to
     * This method can be used by products which belongs to all checks
     *
     * @param id                 - tracking ID
     * @param newGeneralCategory - new general category which the product belong to
     * @param context
     */
    public static void updateGeneralCategory(int id, String newGeneralCategory, Context context) {

        initialization(context);
        //Редактирование записи
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_SHOP_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_GENERAL_CATEGORIES + "='" + newGeneralCategory
                + "' where _id=" + id);
    }

    /**
     * This method allows you to change subject category which the product belong to
     * This method can be used by products which belongs to all checks
     *
     * @param id                 - tracking ID
     * @param newSubjectCategory - new subjectCategory which the product belong to
     * @param context
     */
    public static void updateSubjectCategory(int id, String newSubjectCategory, Context context) {

        initialization(context);
        //Редактирование записи
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_SHOP_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_SUBJECT_CATEGORIES + "='" + newSubjectCategory
                + "' where _id=" + id);
    }


    /**
     * This method allows you to change tax Identification Number
     * This method can be used by only user check
     *
     * @param id      - tracking ID
     * @param newInn  - new tax Identification Number
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the check is recognized from JSON
     */
    public static void updateInn(int id, String newInn, Context context) throws CheckEditingException {

        initialization(context);

        if (checkObtainingMethodForCheck(id)) {
            throw new CheckEditingException();
        }
        //Редактирование записи
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_CHECK_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_INN + "='" + newInn
                + "' where _id=" + id);
//                + "' where " +
//                StorageCheckDataBase.COLUMN_NAME_FISCAL_DOCUMENT_NUMBER + "=" + fiscalDocumentNumber
//                + " and where "
//                + StorageCheckDataBase.COLUMN_NAME_FISCAL_DRIVE_NUMBER + "=" + fiscalDriveNumber
//                + " and where "
//                + StorageCheckDataBase.COLUMN_NAME_FISCAL_SIGN + "=" + fiscalSign);
    }


    /**
     * This method allows you to change store address
     * This method can be used by only user check
     *
     * @param id         - tracking ID
     * @param newAddress - new store address
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the check is recognized from JSON
     */
    public static void updateAdress(int id, String newAddress, Context context) throws CheckEditingException {

        initialization(context);
        if (checkObtainingMethodForCheck(id)) {
            throw new CheckEditingException();
        }
        //Редактирование записи
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_CHECK_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_ADDRESS + "='" + newAddress
                + "' where _id=" + id);
    }


    /**
     * This method allows you to change buying time
     * This method can be used by only user check
     *
     * @param id      - tracking ID
     * @param newTime - new buying time
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the check is recognized from JSON
     */
    public static void updateTime(int id, String newTime, Context context) throws CheckEditingException {

        initialization(context);
        if (checkObtainingMethodForCheck(id)) {
            throw new CheckEditingException();
        }
        //Редактирование записи
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_CHECK_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_TIME + "='" + newTime
                + "' where _id=" + id);
    }


    /**
     * This method allows you to change paid nds sum also change totalSum
     * This method can be used by only user check
     *
     * @param id      - tracking ID
     * @param newNds  - new paid nds sum
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the check is recognized from JSON
     */
    public static void updateNds(int id, int newNds, Context context) throws CheckEditingException {

        initialization(context);
        if (checkObtainingMethodForCheck(id)) {
            throw new CheckEditingException();
        }

        //получение старого nds и старой итоговой суммы
        Cursor cursor = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_CHECK_LIST,
                null, "_id=" + id, null, null, null, null);
        cursor.moveToFirst();
        int oldTotalSum = cursor.getInt(cursor.getColumnIndex
                (StorageCheckDataBase.COLUMN_NAME_TOTAL_SUM));
        int oldNds = cursor.getInt(cursor.getColumnIndex
                (StorageCheckDataBase.COLUMN_NAME_NDS));
        cursor.close();

        //Редактирование итоговой суммы
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_CHECK_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_TOTAL_SUM + "='"
                + (oldTotalSum + (newNds - oldNds)) + "' where _id=" + id);


        //Редактирование записи nds
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_CHECK_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_NDS + "='" + newNds
                + "' where _id=" + id);
    }

    /**
     * This method update all position in check include all purchases
     * This method can be used by only user check
     *
     * @param checkObject - check
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the check is recognized from JSON
     */
    public static void updateAllUserCheck(Context context, CheckInformationStorage checkObject) throws CheckEditingException {
        updateInn(checkObject.getId(), checkObject.getInn(), context);
        updateAdress(checkObject.getId(), checkObject.getAddressOfPurchase(), context);
        updateTime(checkObject.getId(), checkObject.getBuyTime(), context);
        updateNds(checkObject.getId(), checkObject.getPaidNdsSum(), context);

        for (int i = 0; i < checkObject.getQuantityPurchases(); i++) {

            updateNameForUser(checkObject.getShoppingList()[i].getId(),
                    checkObject.getShoppingList()[i].getNameForUser(), context);

            updateName(checkObject.getShoppingList()[i].getId(),
                    checkObject.getShoppingList()[i].getName(), context);

            updateQuantyty(checkObject.getShoppingList()[i].getId(),
                    checkObject.getShoppingList()[i].getQuantity(), context);

            updatePrise(checkObject.getShoppingList()[i].getId(),
                    checkObject.getShoppingList()[i].getPrice(), context);

            updateGeneralCategory(checkObject.getShoppingList()[i].getId(),
                    checkObject.getShoppingList()[i].getGeneralCategory(), context);

            updateSubjectCategory(checkObject.getShoppingList()[i].getId(),
                    checkObject.getShoppingList()[i].getSubjectCategory(), context);
        }
    }

    /**
     * This method update all position editable for FNS in check
     * editable position: NameForUser, GeneralCategory and SubjectCategory
     *
     * This method can be used by all checks
     *
     * @param checkObject - check
     * @param context
     */
    public static void updateAllFnsCheck(Context context, CheckInformationStorage checkObject){
        for (int i = 0; i < checkObject.getQuantityPurchases(); i++) {

            updateNameForUser(checkObject.getShoppingList()[i].getId(),
                    checkObject.getShoppingList()[i].getNameForUser(), context);

            updateGeneralCategory(checkObject.getShoppingList()[i].getId(),
                    checkObject.getShoppingList()[i].getGeneralCategory(), context);

            updateSubjectCategory(checkObject.getShoppingList()[i].getId(),
                    checkObject.getShoppingList()[i].getSubjectCategory(), context);
        }
    }

    /**
     * This method allows you to delete item also change number of products which you bought
     * This method can be used by only user check
     *
     * @param id      - tracking ID
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the products which belongs to check is recognized from JSON
     */
    public static void deleteItem(int id, Context context) throws CheckEditingException {

        initialization(context);
        if (checkObtainingMethodForBoughItem(id)) {
            throw new CheckEditingException();
        }
        //получение id чека, в котором необходимо поменять кол-во покупок
        Cursor cursorShop = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_SHOP_LIST,
                null, "_id=" + id, null, null, null, null);
        cursorShop.moveToFirst();
        int checkListId = cursorShop.getInt(cursorShop.getColumnIndex("checkList_id"));
        cursorShop.close();

        //получение старого кол-ва покупок и уменьшение на 1
        Cursor cursor = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_CHECK_LIST,
                null, "_id=" + checkListId, null, null, null, null);
        cursor.moveToFirst();
        int newQuantityPurchases = cursor.getInt(cursor.getColumnIndex
                (StorageCheckDataBase.COLUMN_NAME_QUANTITY_PURCHASES)) - 1;
        cursor.close();

        //Редактирование кол-во покупок
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_CHECK_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_QUANTITY_PURCHASES + "='" + newQuantityPurchases
                + "' where _id=" + checkListId);

        //Удаление записи
        sqLiteDatabase.execSQL("DELETE FROM " + StorageCheckDataBase.TABLE_NAME_SHOP_LIST
                + " WHERE _id=" + id);


    }


    /**
     * This method allows you to delete the check
     * This method can be used by all checks
     *
     * @param id      - tracking ID
     * @param context
     */
    public static void deleteCheck(int id, Context context) {

        initialization(context);

        sqLiteDatabase.execSQL("DELETE FROM " + StorageCheckDataBase.TABLE_NAME_CHECK_LIST
                + " WHERE _id=" + id);

        sqLiteDatabase.execSQL("DELETE FROM " + StorageCheckDataBase.TABLE_NAME_SHOP_LIST
                + " WHERE checkList_id=" + id);
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

            shoppingList[i] = new BoughtItem.Builder()
                    .setId(cursor.getInt(cursor.getColumnIndex("_id")))
                    .setName(cursor.getString(cursor.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_NAME)))
                    .setNameForUser(cursor.getString(cursor.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_NAME_FOR_USER)))
                    .setPrice(cursor.getInt(cursor.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_PRISE)))
                    .setQuantity(cursor.getInt(cursor.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_QUANTITY)))
                    .setGeneralCategory(cursor.getString(cursor.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_GENERAL_CATEGORIES)))
                    .setSubjectCategory(cursor.getString(cursor.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_SUBJECT_CATEGORIES)))
                    .build();


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

            int quantityPurchases = cursorCheck.getInt(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_QUANTITY_PURCHASES));

            BoughtItem[] shoppingList = new BoughtItem[quantityPurchases];

            //второе условие - соответствие "_id" поля чека и поля товара "checkList_id",
            //привязанного к своему чеку
            for (int j = 0; (j < quantityPurchases) &&
                    (cursorShop.getInt(cursorShop.getColumnIndex("checkList_id")) ==
                            cursorCheck.getInt(cursorCheck.getColumnIndex("_id")));
                 j++) {

                shoppingList[j] = new BoughtItem.Builder()
                        .setId(cursorShop.getInt(cursorShop.getColumnIndex("_id")))
                        .setName(cursorShop.getString(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_NAME)))
                        .setNameForUser(cursorShop.getString(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_NAME_FOR_USER)))
                        .setPrice(cursorShop.getInt(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_PRISE)))
                        .setQuantity(cursorShop.getInt(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_QUANTITY)))
                        .setGeneralCategory(cursorShop.getString(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_GENERAL_CATEGORIES)))
                        .setSubjectCategory(cursorShop.getString(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_SUBJECT_CATEGORIES)))
                        .build();

                cursorShop.moveToNext();
            }


            checkList[i] = new CheckInformationStorage.Builder()
                    .setId(cursorCheck.getInt(cursorCheck.getColumnIndex("_id")))
                    .setObtainingMethod(cursorCheck.getString(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_OBTAINING_METHOD)))
                    .setTotalSum(cursorCheck.getInt(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_TOTAL_SUM)))
                    .setInn(cursorCheck.getString(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_INN)))
                    .setPaidNdsSum(cursorCheck.getInt(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_NDS)))
                    .setAddressOfPurchase(cursorCheck.getString(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_ADDRESS)))
                    .setBuyTime(cursorCheck.getString(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_TIME)))
                    .setQuantityPurchases(quantityPurchases)

                    .setFiscalDocumentNumber(cursorCheck.getInt(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_FISCAL_DOCUMENT_NUMBER)))
                    .setFiscalDriveNumber(cursorCheck.getString(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_FISCAL_DRIVE_NUMBER)))
                    .setFiscalSign(cursorCheck.getInt(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_FISCAL_SIGN)))
                    .setShoppingList(shoppingList)
                    .build();

            cursorCheck.moveToNext();
        }

        cursorCheck.close();
        cursorShop.close();

        return checkList;
    }

}
