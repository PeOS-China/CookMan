package com.example.administrator.cookman.ui.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.example.administrator.cookman.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView适配器基类
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements CommonHolder.OnNotifyChangeListener {
    protected List<T> dataList = new ArrayList<>();
    private boolean enableHead = false;
    CommonHolder headHolder;
    ViewGroup rootView;
    public final static int TYPE_HEAD = 0;
    public static final int TYPE_CONTENT = 1;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        rootView = parent;
        //设置ViewHolder
        int type = getItemViewType(position);
        if (type == TYPE_HEAD) {
            return headHolder;
        } else {
            return setViewHolder(parent);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        runEnterAnimation(holder.itemView, position);
        //数据绑定
        if (enableHead) {
            if (position == 0) {
                ((CommonHolder) holder).bindHeadData();
            } else {
                ((CommonHolder) holder).bindData(dataList.get(position - 1));
            }
        } else {
            ((CommonHolder) holder).bindData(dataList.get(position));
        }
        ((CommonHolder) holder).setOnNotifyChangeListener(this);
    }
    public ViewGroup getRootView() {
        return rootView;
    }
    @Override
    public int getItemCount() {
        if (enableHead) {
            return dataList.size() + 1;
        }
        return dataList.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (enableHead) {
            if (position == 0) {
                return TYPE_HEAD;
            } else {
                return TYPE_CONTENT;
            }
        } else {
            return TYPE_CONTENT;
        }
    }
    private int lastAnimatedPosition = -1;
    protected boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;
    private void runEnterAnimation(View view, int position) {
        if (animationsLocked) return;
        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(DensityUtil.dip2px(view.getContext(), 100));//(position+1)*50f
            view.setAlpha(0.f);
            view.animate()
                    .translationY(0).alpha(1.f)
                    .setStartDelay(delayEnterAnimation ? 20 * (position) : 0)
                    .setInterpolator(new DecelerateInterpolator(2.f))
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationsLocked = true;
                        }
                    }).start();
        }
    }
    @Override
    public void onNotify() {
        //提供给CommonHolder方便刷新视图
        notifyDataSetChanged();
    }
    public void setDataList(List<T> datas) {
        dataList.clear();
        if (null != datas) {
            dataList.addAll(datas);
        }
        notifyDataSetChanged();
    }
    public void clearDatas() {
        dataList.clear();
        notifyDataSetChanged();
    }
    /**
     * 添加数据到前面
     */
    public void addItemsAtFront(List<T> datas) {
        if (null == datas) return;
        dataList.addAll(0, datas);
        notifyDataSetChanged();
    }
    /**
     * 添加数据到尾部
     */
    public void addItems(List<T> datas) {
        if (null == datas) return;
        dataList.addAll(datas);
        notifyDataSetChanged();
    }
    /**
     * 添加单条数据
     */
    public void addItem(T data) {
        if (null == data) return;
        dataList.add(data);
        notifyDataSetChanged();
    }
    /**
     * 删除单条数据
     */
    public void deletItem(T data) {
        dataList.remove(data);
        Log.d("deletItem: ", dataList.remove(data) + "");
        notifyDataSetChanged();
    }
    /**
     * 设置是否显示head
     *
     * @param ifEnable 是否显示头部
     */
    public void setEnableHead(boolean ifEnable) {
        enableHead = ifEnable;
    }
    public void setHeadHolder(CommonHolder headHolder1) {
        enableHead = true;
        headHolder = headHolder1;
    }
    public void setHeadHolder(View itemView) {
        enableHead = true;
        headHolder = new HeadHolder(itemView);
        notifyItemInserted(0);
    }
    public CommonHolder getHeadHolder() {
        return headHolder;
    }
    /**
     * 子类重写实现自定义ViewHolder
     */
    public abstract CommonHolder<T> setViewHolder(ViewGroup parent);

    public class HeadHolder extends CommonHolder<Void> {
        public HeadHolder(View itemView) {
            super(itemView);
        }

        public HeadHolder(Context context, ViewGroup root, int layoutRes) {
            super(context,root,layoutRes);
        }

        @Override
        public void bindData(Void aVoid) {//不用实现
        }
    }
}