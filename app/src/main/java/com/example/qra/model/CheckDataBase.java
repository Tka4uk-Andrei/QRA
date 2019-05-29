package com.example.qra.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.qra.model.check.CheckItem;
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
        public static final String COLUMN_NAME_CHECK_NAME = "checkName";
        public static final String COLUMN_NAME_DATE = "buyDate";


        public StorageCheckDataBase(Context context) {
            super(context, DATABASE_Name, null, DATABASE_VERSION);
        }

        // вызывается при первом создании бд
        @Override
        public void onCreate(SQLiteDatabase db) {


            //primary key autoincrement, это поле будет автоматически заполнятся самй бд
            db.execSQL("create table " + TABLE_NAME_CHECK_LIST
                    + "(_id integer primary key autoincrement, "
                    + COLUMN_NAME_CHECK_NAME + " text, "
                    + COLUMN_NAME_OBTAINING_METHOD + " text, "
                    + COLUMN_NAME_QUANTITY_PURCHASES + " integer, "
                    + COLUMN_NAME_TOTAL_SUM + " integer, "
                    + COLUMN_NAME_NDS + " integer, "
                    + COLUMN_NAME_DATE + " text, "
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
     * This function inserts data if there was no such check before.
     *
     * @param checkObject - class storing the recognized JSON string
     */
    private static void insertObject(CheckInformationStorage checkObject) {


        contentValues.put(StorageCheckDataBase.COLUMN_NAME_CHECK_NAME, checkObject.getCheckName());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_OBTAINING_METHOD, checkObject.getObtainingMethod());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_TOTAL_SUM, checkObject.getTotalSum());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_INN, checkObject.getInn());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_NDS, checkObject.getPaidNdsSum());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_ADDRESS, checkObject.getAddressOfPurchase());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_TIME, checkObject.getBuyTime());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_DATE, checkObject.getBuyDate());
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
     * insert in data base
     *
     * @param checkObject class storing the recognized JSON string
     * @param context
     */
    public static void insert(CheckInformationStorage checkObject, Context context) throws CheckAddingException {
        initialization(context);
        //след строчка используется после изменения структуры бд, для создания таблиц
        //  dataBase.onUpgrade(sqLiteDatabase,1,1);

        //if check from fns
        if (checkObject.getObtainingMethod().equals(CheckInformationStorage.OBTAIN_METHOD_FNS)) {

            Cursor checkCursor = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_CHECK_LIST, null,
                    StorageCheckDataBase.COLUMN_NAME_FISCAL_DOCUMENT_NUMBER
                            + "=" + checkObject.getFiscalDocumentNumber() + " and "
                            + StorageCheckDataBase.COLUMN_NAME_FISCAL_DRIVE_NUMBER
                            + "=" + checkObject.getFiscalDriveNumber() + " and "
                            + StorageCheckDataBase.COLUMN_NAME_FISCAL_SIGN
                            + "=" + checkObject.getFiscalSign(),
                    null, null, null, null);

            //if such check has in fns
            if (checkCursor.getCount() != 0) {
                checkCursor.close();
                throw new CheckAddingException();
            }
            checkCursor.close();
        }
        insertObject(checkObject);
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
     * This method checks product in bd.
     *
     * @param id - tracking ID
     * @return true if the product have in bd
     */
    private static boolean shopBdAvailability(int id) {
        Cursor cursorShop = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_SHOP_LIST,
                null, "_id=" + id, null, null, null, null);
        boolean answer = cursorShop.getCount() != 0;
        cursorShop.close();
        return (answer);
    }

    /**
     * This method checks check in bd.
     *
     * @param id - tracking ID
     * @return true if the check have in bd
     */
    private static boolean checkBdAvailability(int id) {
        Cursor cursorCheck = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_CHECK_LIST,
                null, "_id=" + id, null, null, null, null);
        boolean answer = cursorCheck.getCount() != 0;
        cursorCheck.close();
        return (answer);
    }


    /**
     * @param id             - tracking ID of boughItem
     * @param newNameForUser - new name product for user
     * @param context
     * @Deprecated This method allows you to change name product for user
     * This method can be used by products which belongs to all checks
     */
    public static void updateNameForUser(int id, String newNameForUser, Context context) {

        initialization(context);
        //Редактирование записи
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_SHOP_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_NAME_FOR_USER + "='" + newNameForUser
                + "' where _id=" + id);
    }


    /**
     * @param id      - tracking ID of boughItem
     * @param newName - new name the product
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the products which belongs to check is recognized from JSON
     * @Deprecated This method allows you to change name the product
     * This method can be used by products which belongs to only user check
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
     * @param id          - tracking ID of boughItem
     * @param newQuantity - new number of goods with the given name
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the products which belongs to check is recognized from JSON
     * @Deprecated This method allows you to change number of goods with the given name
     * This method can be used by products which belongs to only user check
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
     * @param id       - tracking ID of boughItem
     * @param newPrise - new price of the products
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the products which belongs to check is recognized from JSON
     * @Deprecated This method allows you to change price of the products also change total sum
     * This method can be used by products which belongs to only user check
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
     * @param id                 - tracking ID of boughItem
     * @param newGeneralCategory - new general category which the product belong to
     * @param context
     * @Deprecated This method allows you to change general category which the product belong to
     * This method can be used by products which belongs to all checks
     */
    public static void updateGeneralCategory(int id, String newGeneralCategory, Context context) {

        initialization(context);
        //Редактирование записи
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_SHOP_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_GENERAL_CATEGORIES + "='" + newGeneralCategory
                + "' where _id=" + id);
    }


    /**
     * @param id                 - tracking ID of boughItem
     * @param newSubjectCategory - new subjectCategory which the product belong to
     * @param context
     * @Deprecated This method allows you to change subject category which the product belong to
     * This method can be used by products which belongs to all checks
     */
    public static void updateSubjectCategory(int id, String newSubjectCategory, Context context) {

        initialization(context);
        //Редактирование записи
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_SHOP_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_SUBJECT_CATEGORIES + "='" + newSubjectCategory
                + "' where _id=" + id);
    }


    /**
     * @param id      - tracking ID of check
     * @param newInn  - new tax Identification Number
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the check is recognized from JSON
     * @Deprecated This method allows you to change tax Identification Number
     * This method can be used by only user check
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

    }


    /**
     * @param id         - tracking ID of check
     * @param newAddress - new store address
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the check is recognized from JSON
     * @Deprecated This method allows you to change store address
     * This method can be used by only user check
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
     * @param id      - tracking ID of check
     * @param newTime - new buying time
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the check is recognized from JSON
     * @Deprecated This method allows you to change buying time
     * This method can be used by only user check
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
     * @param id      - tracking ID of check
     * @param newNds  - new paid nds sum
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the check is recognized from JSON
     * @Deprecated This method allows you to change paid nds sum also change totalSum
     * This method can be used by only user check
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
     * <p>
     * will not add new products and will not remove
     *
     * @param checkObject - check
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the check is recognized from JSON
     *                               or this check is no in dataBase
     */
    public static void updateAllPositionUserCheck(Context context, CheckInformationStorage checkObject)
            throws CheckEditingException {

        updateCheckName(checkObject, context);
        updateInn(checkObject, context);
        updateAdress(checkObject, context);
        updateTime(checkObject, context);
        updateDate(checkObject, context);
        updateNds(checkObject, context);

        for (int i = 0; i < checkObject.getQuantityPurchases(); i++) {

            updateNameForUser(checkObject.getShoppingList()[i], context);
            updateName(checkObject.getShoppingList()[i], context);
            updateQuantyty(checkObject.getShoppingList()[i], context);
            updatePrise(checkObject.getShoppingList()[i], context);
            updateGeneralCategory(checkObject.getShoppingList()[i], context);
            updateSubjectCategory(checkObject.getShoppingList()[i], context);
        }
    }

    /**
     * This method update all position editable for FNS in check
     * editable position:  CheckName (check), NameForUser(product),
     * GeneralCategory(product) and SubjectCategory(product)
     * <p>
     * will not add new products and will not remove
     * <p>
     * This method can be used by all checks
     *
     * @param checkObject - check
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the check which is no in dataBase
     */
    public static void updateAllPositionFnsCheck(Context context, CheckInformationStorage checkObject)
            throws CheckEditingException {
        updateCheckName(checkObject, context);
        for (int i = 0; i < checkObject.getQuantityPurchases(); i++) {

            updateNameForUser(checkObject.getShoppingList()[i], context);
            updateGeneralCategory(checkObject.getShoppingList()[i], context);
            updateSubjectCategory(checkObject.getShoppingList()[i], context);
        }
    }

    /**
     * @param id      - tracking ID of boughItem
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the products which belongs to check is recognized from JSON
     * @Deprecated This method allows you to delete item also change number of products which you bought
     * This method can be used by only user check
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
     * @param id      - tracking ID of check
     * @param context
     * @return BoughtItem - which can be further edited
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the products which belongs to check is recognized from JSON
     * @Deprecated This method allows you to delete item also change number of products which you bought
     * This method can be used by only user check
     */
    public static CheckItem addItem(int id, CheckItem item, Context context) throws CheckEditingException {

        initialization(context);
        if (checkObtainingMethodForCheck(id)) {
            throw new CheckEditingException();
        }

        //получение старого кол-ва покупок и увеличение на 1
        Cursor cursor = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_CHECK_LIST,
                null, "_id=" + id, null, null, null, null);
        cursor.moveToFirst();
        int newQuantityPurchases = cursor.getInt(cursor.getColumnIndex
                (StorageCheckDataBase.COLUMN_NAME_QUANTITY_PURCHASES)) + 1;
        cursor.close();

        //Редактирование кол-во покупок
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_CHECK_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_QUANTITY_PURCHASES + "='" + newQuantityPurchases
                + "' where _id=" + id);

        //Удаление записи добавление в бд
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_NAME, item.getName());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_NAME_FOR_USER, item.getNameForUser());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_QUANTITY, item.getQuantity());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_PRISE, item.getPrice());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_GENERAL_CATEGORIES, item.getGeneralCategory());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_SUBJECT_CATEGORIES, item.getSubjectCategory());
        contentValues.put("checkList_id", id);

        //второй аргумент используется для вставки пустой строки - это не нужно сейчас
        sqLiteDatabase.insert(StorageCheckDataBase.TABLE_NAME_SHOP_LIST, null, contentValues);
        contentValues.clear();

        Cursor cursorShop = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_SHOP_LIST,
                null, "checkList_id=" + id, null, null, null, null);
        cursorShop.moveToLast();

        CheckItem tempItem = new CheckItem.Builder()
                .setId(cursorShop.getInt(cursorShop.getColumnIndex("_id")))
                .setName(cursorShop.getString(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_NAME)))
                .setNameForUser(cursorShop.getString(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_NAME_FOR_USER)))
                .setPrice(cursorShop.getInt(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_PRISE)))
                .setQuantity(cursorShop.getInt(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_QUANTITY)))
                .setGeneralCategory(cursorShop.getString(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_GENERAL_CATEGORIES)))
                .setSubjectCategory(cursorShop.getString(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_SUBJECT_CATEGORIES)))
                .build();
        cursorShop.close();
        return tempItem;


    }


    /**
     * @param id      - tracking ID of check
     * @param context
     * @Deprecated This method allows you to delete the check
     * This method can be used by all checks
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
    public static CheckItem[] getShoppingList(Context context) {
        initialization(context);

        Cursor cursor = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_SHOP_LIST, null,
                null, null, null, null, null);
        CheckItem[] shoppingList = new CheckItem[cursor.getCount()];
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {

            shoppingList[i] = new CheckItem.Builder()
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
     * get your goods with a certain category
     *
     * @param context
     * @param generalCategory - What general category does the product belong to
     * @param subjectCategory - What subject category does the product belong to
     * @return shopping list
     */
    public static CheckItem[] getShoppingList(Context context, String generalCategory, String subjectCategory) {
        initialization(context);

        String whereClause = StorageCheckDataBase.COLUMN_NAME_GENERAL_CATEGORIES + " = ? and "
                + StorageCheckDataBase.COLUMN_NAME_SUBJECT_CATEGORIES + " = ?";
        String[] whereArgs = new String[]{generalCategory, subjectCategory};

        Cursor cursor = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_SHOP_LIST, null,
                whereClause, whereArgs, null, null, null);

        CheckItem[] shoppingList = new CheckItem[cursor.getCount()];
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {

            shoppingList[i] = new CheckItem.Builder()
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


        for (int i = 0; i < cursorCheck.getCount(); i++) {

            int quantityPurchases = cursorCheck.getInt(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_QUANTITY_PURCHASES));
            int checkId = cursorCheck.getInt(cursorCheck.getColumnIndex("_id"));


            CheckItem[] shoppingList = new CheckItem[quantityPurchases];

            //второе условие - соответствие "_id" поля чека и поля товара "checkList_id",
            //привязанного к своему чеку
            Cursor cursorShop = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_SHOP_LIST, null,
                    "checkList_id=" + checkId, null, null, null, null);
            cursorShop.moveToFirst();

            for (int j = 0; (j < quantityPurchases); j++) {


                shoppingList[j] = new CheckItem.Builder()
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
            cursorShop.close();


            checkList[i] = new CheckInformationStorage.Builder()
                    .setId(checkId)
                    .setCheckName(cursorCheck.getString(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_CHECK_NAME)))
                    .setObtainingMethod(cursorCheck.getString(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_OBTAINING_METHOD)))
                    .setTotalSum(cursorCheck.getInt(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_TOTAL_SUM)))
                    .setInn(cursorCheck.getString(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_INN)))
                    .setPaidNdsSum(cursorCheck.getInt(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_NDS)))
                    .setAddressOfPurchase(cursorCheck.getString(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_ADDRESS)))
                    .setBuyTime(cursorCheck.getString(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_TIME)))
                    .setBuyDate(cursorCheck.getString(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_DATE)))
                    .setQuantityPurchases(quantityPurchases)

                    .setFiscalDocumentNumber(cursorCheck.getInt(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_FISCAL_DOCUMENT_NUMBER)))
                    .setFiscalDriveNumber(cursorCheck.getString(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_FISCAL_DRIVE_NUMBER)))
                    .setFiscalSign(cursorCheck.getInt(cursorCheck.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_FISCAL_SIGN)))
                    .setShoppingList(shoppingList)
                    .build();

            cursorCheck.moveToNext();
        }

        cursorCheck.close();


        return checkList;
    }


    /**
     * This method allows you to change name product for user
     * This method can be used by products which belongs to all checks
     *
     * @param checkItemObject - product in which the name for user is changed
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the products which is no in dataBase
     */
    public static void updateNameForUser(CheckItem checkItemObject, Context context) throws CheckEditingException {

        if (!shopBdAvailability(checkItemObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_NO_CHECK_IN_DB);
        }

        initialization(context);
        //Редактирование записи
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_SHOP_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_NAME_FOR_USER + "='"
                + checkItemObject.getNameForUser() + "' where _id=" + checkItemObject.getId());
    }

    /**
     * This method can be used by products which belongs to only user check
     * This method allows you to change name the product
     *
     * @param checkItemObject -  product in which the name is changed
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the products which belongs to check is recognized from JSON
     *                               or this product is no in dataBase
     */
    public static void updateName(CheckItem checkItemObject, Context context) throws CheckEditingException {

        initialization(context);

        if (checkObtainingMethodForBoughItem(checkItemObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_CHECK_FROM_FNS);
        }
        if (!shopBdAvailability(checkItemObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_NO_CHECK_IN_DB);
        }

        //Редактирование записи
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_SHOP_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_NAME + "='" + checkItemObject.getName()
                + "' where _id=" + checkItemObject.getId());
    }


    /**
     * This method allows you to change number of goods with the given name
     * This method can be used by products which belongs to only user check
     *
     * @param checkItemObject - product in which the quantyty is changed
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the products which belongs to check is recognized from JSON
     *                               or this product is no in dataBase
     */
    public static void updateQuantyty(CheckItem checkItemObject, Context context)
            throws CheckEditingException {

        initialization(context);
        if (checkObtainingMethodForBoughItem(checkItemObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_CHECK_FROM_FNS);
        }

        if (!shopBdAvailability(checkItemObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_NO_CHECK_IN_DB);
        }

        //Редактирование записи
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_SHOP_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_QUANTITY + "='" + checkItemObject.getQuantity()
                + "' where _id=" + checkItemObject.getId());
    }


    /**
     * This method allows you to change price of the products also change total sum
     * This method can be used by products which belongs to only user check
     *
     * @param checkItemObject - product in which the prise is changed
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the products which belongs to check is recognized from
     *                               or this product is no in dataBase
     */
    public static void updatePrise(CheckItem checkItemObject, Context context) throws CheckEditingException {

        initialization(context);

        if (checkObtainingMethodForBoughItem(checkItemObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_CHECK_FROM_FNS);
        }
        if (!shopBdAvailability(checkItemObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_NO_CHECK_IN_DB);
        }

        //получение id чека, в котором необходимо поменять итогувую сумму и старой цены
        Cursor cursorShop = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_SHOP_LIST,
                null, "_id=" + checkItemObject.getId(), null, null, null, null);
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
                + (oldTotalSum + (checkItemObject.getPrice() - oldPrise)) + "' where _id=" + checkListId);


        //Редактирование самой цены
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_SHOP_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_PRISE + "='" + checkItemObject.getPrice()
                + "' where _id=" + checkItemObject.getId());


    }


    /**
     * This method allows you to change general category which the product belong to
     * This method can be used by products which belongs to all checks
     *
     * @param checkItemObject - product in which the general category is changed
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the products which is no in dataBase
     */
    public static void updateGeneralCategory(CheckItem checkItemObject, Context context) throws CheckEditingException {

        initialization(context);
        if (!shopBdAvailability(checkItemObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_NO_CHECK_IN_DB);
        }
        //Редактирование записи
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_SHOP_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_GENERAL_CATEGORIES + "='"
                + checkItemObject.getGeneralCategory() + "' where _id=" + checkItemObject.getId());
    }


    /**
     * This method allows you to change subject category which the product belong to
     * This method can be used by products which belongs to all checks
     *
     * @param checkItemObject - product in which the general category is changed
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the products which is no in dataBase
     */
    public static void updateSubjectCategory(CheckItem checkItemObject, Context context) throws CheckEditingException {

        initialization(context);
        if (!shopBdAvailability(checkItemObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_NO_CHECK_IN_DB);
        }
        //Редактирование записи
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_SHOP_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_SUBJECT_CATEGORIES + "='"
                + checkItemObject.getSubjectCategory() + "' where _id=" + checkItemObject.getId());
    }


    /**
     * This method allows you to change tax Identification Number
     * This method can be used by only user check
     *
     * @param checkObject - check in which the inn is changed
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the check is recognized from JSON
     *                               or this check is no in dataBase
     */
    public static void updateInn(CheckInformationStorage checkObject, Context context) throws CheckEditingException {

        initialization(context);

        if (checkObtainingMethodForCheck(checkObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_CHECK_FROM_FNS);
        }
        if (!checkBdAvailability(checkObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_NO_CHECK_IN_DB);
        }
        //Редактирование записи
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_CHECK_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_INN + "='" + checkObject.getInn()
                + "' where _id=" + checkObject.getId());

    }


    /**
     * This method allows you to change store address
     * This method can be used by only user check
     *
     * @param checkObject - check in which the adress is changed
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the check is recognized from JSON
     *                               or this check is no in dataBase
     */
    public static void updateAdress(CheckInformationStorage checkObject, Context context) throws CheckEditingException {

        initialization(context);
        if (checkObtainingMethodForCheck(checkObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_CHECK_FROM_FNS);
        }

        if (!checkBdAvailability(checkObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_NO_CHECK_IN_DB);
        }
        //Редактирование записи
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_CHECK_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_ADDRESS + "='" + checkObject.getAddressOfPurchase()
                + "' where _id=" + checkObject.getId());
    }


    /**
     * This method allows you to change buying time
     * This method can be used by only user check
     *
     * @param checkObject - check in which the time is changed
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the check is recognized from JSON
     *                               or this check is no in dataBase
     */
    public static void updateTime(CheckInformationStorage checkObject, Context context) throws CheckEditingException {

        initialization(context);
        if (checkObtainingMethodForCheck(checkObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_CHECK_FROM_FNS);
        }
        if (!checkBdAvailability(checkObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_NO_CHECK_IN_DB);
        }
        //Редактирование записи
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_CHECK_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_TIME + "='" + checkObject.getBuyTime()
                + "' where _id=" + checkObject.getId());
    }

    /**
     * This method allows you to change buying time
     * This method can be used by only user check
     *
     * @param checkObject - check in which the date is changed
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the check is recognized from JSON
     *                               or this check is no in dataBase
     */
    public static void updateDate(CheckInformationStorage checkObject, Context context) throws CheckEditingException {

        initialization(context);
        if (checkObtainingMethodForCheck(checkObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_CHECK_FROM_FNS);
        }

        if (!checkBdAvailability(checkObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_NO_CHECK_IN_DB);
        }
        //Редактирование записи
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_CHECK_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_DATE + "='" + checkObject.getBuyDate()
                + "' where _id=" + checkObject.getId());
    }


    /**
     * This method allows you to change name of check
     * This method can be used by only user check
     *
     * @param checkObject - check in which the name is changed
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the check which is no in dataBase
     */
    public static void updateCheckName(CheckInformationStorage checkObject, Context context) throws CheckEditingException {

        if (!checkBdAvailability(checkObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_NO_CHECK_IN_DB);
        }
        //Редактирование записи
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_CHECK_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_CHECK_NAME + "='" + checkObject.getCheckName()
                + "' where _id=" + checkObject.getId());
    }

    /**
     * This method allows you to change paid nds sum also change totalSum
     * This method can be used by only user check
     *
     * @param checkObject - check in which the nds is changed
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the check is recognized from JSON
     *                               or this check is no in dataBase
     */
    public static void updateNds(CheckInformationStorage checkObject, Context context) throws CheckEditingException {

        initialization(context);
        if (checkObtainingMethodForCheck(checkObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_CHECK_FROM_FNS);
        }

        if (!checkBdAvailability(checkObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_NO_CHECK_IN_DB);
        }

        //получение старого nds и старой итоговой суммы
        Cursor cursor = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_CHECK_LIST,
                null, "_id=" + checkObject.getId(), null, null, null, null);
        cursor.moveToFirst();
        int oldTotalSum = cursor.getInt(cursor.getColumnIndex
                (StorageCheckDataBase.COLUMN_NAME_TOTAL_SUM));
        int oldNds = cursor.getInt(cursor.getColumnIndex
                (StorageCheckDataBase.COLUMN_NAME_NDS));
        cursor.close();

        //Редактирование итоговой суммы
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_CHECK_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_TOTAL_SUM + "='"
                + (oldTotalSum + (checkObject.getPaidNdsSum() - oldNds)) + "' where _id=" + checkObject.getId());


        //Редактирование записи nds
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_CHECK_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_NDS + "='" + checkObject.getPaidNdsSum()
                + "' where _id=" + checkObject.getId());
    }


    /**
     * This method allows you to delete item also change number of products which you bought
     * This method can be used by only user check
     *
     * @param checkItemObject - product which you want to delete
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the products which belongs to check is recognized from JSON
     *                               or this product is no in dataBase
     */
    public static void deleteItem(CheckItem checkItemObject, Context context) throws CheckEditingException {

        initialization(context);
        if (checkObtainingMethodForBoughItem(checkItemObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_CHECK_FROM_FNS);
        }
        if (!shopBdAvailability(checkItemObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_NO_CHECK_IN_DB);
        }
        //получение id чека, в котором необходимо поменять кол-во покупок
        Cursor cursorShop = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_SHOP_LIST,
                null, "_id=" + checkItemObject.getId(), null, null, null, null);
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
                + " WHERE _id=" + checkItemObject.getId());


    }


    /**
     * This method allows you to delete item also change number of products which you bought
     * This method can be used by only user check
     *
     * @param checkObject - check in which you want to add item
     * @param item        - product which you want to add
     * @param context
     * @return BoughtItem - which can be further edited
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the products which belongs to check is recognized from JSON
     */
    public static CheckItem addItem(CheckInformationStorage checkObject, CheckItem item, Context context)
            throws CheckEditingException {

        initialization(context);
        if (checkObtainingMethodForCheck(checkObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_CHECK_FROM_FNS);
        }

        //получение старого кол-ва покупок и увеличение на 1
        Cursor cursor = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_CHECK_LIST,
                null, "_id=" + checkObject.getId(), null, null, null, null);
        cursor.moveToFirst();
        int newQuantityPurchases = cursor.getInt(cursor.getColumnIndex
                (StorageCheckDataBase.COLUMN_NAME_QUANTITY_PURCHASES)) + 1;
        cursor.close();

        //Редактирование кол-во покупок
        sqLiteDatabase.execSQL("UPDATE " + StorageCheckDataBase.TABLE_NAME_CHECK_LIST +
                " SET " + StorageCheckDataBase.COLUMN_NAME_QUANTITY_PURCHASES + "='" + newQuantityPurchases
                + "' where _id=" + checkObject.getId());

        //Удаление записи добавление в бд
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_NAME, item.getName());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_NAME_FOR_USER, item.getNameForUser());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_QUANTITY, item.getQuantity());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_PRISE, item.getPrice());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_GENERAL_CATEGORIES, item.getGeneralCategory());
        contentValues.put(StorageCheckDataBase.COLUMN_NAME_SUBJECT_CATEGORIES, item.getSubjectCategory());
        contentValues.put("checkList_id", checkObject.getId());

        //второй аргумент используется для вставки пустой строки - это не нужно сейчас
        sqLiteDatabase.insert(StorageCheckDataBase.TABLE_NAME_SHOP_LIST, null, contentValues);
        contentValues.clear();

        Cursor cursorShop = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_SHOP_LIST,
                null, "checkList_id=" + checkObject.getId(), null, null, null, null);
        cursorShop.moveToLast();

        CheckItem tempItem = new CheckItem.Builder()
                .setId(cursorShop.getInt(cursorShop.getColumnIndex("_id")))
                .setName(cursorShop.getString(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_NAME)))
                .setNameForUser(cursorShop.getString(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_NAME_FOR_USER)))
                .setPrice(cursorShop.getInt(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_PRISE)))
                .setQuantity(cursorShop.getInt(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_QUANTITY)))
                .setGeneralCategory(cursorShop.getString(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_GENERAL_CATEGORIES)))
                .setSubjectCategory(cursorShop.getString(cursorShop.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_SUBJECT_CATEGORIES)))
                .build();
        cursorShop.close();
        return tempItem;


    }


    /**
     * This method allows you to delete the check
     * This method can be used by all checks
     *
     * @param checkObject - check which you want delete
     * @param context
     * @throws CheckEditingException - this exception is thrown if passed to a function
     *                               the check which is no in dataBase
     */
    public static void deleteCheck(CheckInformationStorage checkObject, Context context) throws CheckEditingException {

        initialization(context);

        if (!checkBdAvailability(checkObject.getId())) {
            throw new CheckEditingException(CheckEditingException.ERROR_MESSAGE_NO_CHECK_IN_DB);
        }

        sqLiteDatabase.execSQL("DELETE FROM " + StorageCheckDataBase.TABLE_NAME_CHECK_LIST
                + " WHERE _id=" + checkObject.getId());

        sqLiteDatabase.execSQL("DELETE FROM " + StorageCheckDataBase.TABLE_NAME_SHOP_LIST
                + " WHERE checkList_id=" + checkObject.getId());
    }

    public static String [] getAllCheckNames(Context context){
        initialization(context);
        Cursor cursor = sqLiteDatabase.query(StorageCheckDataBase.TABLE_NAME_CHECK_LIST, null,
                null, null, null, null, null);
        cursor.moveToFirst();
        CheckInformationStorage[] checkList = new CheckInformationStorage[cursor.getCount()];


        String [] checkNames = new String[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++) {
            checkNames[i] = cursor.getString(cursor.getColumnIndex(StorageCheckDataBase.COLUMN_NAME_CHECK_NAME));
            cursor.moveToNext();
        }
        cursor.close();
        return checkNames;
    }

}
