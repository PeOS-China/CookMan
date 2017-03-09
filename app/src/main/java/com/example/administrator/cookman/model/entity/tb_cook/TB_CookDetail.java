package com.example.administrator.cookman.model.entity.tb_cook;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/2/25.
 */

public class TB_CookDetail extends DataSupport{

    private String ctgTitles;
    private String menuId;
    private String name;
    private String thumbnail;

    private String img;
    private String ingredients;
    private String method;
    private String sumary;
    private String title;

    public TB_CookDetail(){

    }

    public String getCtgTitles() {
        return ctgTitles;
    }

    public void setCtgTitles(String ctgTitles) {
        this.ctgTitles = ctgTitles;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSumary() {
        return sumary;
    }

    public void setSumary(String sumary) {
        this.sumary = sumary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
