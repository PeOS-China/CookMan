package com.example.administrator.cookman.model.entity.CookEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/17.
 */

public class CategoryResultInfo {

    private CategoryInfo categoryInfo;
    private ArrayList<CategoryChildInfo1> childs;

    public CategoryResultInfo(){

    }

    public CategoryInfo getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(CategoryInfo categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public ArrayList<CategoryChildInfo1> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<CategoryChildInfo1> childs) {
        this.childs = childs;
    }
}
