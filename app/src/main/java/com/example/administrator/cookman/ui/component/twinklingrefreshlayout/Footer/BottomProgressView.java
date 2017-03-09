package com.example.administrator.cookman.ui.component.twinklingrefreshlayout.Footer;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;

import com.example.administrator.cookman.R;
import com.example.administrator.cookman.ui.component.twinklingrefreshlayout.IBottomView;


/**
 * Created by lcodecore on 2016/10/1.
 */

public class BottomProgressView extends ProgressView implements IBottomView {


    public BottomProgressView(Context context) {
        this(context,null);
    }

    public BottomProgressView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BottomProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        setLayoutParams(params);
        setIndicatorColor(getResources().getColor(R.color.Orange));
        setIndicatorId(BallPulse);
    }

    private int normalColor = 0xffeeeeee;
    private int animatingColor = 0xffe75946;

    public void setNormalColor(@ColorInt int color){
        normalColor = color;
    }

    public void setAnimatingColor(@ColorInt int color){
        animatingColor = color;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxHeadHeight, float headHeight) {
        setIndicatorColor(normalColor);
      stopAnim();
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        setIndicatorColor(animatingColor);
        startAnim();
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
       stopAnim();
    }

    @Override
    public void onFinish() {
        stopAnim();
    }

    @Override
    public void reset() {
        stopAnim();
    }
}
