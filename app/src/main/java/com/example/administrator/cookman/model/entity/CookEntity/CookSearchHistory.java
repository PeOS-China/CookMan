package com.example.administrator.cookman.model.entity.CookEntity;

/**
 * Created by Administrator on 2017/3/30.
 */

public class CookSearchHistory {

    private String name;

    public CookSearchHistory(){

    }

    public CookSearchHistory(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
