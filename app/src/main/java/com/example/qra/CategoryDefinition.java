package com.example.qra;


/**
 * responsible for determining the category
 *
 * @author: Marina Alekseeva
 */
public class CategoryDefinition {

    /**
     * define general category
     *
     * @param name - name of product
     * @return general category
     */
    public static String defineGeneralCategory(String name) {
        String[] filesCategories = FilesCategoriesInformations.getFilesGeneralCategories();
        String[] categories = ItemCategories.getGeneralCategories();

        return define(name, ItemCategories.NUMBERS_GENERAL_CATEGORIES, categories, filesCategories);
    }

    /**
     * define subject eat category
     *
     * @param name - name of product
     * @return subject category
     */
    public static String defineSubjectEatCategory(String name) {
        String[] filesCategories = FilesCategoriesInformations
                .FILES_CONTENT_CATEGORY_EAT_INFORMATION.getEatFilesCategories();
        String[] categories = ItemCategories.CONTENT_CATEGORY_EAT.getEatCategories();

        return define(name, ItemCategories.CONTENT_CATEGORY_EAT.NUMBERS_EAT_CATEGORY,
                categories, filesCategories);

    }

    /**
     * define category
     * @param name             - name of product
     * @param numberCategories  - number of categories
     * @param categories  - category list
     * @param filesCategories - list of files
     * @return name of category
     */
    private static String define(String name, int numberCategories,
                                 String[] categories, String[] filesCategories) {

        String[] words = name.split("\\s");
        for (String subStr : words) {
            for (int i = 0; i < numberCategories; i++) {
                if (filesCategories[i].contains(subStr)) {
                    return categories[i];
                }
            }
        }

//        for (int i = 0; i< numberCategories; i++) {
//            String[] wordsFromFilesCategory = filesCategories[i].split("\\s");
//            for (String subStrFromFilesCategory : wordsFromFilesCategory) {
//                if(name.contains(subStrFromFilesCategory)){
//                    return categories[i];
//                }
//            }
//        }
        return ItemCategories.NOT_DISTRIBUTED;
    }

}
