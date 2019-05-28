package com.example.qra.model;

import android.content.Context;

import com.example.qra.model.check.CheckItem;

@Deprecated
public class AccordingFileCheckItemCategorizer extends DefaultCheckItemCategorizer {

    public AccordingFileCheckItemCategorizer(Context context) {
//        ArrayList<Pair<Pair<String, String>, String>> itemCategoryDictionary = new ArrayList<>();
//
//        String[] categories = ItemCategories.getGeneralCategories();
//        String[] nameFileCategories = getListNamesFilesGeneralCategories();
//        String[] nameFileCEat = getNamesFilesEatCategories();
//
//        for (int i = 0; i < nameFileCEat.length; ++i)
//
//        for (int i = 1; i < nameFileCategories.length; ++i){
//
//            try {
//                Scanner input = new Scanner(context.getAssets().open(nameFileCategories[i]));
//                Pair<Pair<String, String>, String> item;
//                item.first.first = categories[];
//                item.first.second = ;
//                item.second = ;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }

    }

    @Override
    public void defineCategory(CheckItem item) {
        String[] filesCategories = FilesCategoriesInformations.getFilesGeneralCategories();
        String[] words = item.getNameForUser().split("\\s"); // Разбиение строки на слова с помощью разграничителя (пробел)
        String[] categories = ItemCategories.getGeneralCategories();
        for (String subStr : words) {
            for (int i = 0; i < ItemCategories.NUMBERS_GENERAL_CATEGORIES; i++) {
                if (filesCategories[i].contains(subStr)) {
                    item.setGeneralCategory(categories[i]);
                    if (categories[i].equals(ItemCategories.CATEGORY_EAT)) {
                        //  item.setSubjectCategory();
                    }
                }
            }

        }
    }


//    static void defineCategory(CheckItem item, Context context){
//        String[] words = item.getNameForUser().split("\\s"); // Разбиение строки на слова с помощью разграничителя (пробел)
//        String[] categories = ItemCategories.getGeneralCategories();
//        String[] nameFileCategories = getListNamesFilesGeneralCategories();
//        for (String subStr : words) {
//            for (int i = 0; i < ItemCategories.NUMBERS_GENERAL_CATEGORIES; i++) {
//                try {
//                    InputStream mInput = context.getAssets().open(nameFileCategories[i]);
//                    byte[] mBuffer = new byte[mInput.available()];
//                    mInput.read(mBuffer);
//                    String text = new String(mBuffer, "windows-1251");
//                    if (text.contains(subStr)) {
//                        item.setGeneralCategory(categories[i]);
//                        if(categories[i].equals(ItemCategories.CATEGORY_EAT)){
//                            item.setSubjectCategory(defineEatCategories(item.getNameForUser(), context));
//                        }
//                        return;
//                    }
//                } catch (IOException ex) {
//                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//     //   DefaultCheckItemCategorizer.defineCategory(item);
//    }
//
//
//    private static String defineEatCategories(String name, Context context) {
//
//        String[] words = name.split("\\s"); // Разбиение строки на слова с помощью разграничителя (пробел)
//        String[] categories = ItemCategories.CONTENT_CATEGORY_EAT.getEatCategories();
//        String[] nameFileCategories = getNamesFilesEatCategories();
//        for (String subStr : words) {
//            for (int i = 0; i < ItemCategories.CONTENT_CATEGORY_EAT.NUMBERS_EAT_CATEGORY; i++) {
//
//                try {
//                    InputStream mInput = context.getAssets().open(nameFileCategories[i]);
//                    byte[] mBuffer = new byte[mInput.available()];
//                    mInput.read(mBuffer);
//                    String text = new String(mBuffer, "windows-1251");
//                    if (text.contains(subStr)) {
//                        return categories[i];
//                    }
//                } catch (IOException ex) {
//                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//        return ItemCategories.CONTENT_CATEGORY_EAT.EAT_NOT_DISTRIBUTED;
//    }
//
//
//
//
//    private static String[] getListNamesFilesGeneralCategories() {
//        String[] namesFiles = new String[ItemCategories.NUMBERS_GENERAL_CATEGORIES];
//        namesFiles[0] = "eat.txt";
//        namesFiles[1] = "household.txt";
//        namesFiles[2] = "baby.txt";
//        namesFiles[3] = "office.txt";
//        namesFiles[4] = "animals.txt";
//        namesFiles[5] = "clothes.txt";
//        namesFiles[6] = "electronic.txt";
//        namesFiles[7] = "other.txt";
//        namesFiles[8] = "notDistributed.txt";
//
//        return namesFiles;
//    }
//
//    private static String[] getNamesFilesEatCategories() {
//        String[] namesFiles = new String[ItemCategories.CONTENT_CATEGORY_EAT.NUMBERS_EAT_CATEGORY];
//        namesFiles[0] = "fruits.txt";
//        namesFiles[1] = "berries.txt";
//        namesFiles[2] = "vegetables.txt";
//        namesFiles[3] = "grocery.txt";
//        namesFiles[4] = "candies.txt";
//        namesFiles[5] = "alcohol.txt";
//        namesFiles[6] = "milk.txt";
//        namesFiles[7] = "hot.txt";
//        namesFiles[8] = "meat.txt";
//        namesFiles[9] = "nonalcoholic.txt";
//        namesFiles[10] = "mushrooms.txt";
//        namesFiles[11] = "seasoning.txt";
//        namesFiles[12] = "bee.txt";
//        namesFiles[13] = "fats.txt";
//        namesFiles[14] = "cereals.txt";
//        namesFiles[15] = "babyFood.txt";
//        namesFiles[16] = "eatOther.txt";
//        namesFiles[17] = "eatNotDistributed.txt";
//
//        return namesFiles;
//    }
    }
