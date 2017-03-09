package com.example.administrator.cookman.model.entity.CookEntity.subscriberEntity;

import com.example.administrator.cookman.model.entity.CookEntity.SearchCookMenuResultInfo;

/**
 * Created by Administrator on 2017/2/20.
 */

public class SearchCookMenuSubscriberResultInfo {

    private String msg;
    private String retCode;
    private SearchCookMenuResultInfo result;

    public SearchCookMenuSubscriberResultInfo(){

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

    public SearchCookMenuResultInfo getResult() {
        return result;
    }

    public void setResult(SearchCookMenuResultInfo result) {
        this.result = result;
    }
}
