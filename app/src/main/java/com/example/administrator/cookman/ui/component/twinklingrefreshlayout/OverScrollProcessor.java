package com.example.administrator.cookman.ui.component.twinklingrefreshlayout;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsListView;

import com.example.administrator.cookman.ui.component.twinklingrefreshlayout.utils.ScrollingUtil;


/**
 * Created by lcodecore on 2016/11/26.
 */

public class OverScrollProcessor {

    private TwinklingRefreshLayout.CoProcessor cp;

    //满足越界的手势的最低速度(默认3000)
    protected int OVER_SCROLL_MIN_VX = 3000;

    public OverScrollProcessor(TwinklingRefreshLayout.CoProcessor coProcessor) {
        this.cp = coProcessor;
        mTouchSlop = cp.getTouchSlop();
    }

    private VelocityTracker moveTracker;
    private int mPointerId;
    private float vy;

    public void init() {
        final View mChildView = cp.getContent();

        final GestureDetector gestureDetector = new GestureDetector(cp.getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (cp.isRefreshVisible() && distanceY >= mTouchSlop && !cp.isOpenFloatRefresh()) {
                    cp.setRefreshing(false);
                    cp.getAnimProcessor().animHeadHideByVy((int) vy);
                }
                if (cp.isLoadingVisible() && distanceY <= -mTouchSlop) {
                    cp.setLoadingMore(false);
                    cp.getAnimProcessor().animBottomHideByVy((int) vy);
                }

                return super.onScroll(e1, e2, distanceX, distanceY);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (!cp.enableOverScroll()) return false;

                mVelocityY = velocityY;
//            if (!(mChildView instanceof AbsListView || mChildView instanceof RecyclerView)) {
                //既不是AbsListView也不是RecyclerView,由于这些没有实现OnScrollListener接口,无法回调状态,只能采用延时策略
                if (Math.abs(mVelocityY) >= OVER_SCROLL_MIN_VX) {
                    mHandler.sendEmptyMessage(MSG_START_COMPUTE_SCROLL);
                } else {
                    mVelocityY = 0;
                    cur_delay_times = ALL_DELAY_TIMES;
                }
//            }
                return false;
            }
        });

        mChildView.setOnTouchListener(new View.OnTouchListener() {
            int mMaxVelocity = ViewConfiguration.get(cp.getContext()).getScaledMaximumFlingVelocity();

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //手势监听的两个任务：1.监听fling动作，获取速度  2.监听滚动状态变化
                obtainTracker(event);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mPointerId = event.getPointerId(0);
                        break;
                    case MotionEvent.ACTION_UP:
                        moveTracker.computeCurrentVelocity(1000, mMaxVelocity);
                        vy = moveTracker.getYVelocity(mPointerId);
                        releaseTracker();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        releaseTracker();
                        break;
                }
                return gestureDetector.onTouchEvent(event);
            }
        });

        if (!cp.enableOverScroll()) return;

        if (mChildView instanceof AbsListView) {
            ((AbsListView) mChildView).setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (cp.allowOverScroll() && firstVisibleItem == 0 || ((AbsListView) mChildView).getLastVisiblePosition() == totalItemCount - 1) {
                        if (mVelocityY >= OVER_SCROLL_MIN_VX && ScrollingUtil.isAbsListViewToTop((AbsListView) mChildView)) {
                            cp.getAnimProcessor().animOverScrollTop(mVelocityY, cur_delay_times);
                            mVelocityY = 0;
                            cur_delay_times = ALL_DELAY_TIMES;
                        }
                        if (mVelocityY <= -OVER_SCROLL_MIN_VX && ScrollingUtil.isAbsListViewToBottom((AbsListView) mChildView)) {
                            cp.getAnimProcessor().animOverScrollBottom(mVelocityY, cur_delay_times);
                            mVelocityY = 0;
                            cur_delay_times = ALL_DELAY_TIMES;
                        }
                    }
                }
            });
        } else if (mChildView instanceof RecyclerView) {
            ((RecyclerView) mChildView).addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (cp.allowOverScroll() && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (mVelocityY >= OVER_SCROLL_MIN_VX && ScrollingUtil.isRecyclerViewToTop((RecyclerView) mChildView)) {
                            cp.getAnimProcessor().animOverScrollTop(mVelocityY, cur_delay_times);
                            mVelocityY = 0;
                            cur_delay_times = ALL_DELAY_TIMES;
                        }
                        if (mVelocityY <= -OVER_SCROLL_MIN_VX && ScrollingUtil.isRecyclerViewToBottom((RecyclerView) mChildView)) {
                            cp.getAnimProcessor().animOverScrollBottom(mVelocityY, cur_delay_times);
                            mVelocityY = 0;
                            cur_delay_times = ALL_DELAY_TIMES;
                        }
                    }
                    super.onScrollStateChanged(recyclerView, newState);
                }
            });
        }
    }

    private void obtainTracker(MotionEvent event) {
        if (null == moveTracker) {
            moveTracker = VelocityTracker.obtain();
        }
        moveTracker.addMovement(event);
    }

    private void releaseTracker() {
        if (null != moveTracker) {
            moveTracker.clear();
            moveTracker.recycle();
            moveTracker = null;
        }
    }

    //主要为了监测Fling的动作,实现越界回弹
    private float mVelocityY;

    //针对部分没有OnScrollListener的View的延时策略
    private static final int MSG_START_COMPUTE_SCROLL = 0; //开始计算
    private static final int MSG_CONTINUE_COMPUTE_SCROLL = 1;//继续计算
    private static final int MSG_STOP_COMPUTE_SCROLL = 2; //停止计算

    private int cur_delay_times = 0; //当前计算次数
    private static final int ALL_DELAY_TIMES = 60;  //10ms计算一次,总共计算20次
    private int mTouchSlop;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START_COMPUTE_SCROLL:
                    cur_delay_times = -1; //这里没有break,写作-1方便计数
                case MSG_CONTINUE_COMPUTE_SCROLL:
                    cur_delay_times++;

                    View mChildView = cp.getContent();

                    if (!(mChildView instanceof AbsListView || mChildView instanceof RecyclerView)) {

                        if (cp.allowOverScroll() && mVelocityY >= OVER_SCROLL_MIN_VX && (mChildView != null && Math.abs(mChildView.getScrollY()) <= 2 * mTouchSlop)) {
                            cp.getAnimProcessor().animOverScrollTop(mVelocityY, cur_delay_times);
                            mVelocityY = 0;
                            cur_delay_times = ALL_DELAY_TIMES;
                        }

                        if (cp.allowOverScroll() && mVelocityY <= -OVER_SCROLL_MIN_VX && mChildView != null) {
                            if (mChildView instanceof WebView) {
                                WebView webview = (WebView) (mChildView);
                                if (Math.abs(webview.getContentHeight() * webview.getScale() - (webview.getHeight() + webview.getScrollY())) <= 2 * mTouchSlop) {
                                    cp.getAnimProcessor().animOverScrollBottom(mVelocityY, cur_delay_times);
                                    mVelocityY = 0;
                                    cur_delay_times = ALL_DELAY_TIMES;
                                }
                            } else if (mChildView instanceof ViewGroup) {
                                View subChildView = ((ViewGroup) mChildView).getChildAt(0);
                                if (subChildView != null && subChildView.getMeasuredHeight() <= mChildView.getScrollY() + mChildView.getHeight()) {
                                    //滚动到了底部
                                    cp.getAnimProcessor().animOverScrollBottom(mVelocityY, cur_delay_times);
                                    mVelocityY = 0;
                                    cur_delay_times = ALL_DELAY_TIMES;
                                }
                            } else if (mChildView.getScrollY() >= mChildView.getHeight()) {
                                cp.getAnimProcessor().animOverScrollBottom(mVelocityY, cur_delay_times);
                                mVelocityY = 0;
                                cur_delay_times = ALL_DELAY_TIMES;
                            }
                        }
                    }

                    if (cur_delay_times < ALL_DELAY_TIMES)
                        mHandler.sendEmptyMessageDelayed(MSG_CONTINUE_COMPUTE_SCROLL, 10);
                    break;
                case MSG_STOP_COMPUTE_SCROLL:
                    cur_delay_times = ALL_DELAY_TIMES;
                    break;
            }
        }
    };
}
