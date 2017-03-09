package com.example.administrator.cookman.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.cookman.R;
import com.example.administrator.cookman.model.entity.tb_cook.TB_CustomCategory;
import com.example.administrator.cookman.model.manager.CustomCategoryManager;
import com.example.administrator.cookman.presenter.Presenter;
import com.example.administrator.cookman.ui.component.tagComponent.ChannelItem;
import com.example.administrator.cookman.ui.component.tagComponent.ChannelManage;
import com.example.administrator.cookman.ui.component.tagComponent.DragAdapter;
import com.example.administrator.cookman.ui.component.tagComponent.DragGrid;
import com.example.administrator.cookman.ui.component.tagComponent.OtherAdapter;
import com.example.administrator.cookman.ui.component.tagComponent.OtherGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class CookChannelActivity extends BaseSwipeBackActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.userGridView)
    public DragGrid userGridView;
    @Bind(R.id.otherGridView)
    public OtherGridView otherGridView;

    private DragAdapter userAdapter;
    private OtherAdapter otherAdapter;

    private ArrayList<ChannelItem> otherChannelList = new ArrayList<ChannelItem>();
    private ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();

    private boolean isMove = false;
    private boolean isDataChanged = false;

    @Override
    protected Presenter getPresenter(){
        return null;
    }

    @Override
    protected int getLayoutId(){
        return R.layout.activity_cook_channel;
    }


    @Override
    protected void init(Bundle savedInstanceState){

        userChannelList = ((ArrayList<ChannelItem>)ChannelManage.getManage().getUserChannel());
        otherChannelList = ((ArrayList<ChannelItem>) ChannelManage.getManage().getOtherChannel());

        userAdapter = new DragAdapter(this, userChannelList);
        userGridView.setAdapter(userAdapter);

        otherAdapter = new OtherAdapter(this, otherChannelList);
        otherGridView.setAdapter(this.otherAdapter);

        otherGridView.setOnItemClickListener(this);
        userGridView.setOnItemClickListener(this);

    }

    @Override
    public void onPause(){
        super.onPause();

        CustomCategoryManager.getInstance().save(
                ChannelManage.getManage().getDefaultUserChannels()
                , ChannelManage.getManage().getDefaultOtherChannels()
        );
    }

    @Override
    public void onBackPressed(){
        onClickBack();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
        if(isMove){
            return;
        }

        switch (parent.getId()) {
            case R.id.userGridView:
                isDataChanged = true;
                if (position != 0 && position != 1) {
                    final ImageView moveImageView = getView(view);
                    if (moveImageView != null) {

                        TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                        final int[] startLocation = new int[2];
                        newTextView.getLocationInWindow(startLocation);
                        final ChannelItem channel = ((DragAdapter) parent.getAdapter()).getItem(position);

                        otherAdapter.setVisible(false);
                        otherAdapter.addItem(channel);

                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                try {
                                    int[] endLocation = new int[2];

                                    otherGridView.getChildAt(otherGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                    MoveAnim(moveImageView, startLocation , endLocation, channel,userGridView);
                                    userAdapter.setRemove(position);

                                } catch (Exception localException) {
                                }
                            }
                        }, 50L);
                    }
                }
                break;
            case R.id.otherGridView:
                isDataChanged = true;

                final ImageView moveImageView = getView(view);
                if (moveImageView != null){
                    TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final ChannelItem channel = ((OtherAdapter) parent.getAdapter()).getItem(position);

                    userAdapter.setVisible(false);
                    userAdapter.addItem(channel);

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                int[] endLocation = new int[2];

                                userGridView.getChildAt(userGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                MoveAnim(moveImageView, startLocation , endLocation, channel,otherGridView);

                                otherAdapter.setRemove(position);
                            } catch (Exception localException) {
                            }
                        }
                    }, 50L);
                }
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.imgv_back)
    public void onClickBack(){
        if(isDataChanged) {
            setResult(Result_Code_Channel_Changed, new Intent());
        }
        else{
            setResult(Result_Code_Channel_NoChanged, new Intent());
        }
        finish();
    }

    private void MoveAnim(View moveView, int[] startLocation,int[] endLocation, final ChannelItem moveChannel,
                          final GridView clickGridView) {
        int[] initLocation = new int[2];
        moveView.getLocationInWindow(initLocation);
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);

        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(300L);

        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                isMove = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moveViewGroup.removeView(mMoveView);

                if (clickGridView instanceof DragGrid) {
                    otherAdapter.setVisible(true);
                    otherAdapter.notifyDataSetChanged();
                    userAdapter.remove();
                }else{
                    userAdapter.setVisible(true);
                    userAdapter.notifyDataSetChanged();
                    otherAdapter.remove();
                }
                isMove = false;
            }
        });
    }

    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        viewGroup.addView(view);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    private ViewGroup getMoveViewGroup() {
        ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(this);
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        return moveLinearLayout;
    }

    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(cache);
        return iv;
    }

    public final static int Request_Code_Channel = 30456;
    public final static int Result_Code_Channel_Changed = 30457;
    public final static int Result_Code_Channel_NoChanged = 30457;
    public static void startActivity(Activity activity){
        activity.startActivityForResult(
                  new Intent(activity, CookChannelActivity.class)
                , Request_Code_Channel
                , ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
    }
}
