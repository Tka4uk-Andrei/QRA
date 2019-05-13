package com.example.qra;

/**
 * class responsible for defining Category
 *
 * @author: Marina Alekseeva
 */
public class ItemCategories {
    public static final String NOT_DISTRIBUTED = "не определено";

    public static final String CATEGORY_EAT = "еда";

    public static final class CONTENT_CATEGORY_EAT {
        public static final String FRUITS = "фрукты";
        public static final String BERRIES = "ягоды";
        public static final String VEGETABLES = "овощи";
        public static final String GROCERY = "бакалея";
        public static final String CANDIES = "сладости";
        public static final String ALCOGOLIC_AND_ENERGY_DRINCS = "алкогольные и энергетические напитки";
        public static final String MILK = "молочная продукцмя";
        public static final String DRINKS_HOT = "горячие напитки";
        public static final String MEAT_SEAFOOD_EGG = "мясные изделия, морепродукты, яйца";
        public static final String SOFT_DRINK = "безалкогольные напитки";
        public static final String MUSHROOMS = "грибы";
        public static final String CONDIMENTS_DRIEDFRUIT_SEEDS_NUTS = "приправы, сухофрукты, орехи и семечки";
        public static final String BEEKEEPING = "пчеловодство";
        public static final String OIL_FATS = "масла и жиры";
        public static final String CEREALS = "крупы";
        public static final String BABY_FOOD = "детское питание";

        public static final String OTHER = "другое";
    }


    public static final String CATEGORY_HOUSEHOLD_PRODUCTS = "товары для дома";
    public static final String CATEGORY_ANIMALS = "товары для животных";
    public static final String CATEGORY_CLOTHES = "одежда";
    public static final String CATEGORY_GOODS_FOR_KIDS = "товаря для детей";
    public static final String CATEGORY_STATIONERY = "канцелярские товары";
    public static final String CATEGORY_ELECTRONICS = "элетроника";
    public static final String CATEGORY_OTHER = "другое";

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

}

