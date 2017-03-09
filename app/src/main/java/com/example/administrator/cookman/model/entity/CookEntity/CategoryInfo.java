package com.example.administrator.cookman.model.entity.CookEntity;

/**
 * Created by Administrator on 2017/2/17.
 */

public class CategoryInfo {

    private String ctgId;
    private String parentId;
    private String name;

    public CategoryInfo(){

    }

    public String getCtgId() {
        return ctgId;
    }

    public void setCtgId(String ctgId) {
        this.ctgId = ctgId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
