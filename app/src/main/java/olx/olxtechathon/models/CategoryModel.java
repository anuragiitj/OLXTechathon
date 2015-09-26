package olx.olxtechathon.models;

import java.util.ArrayList;

public class CategoryModel {

    private ArrayList<CategoryItem> categoryList;
    public ArrayList<CategoryItem> getCategoryList() {
        return categoryList;
    }


    public CategoryModel(){

        categoryList = new ArrayList<>();

        ArrayList<String> subCategoryMovbile = new ArrayList<>();
        subCategoryMovbile.add("Windows");
        subCategoryMovbile.add("Nokia");
        subCategoryMovbile.add("Iphone");
        subCategoryMovbile.add("Blackberry");
        subCategoryMovbile.add("Android");
        CategoryItem categoryItemMobile = new CategoryItem("Mobile", subCategoryMovbile);
        categoryList.add(categoryItemMobile);

        ArrayList<String> subCategoryCars = new ArrayList<>();
        subCategoryCars.add("Fiat");
        subCategoryCars.add("Honda");
        subCategoryCars.add("Maruti");
        subCategoryCars.add("Hyundai");
        subCategoryCars.add("Volkswagon");
        subCategoryCars.add("Skoda");
        CategoryItem categoryItemCars = new CategoryItem("Cars", subCategoryCars);
        categoryList.add(categoryItemCars);

        ArrayList<String> subCategoryEle = new ArrayList<>();
        subCategoryEle.clear();
        subCategoryEle.add("Air Conditionar");
        subCategoryEle.add("TV");
        subCategoryEle.add("Refrigerator");
        subCategoryEle.add("Camera");
        subCategoryEle.add("Washing Machine");
        CategoryItem categoryItemElectronics = new CategoryItem("Electronics", subCategoryEle);
        categoryList.add(categoryItemElectronics);
    }



    public class CategoryItem{

        public CategoryItem(String name, ArrayList<String> subCategories){
            this.name = name;
            this.subCategories = subCategories;
        }
        private String name;

        public ArrayList<String> getSubCategories() {
            return subCategories;
        }

        public String getName() {
            return name;
        }

        private ArrayList<String> subCategories;
    }

}
