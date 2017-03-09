package com.example.administrator.cookman.utils;

/**
 * Created by Administrator on 2017/3/2.
 */

public class ErrorExceptionUtil {

    public static String getErrorMsg(Throwable e){

        String msg;
        String err = e.getMessage();

        if(err.equals("No address associated with hostname")){
            msg = "网络没有连接";
        }
        else{
            msg = "网络出问题了";
        }

        return msg;
    }
}
