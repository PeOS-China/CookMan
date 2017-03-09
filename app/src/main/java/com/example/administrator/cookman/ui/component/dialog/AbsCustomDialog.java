package com.example.administrator.cookman.ui.component.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by PeOS on 2016/9/2 0002.
 */
public abstract class AbsCustomDialog extends Dialog {
    public Window mWindow;

    public AbsCustomDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setCancelable(getCancelable());
        setCanceledOnTouchOutside(getCanceledOnTouchOutside());

        setContentView(getLayoutResID());

        mWindow = getWindow();
        mWindow.setBackgroundDrawableResource(getBackgroundDrawableResourceId());

        if (getWindowAnimationsResId() != -1) {
            mWindow.setWindowAnimations(getWindowAnimationsResId());
        }

        if (getDimEnabled()) {
            mWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        else {
            mWindow.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }

        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.width = getWidth();
        lp.height = getHeight();
        lp.gravity = getGravity();
        onWindowAttributesChanged(lp);

        initView();
        initData();
        initListener();
    }

    /**
     * 是否背景模糊
     *
     * @return
     */
    public boolean getDimEnabled() {
        return true;
    }

    /**
     * 是否可取消
     *
     * @return
     */
    public boolean getCancelable() {
        return true;
    }

    /**
     * 触摸外部是否可取消
     *
     * @return
     */
    public boolean getCanceledOnTouchOutside() {
        return true;
    }

    /**
     * 背景资源ID
     *
     * @return
     */
    public int getBackgroundDrawableResourceId() {
        return android.R.color.transparent;
    }

    /**
     * 动画资源ID
     *
     * @return
     */
    public int getWindowAnimationsResId() {
        return -1;
    }

    /**
     * Dialog宽
     *
     * @return
     */
    public int getWidth() {
        return android.view.ViewGroup.LayoutParams.MATCH_PARENT;
    }

    /**
     * Dialog高
     *
     * @return
     */
    public int getHeight() {
        return android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    /**
     * 显示位置
     *
     * @return
     */
    public int getGravity() {
        return Gravity.CENTER;
    }

    /**
     * 自定义布局资源ID
     *
     * @return
     */
    public abstract int getLayoutResID();

    /**
     * 初始化View
     */
    public abstract void initView();

    /**
     * 显示数据
     */
    public abstract void initData();

    /**
     * 初始化监听器
     */
    public abstract void initListener();

    /**
     * margin
     * @return
     */
    public float getMargin() {
        return 0;
    }
}
