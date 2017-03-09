package com.example.administrator.cookman.utils;

import android.content.Context;
import android.util.TypedValue;

import com.example.administrator.cookman.CookManApplication;
import com.example.administrator.cookman.model.manager.CookCollectionManager;

/**
 * Created by Administrator on 2017/3/1.
 */

public class EnvUtil {
    private static int sStatusBarHeight;

    public static int getActionBarSize(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return DensityUtil.dp2px(44);
    }

    public static int getStatusBarHeight() {
        if (sStatusBarHeight == 0) {
            int resourceId =
                    CookManApplication.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                sStatusBarHeight = CookManApplication.getContext().getResources().getDimensionPixelSize(resourceId);
            }
        }
        return sStatusBarHeight;
    }

}
