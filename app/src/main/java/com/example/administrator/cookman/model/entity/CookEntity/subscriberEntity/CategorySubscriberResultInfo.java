package com.example.administrator.cookman.model.entity.CookEntity.subscriberEntity;

import com.example.administrator.cookman.model.entity.CookEntity.CategoryResultInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/20.
 */

public class CategorySubscriberResultInfo {

    private String msg;
    private String retCode;
    private CategoryResultInfo result;

    public CategorySubscriberResultInfo(){

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public CategoryResultInfo getResult() {
        return result;
    }

    public void setResult(CategoryResultInfo result) {
        this.result = result;
    }
}
