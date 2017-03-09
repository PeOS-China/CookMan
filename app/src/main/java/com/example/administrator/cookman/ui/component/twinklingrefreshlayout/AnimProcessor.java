package com.example.administrator.cookman.ui.component.twinklingrefreshlayout;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.animation.DecelerateInterpolator;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by lcodecore on 2016/11/26.
 */

public class AnimProcessor {

    private TwinklingRefreshLayout.CoProcessor cp;
    private static final float animFraction = 1f;
    //动画的变化率
    private DecelerateInterpolator decelerateInterpolator;

    public AnimProcessor(TwinklingRefreshLayout.CoProcessor coProcessor) {
        this.cp = coProcessor;

        decelerateInterpolator = new DecelerateInterpolator(8);
    }

    public void init() {
    }


    public void scrollHeadByMove(float moveY) {
        float offsetY = decelerateInterpolator.getInterpolation(moveY / cp.getMaxHeadHeight() / 2) * moveY / 2;
        if (cp.getHeader().getVisibility() != VISIBLE) cp.getHeader().setVisibility(VISIBLE);

        if (cp.isPureScrollModeOn()) cp.getHeader().setVisibility(GONE);
        cp.getHeader().getLayoutParams().height = (int) Math.abs(offsetY);
        cp.getHeader().requestLayout();

        if (!cp.isOpenFloatRefresh()) {
            cp.getContent().setTranslationY(offsetY);
            translateExHead((int) offsetY);
        }
        cp.onPullingDown(offsetY);
    }

    public void scrollBottomByMove(float moveY) {
        float offsetY = decelerateInterpolator.getInterpolation(moveY / cp.getBottomHeight() / 2) * moveY / 2;

        if (cp.getFooter().getVisibility() != VISIBLE) cp.getFooter().setVisibility(VISIBLE);

        if (cp.isPureScrollModeOn()) cp.getFooter().setVisibility(GONE);
        cp.getFooter().getLayoutParams().height = (int) Math.abs(offsetY);
        cp.getFooter().requestLayout();

        cp.getContent().setTranslationY(-offsetY);

        cp.onPullingUp(-offsetY);
    }

    public void dealPullDownRelease() {
        if (!cp.isPureScrollModeOn() && getVisibleHeadHeight() >= cp.getHeadHeight() - cp.getTouchSlop()) {
            animHeadToRefresh();
        } else {
            animHeadBack();
        }
    }

    public void dealPullUpRelease() {
        if (!cp.isPureScrollModeOn() && getVisibleFootHeight() >= cp.getBottomHeight() - cp.getTouchSlop()) {
            animBottomToLoad();
        } else {
            animBottomBack();
        }
    }

    private int getVisibleHeadHeight() {
        return cp.getHeader().getLayoutParams().height;
    }

    private int getVisibleFootHeight() {
        return cp.getFooter().getLayoutParams().height;
    }

    private boolean isAnimHeadToRefresh = false;

    /**
     * 1.满足进入刷新的条件或者主动刷新时，把Head位移到刷新位置（当前位置 ~ HeadHeight）
     */
    public void animHeadToRefresh() {
        isAnimHeadToRefresh = true;
        animLayoutByTime(getVisibleHeadHeight(), cp.getHeadHeight(), animHeadUpListener, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimHeadToRefresh = false;

                cp.setRefreshing(true);
                cp.onRefresh();
            }
        });
    }

    private boolean isAnimHeadBack = false;

    /**
     * 2.动画结束或不满足进入刷新状态的条件，收起头部（当前位置 ~ 0）
     */
    public void animHeadBack() {
        isAnimHeadBack = true;
        animLayoutByTime(getVisibleHeadHeight(), 0, animHeadUpListener, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimHeadBack = false;
            }
        });
    }

    private boolean isAnimBottomToLoad = false;

    /**
     * 3.满足进入加载更多的条件或者主动加载更多时，把Footer移到加载更多位置（当前位置 ~ BottomHeight）
     */
    public void animBottomToLoad() {
        isAnimBottomToLoad = true;
        animLayoutByTime(getVisibleFootHeight(), cp.getBottomHeight(), animBottomUpListener, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimBottomToLoad = false;

                cp.setLoadingMore(true);
                cp.onLoadMore();
            }
        });
    }

    private boolean isAnimBottomBack = false;

    /**
     * 4.加载更多完成或者不满足进入加载更多模式的条件时，收起尾部（当前位置 ~ 0）
     */
    public void animBottomBack() {
        isAnimBottomBack = true;
        animLayoutByTime(getVisibleFootHeight(), 0, animBottomUpListener, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimBottomBack = false;
            }
        });
    }

    private boolean isAnimHeadHide = false;

    /**
     * 5.当刷新处于可见状态，向上滑动屏幕时，隐藏刷新控件
     *
     * @param vy 手指向上滑动速度
     */
    public void animHeadHideByVy(int vy) {
        isAnimHeadHide = true;
        cp.onRefreshCanceled();
        vy = Math.abs(vy);
        if (vy < 5000) vy = 8000;
        animLayoutByTime(getVisibleHeadHeight(), 0, 5 * Math.abs(getVisibleHeadHeight() * 1000 / vy), animHeadUpListener, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimHeadHide = false;
                cp.resetHeaderView();
            }
        });
    }

    private boolean isAnimBottomHide = false;

    /**
     * 6.当加载更多处于可见状态时，向下滑动屏幕，隐藏加载更多控件
     *
     * @param vy 手指向下滑动的速度
     */
    public void animBottomHideByVy(int vy) {
        isAnimBottomHide = true;
        cp.onLoadmoreCanceled();
        vy = Math.abs(vy);
        if (vy < 5000) vy = 8000;
        animLayoutByTime(getVisibleFootHeight(), 0, 5 * getVisibleFootHeight() * 1000 / vy, animBottomUpListener, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimBottomHide = false;
                cp.resetBottomView();
            }
        });
    }

    private boolean isAnimOsTop = false;

    /**
     * 7.执行顶部越界  To executive cross-border springback at the top.
     * 越界高度height ∝ vy/computeTimes，此处采用的模型是 height=A*(vy + B)/computeTimes
     *
     * @param vy           满足越界条件的手指滑动速度  the finger sliding speed on the screen.
     * @param computeTimes 从满足条件到滚动到顶部总共计算的次数 Calculation times from sliding to top.
     */
    public void animOverScrollTop(float vy, int computeTimes) {
        if (cp.isOsTopLocked()) return;
        cp.lockOsTop();
        isAnimOsTop = true;
        cp.setStatePTD();
        int oh = (int) Math.abs(vy / computeTimes / 2);
        final int overHeight = oh > cp.getOsHeight() ? cp.getOsHeight() : oh;
        final int time = overHeight <= 50 ? 115 : (int) (0.3 * overHeight + 100);
        animLayoutByTime(0, overHeight, time, overScrollTopUpListener, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animLayoutByTime(overHeight, 0, 2 * time, overScrollTopUpListener, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isAnimOsTop = false;
                        cp.releaseOsTopLock();
                    }
                });
            }
        });
    }

    private boolean isAnimOsBottom = false;

    /**
     * 8.执行底部越界
     *
     * @param vy           满足越界条件的手指滑动速度
     * @param computeTimes 从满足条件到滚动到顶部总共计算的次数
     */
    public void animOverScrollBottom(float vy, int computeTimes) {
        if (cp.isOsBottomLocked()) return;
        cp.setStatePBU();
        int oh = (int) Math.abs(vy / computeTimes / 2);
        final int overHeight = oh > cp.getOsHeight() ? cp.getOsHeight() : oh;
        final int time = overHeight <= 50 ? 115 : (int) (0.3 * overHeight + 100);
        if (cp.autoLoadMore()) {
            cp.startLoadMore();
        } else {
            cp.lockOsBottom();
            isAnimOsBottom = true;
            animLayoutByTime(0, overHeight, time, overScrollBottomUpListener, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    animLayoutByTime(overHeight, 0, 2 * time, overScrollBottomUpListener, new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isAnimOsBottom = false;
                            cp.releaseOsBottomLock();
                        }
                    });
                }
            });
        }
    }

    private AnimatorUpdateListener animHeadUpListener = new AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int height = (int) animation.getAnimatedValue();
            cp.getHeader().getLayoutParams().height = height;
            cp.getHeader().requestLayout();
            if (!cp.isOpenFloatRefresh()) {
                cp.getContent().setTranslationY(height);
                translateExHead(height);
            }
            cp.onPullDownReleasing(height);
        }
    };

    private AnimatorUpdateListener animBottomUpListener = new AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int height = (int) animation.getAnimatedValue();
            cp.getFooter().getLayoutParams().height = height;
            cp.getFooter().requestLayout();
            cp.getContent().setTranslationY(-height);
            cp.onPullUpReleasing(height);
        }
    };

    private AnimatorUpdateListener overScrollTopUpListener = new AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int height = (int) animation.getAnimatedValue();
            if (cp.isOverScrollTopShow()) {
                cp.getHeader().getLayoutParams().height = height;
                cp.getHeader().requestLayout();
            } else cp.getHeader().setVisibility(GONE);

            cp.getContent().setTranslationY(height);
            translateExHead(height);

            cp.onPullDownReleasing(height);
        }
    };

    private AnimatorUpdateListener overScrollBottomUpListener = new AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int height = (int) animation.getAnimatedValue();
            if (cp.isOverScrollBottomShow()) {
                cp.getFooter().getLayoutParams().height = height;
                cp.getFooter().requestLayout();
            } else cp.getFooter().setVisibility(GONE);
            cp.getContent().setTranslationY(-height);
            cp.onPullUpReleasing(height);
        }
    };

    public void onScrolled(int distanceY) {
        //TODO 支持正常模式的Header  to support the normal-mode ex-header.
    }

    private void translateExHead(int offsetY) {
        if (!cp.isExHeadLocked()) cp.getExHead().setTranslationY(offsetY);
    }

    public void animLayoutByTime(int start, int end, long time, AnimatorUpdateListener listener, AnimatorListener animatorListener) {
        ValueAnimator va = ValueAnimator.ofInt(start, end);
        va.setInterpolator(new DecelerateInterpolator());
        va.addUpdateListener(listener);
        va.addListener(animatorListener);
        va.setDuration(time);
        va.start();
    }

    public void animLayoutByTime(int start, int end, long time, AnimatorUpdateListener listener) {
        ValueAnimator va = ValueAnimator.ofInt(start, end);
        va.setInterpolator(new DecelerateInterpolator());
        va.addUpdateListener(listener);
        va.setDuration(time);
        va.start();
    }

    public void animLayoutByTime(int start, int end, AnimatorUpdateListener listener, AnimatorListener animatorListener) {
        ValueAnimator va = ValueAnimator.ofInt(start, end);
        va.setInterpolator(new DecelerateInterpolator());
        va.addUpdateListener(listener);
        va.addListener(animatorListener);
        va.setDuration((int) (Math.abs(start - end) * animFraction));
        va.start();
    }
}
