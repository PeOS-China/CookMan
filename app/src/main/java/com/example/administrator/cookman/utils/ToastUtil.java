package com.example.administrator.cookman.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by PeOS on 2016/8/30 0030.
 */
public class ToastUtil {
    public static void showToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int msgID){
        Toast.makeText(context, msgID, Toast.LENGTH_SHORT).show();
    }
}
