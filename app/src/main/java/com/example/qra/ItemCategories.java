package com.example.qra;

/**
 * class responsible for storage Category
 *
 * @author: Marina Alekseeva
 */
public class ItemCategories {

    public static final int NUMBERS_GENERAL_CATEGORIES = 9;

    public static final String NOT_DISTRIBUTED = "не_определено";

    public static final String CATEGORY_EAT = "еда";

    public static final class CONTENT_CATEGORY_EAT {

        public final static int NUMBERS_EAT_CATEGORY = 18;

        public static final String FRUITS = "фрукты";
        public static final String BERRIES = "ягоды";
        public static final String VEGETABLES = "овощи";
        public static final String GROCERY = "бакалея";
        public static final String CANDIES = "сладости";
        public static final String ALCOGOLIC_AND_ENERGY_DRINCS = "алкогольные_и_энергетические_напитки";
        public static final String MILK = "молочная_продукцмя";
        public static final String DRINKS_HOT = "горячие_напитки";
        public static final String MEAT_SEAFOOD_EGG = "мясные_изделия_морепродукты_яйца";
        public static final String SOFT_DRINK = "безалкогольные_напитки";
        public static final String MUSHROOMS = "грибы";
        public static final String CONDIMENTS_DRIEDFRUIT_SEEDS_NUTS = "приправы_сухофрукты_орехи_и_семечки";
        public static final String BEEKEEPING = "пчеловодство";
        public static final String OIL_FATS = "масла_и_жиры";
        public static final String CEREALS = "крупы";
        public static final String BABY_FOOD = "детское питание";
        public static final String EAT_OTHER = "еда_другое";
        public static final String EAT_NOT_DISTRIBUTED = "не_определено";

        public static String[] getEatCategories() {
            String[] categories = new String[NUMBERS_EAT_CATEGORY];
            categories[0] = FRUITS;
            categories[1] = BERRIES;
            categories[2] = VEGETABLES;
            categories[3] = GROCERY;
            categories[4] = CANDIES;
            categories[5] = ALCOGOLIC_AND_ENERGY_DRINCS;
            categories[6] = MILK;
            categories[7] = DRINKS_HOT;
            categories[8] = MEAT_SEAFOOD_EGG;
            categories[9] = SOFT_DRINK;
            categories[10] = MUSHROOMS;
            categories[11] = CONDIMENTS_DRIEDFRUIT_SEEDS_NUTS;
            categories[12] = BEEKEEPING;
            categories[13] = OIL_FATS;
            categories[14] = CEREALS;
            categories[15] = BABY_FOOD;
            categories[16] = EAT_OTHER;
            categories[17] = EAT_NOT_DISTRIBUTED;
            return categories;
        }
    }

    public static final String CATEGORY_HOUSEHOLD_PRODUCTS = "товары_для_дома";
    public static final String CATEGORY_ANIMALS = "товары для животных";
    public static final String CATEGORY_CLOTHES = "одежда";
    public static final String CATEGORY_GOODS_FOR_KIDS = "товаря_для_детей";
    public static final String CATEGORY_STATIONERY = "канцелярские_товары";
    public static final String CATEGORY_ELECTRONICS = "элетроника";
    public static final String CATEGORY_OTHER = "другое";

    public static String[] getGeneralCategories() {
        String[] categories = new String[NUMBERS_GENERAL_CATEGORIES];
        categories[0] = CATEGORY_EAT;
        categories[1] = CATEGORY_HOUSEHOLD_PRODUCTS;
        categories[2] = CATEGORY_GOODS_FOR_KIDS;
        categories[3] = CATEGORY_STATIONERY;

        categories[4] = CATEGORY_ANIMALS;
        categories[5] = CATEGORY_CLOTHES;
        categories[6] = CATEGORY_ELECTRONICS;
        categories[7] = CATEGORY_OTHER;
        categories[8] = NOT_DISTRIBUTED;

        return categories;
    }


}


//    private static class StorageCategoriesDataBase extends SQLiteOpenHelper {
//
//        private static final int DATABASE_VERSION = 1;
//        private static final String DATABASE_Name = "CategoriesDataBase";
//
//        public static final String TABLE_NAME_GENERAL_CATEGORIES = "generalCategories";
//        public static final String TABLE_NAME_EAT_SUBJECT_CATEGORIES = "eatCategories";
//
//        public StorageCategoriesDataBase(Context context) {
//            super(context, DATABASE_Name, null, DATABASE_VERSION);
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase db) {
//            //primary key autoincrement, это поле будет автоматически заполнятся самй бд
//            db.execSQL("create table " + TABLE_NAME_GENERAL_CATEGORIES
//                    + "(_id integer primary key autoincrement, "
//                    + CATEGORY_EAT + " text, "
//                    + CATEGORY_HOUSEHOLD_PRODUCTS + " text, "
//                    + CATEGORY_ANIMALS + " text, "
//                    + COLUMN_NAME_TIME + " text, "
//                    + COLUMN_NAME_ADDRESS + " text, "
//                    + COLUMN_NAME_INN + " text, "
//                    + COLUMN_NAME_FISCAL_DRIVE_NUMBER + " text, "
//                    + COLUMN_NAME_FISCAL_SIGN + " text"
//                    + ")");
//
//            //внешний ключ привязан к первому полю чека (_id)
//            db.execSQL("create table " + TABLE_NAME_SHOP_LIST
//                    + "(_id integer primary key autoincrement, "
//                    + COLUMN_NAME_NAME + " text, "
//                    + COLUMN_NAME_NAME_FOR_USER + " text, "
//                    + COLUMN_NAME_GENERAL_CATEGORIES + " text, "
//                    + COLUMN_NAME_SUBJECT_CATEGORIES + " text, "
//                    + "checkList_id integer,"
//                    + " foreign key(checkList_id) references  checkList(_id)" + ")");
//
//        }
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }
//}

//    private Node root;
//
//    public void initCat(){
//        int quatnCat = 10;
//        String[] arrayCategory = new String[quatnCat];
//
//        arrayCategory[1] = FRUITS;
//        arrayCategory[2] = BERRIES;
//        arrayCategory[3] = VEGETABLES;
//        arrayCategory[4] = GROCERY;
//        arrayCategory[5] = CANDIES;
//        arrayCategory[6] = ALCOGOLIC_AND_ENERGY_DRINCS;
//        arrayCategory[7] = MILK;
//        arrayCategory[8] = DRINKS_HOT;
//        arrayCategory[9] = MEAT;
//
//        for(int i = 0; i < quatnCat; i ++){
//            Node tempNode = new Node();
//            tempNode.data = arrayCategory[i];
//            tempNode.children = new ArrayList<Node>();
//            tempNode.parent = root;
//            root.children.add(i, tempNode);
//        }
//    }
//
//    public void initEat(Node eat) {
//        int quatnEat = 8;
//        String[] arrayEat = new String[quatnEat];
//        arrayEat[0] = CATEGORY_EAT;
//        arrayEat[1] = CATEGORY_HOUSEHOLD_PRODUCTS;
//        arrayEat[2] = CATEGORY_ANIMALS;
//        arrayEat[3] = CATEGORY_CLOTHES;
//        arrayEat[4] = CATEGORY_GOODS_FOR_KIDS;
//        arrayEat[5] = CATEGORY_STATIONERY;
//        arrayEat[6] = CATEGORY_ELECTRONICS;
//        arrayEat[7] = CATEGORY_OTHER;
//
//        for(int i = 0; i < quatnEat; i ++){
//            Node tempNode = new Node();
//            tempNode.data = arrayEat[i];
//            tempNode.children = new ArrayList<Node>();
//            tempNode.parent = eat;
//            eat.children.add(i, tempNode);
//        }
//    }
//
//    public ItemCategories() {
//        root = new Node();
//        root.data = NOT_DISTRIBUTED;
//        root.children = new ArrayList<Node>();
//
//        initCat();
//        initEat(root.children.get(0));
//
//    }
//
//
//    public ItemCategories(String rootData) {
//        root = new Node();
//        root.data = rootData;
//        root.children = new ArrayList<Node>();
//    }
//
//    public static class Node {
//        private String data;
//        private Node parent;
//        private List<Node> children;
//    }



