package com.example.administrator.cookman.model.entity.CookEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/17.
 */

public class CategoryChildInfo1 {

    private CategoryInfo categoryInfo;
    private ArrayList<CategoryChildInfo2> childs;

    public CategoryChildInfo1(){

    }

    public CategoryInfo getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(CategoryInfo categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public ArrayList<CategoryChildInfo2> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<CategoryChildInfo2> childs) {
        this.childs = childs;
    }
}
