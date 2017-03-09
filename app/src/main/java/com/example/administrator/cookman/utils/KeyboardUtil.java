package com.example.administrator.cookman.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by PeOS on 2016/8/8 0008.
 */
public class KeyboardUtil {
    public static void showKeyboard(Activity activity, boolean isShow){
        if(isShow){
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        else{
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    public static void showKeyboard(Activity activity, View view, boolean isShow){
        if(isShow)
            ((InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInputFromInputMethod(view.getWindowToken(), 0);
        else
            ((InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
