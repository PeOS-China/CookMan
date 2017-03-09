package com.example.administrator.cookman.ui.component.twinklingrefreshlayout;

import android.view.MotionEvent;

import com.example.administrator.cookman.ui.component.twinklingrefreshlayout.utils.ScrollingUtil;


/**
 * Created by lcodecore on 2016/11/26.
 */

public class RefreshProcessor {
    private float mTouchX, mTouchY;
    private TwinklingRefreshLayout.CoProcessor cp;

    public RefreshProcessor(TwinklingRefreshLayout.CoProcessor coProcessor) {
        this.cp = coProcessor;
    }

    public boolean interceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchX = ev.getX();
                mTouchY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = ev.getX() - mTouchX;
                float dy = ev.getY() - mTouchY;
                if (Math.abs(dx) <= Math.abs(dy)) {//滑动允许最大角度为45度
                    if (dy > 0 && !ScrollingUtil.canChildScrollUp(cp.getScrollableView()) && cp.allowPullDown()) {
                        cp.setStatePTD();
                        return true;
                    } else if (dy < 0 && !ScrollingUtil.canChildScrollDown(cp.getScrollableView()) && cp.allowPullUp()) {
                        cp.setStatePBU();
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    public boolean consumeTouchEvent(MotionEvent e) {
        if (cp.isRefreshVisible() || cp.isLoadingVisible()) return false;

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dy = e.getY() - mTouchY;
                if (cp.isStatePTD()) {
                    dy = Math.min(cp.getMaxHeadHeight() * 2, dy);
                    dy = Math.max(0, dy);
                    cp.getAnimProcessor().scrollHeadByMove(dy);
                } else if (cp.isStatePBU()) {
                    //加载更多的动作
                    dy = Math.min(cp.getBottomHeight() * 2, Math.abs(dy));
                    dy = Math.max(0, dy);
                    cp.getAnimProcessor().scrollBottomByMove(dy);
                }
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (cp.isStatePTD()) {
                    cp.getAnimProcessor().dealPullDownRelease();
                } else if (cp.isStatePBU()) {
                    cp.getAnimProcessor().dealPullUpRelease();
                }
                return true;
        }
        return false;
    }
}
