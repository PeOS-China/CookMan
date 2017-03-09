package com.example.administrator.cookman.model.entity.tb_cook;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/2/22.
 */

public class TB_CookSearchHistory extends DataSupport{

    public final static int CookSearchHistory_Type_Clean = 1;
    public final static int CookSearchHistory_Type_Content = 2;

    private int type;
    private String name;

    public TB_CookSearchHistory(){
        this.type = CookSearchHistory_Type_Content;
    }

    public TB_CookSearchHistory(int type, String name){
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
