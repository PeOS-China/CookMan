package com.example.administrator.cookman.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.cookman.R;
import com.example.administrator.cookman.constants.Constants;
import com.example.administrator.cookman.model.entity.CookEntity.CookDetail;
import com.example.administrator.cookman.model.manager.CookCollectionManager;
import com.example.administrator.cookman.presenter.Presenter;
import com.example.administrator.cookman.ui.adapter.CookDetailAdapter;
import com.example.administrator.cookman.ui.component.SwitchIconView;
import com.example.administrator.cookman.utils.GlideUtil;
import com.example.administrator.cookman.utils.Logger.Logger;
import com.example.administrator.cookman.utils.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.OnClick;

public class CookDetailActivity extends BaseSwipeBackActivity {

    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.toolbar_layout)
    public CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.recyclerview_list)
    public RecyclerView recyclerList;

    @Bind(R.id.imgv_bg)
    public ImageView imgvBg;

    private CookDetail data;
    private boolean isShowCollection;
    private GlideUtil glideUtil;
    private CookDetailAdapter cookDetailAdapter;


    @Override
    protected Presenter getPresenter(){
        return null;
    }

    @Override
    protected int getLayoutId(){
        return R.layout.activity_cook_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState){

        StatusBarUtil.setImmersiveStatusBar(this);
        StatusBarUtil.setImmersiveStatusBarToolbar(toolbar, this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        data = intent.getParcelableExtra(Intnet_Data_Cook);
        isShowCollection = intent.getBooleanExtra(Intnet_Data_Collection, false);

        if(null == data)
            return ;

        glideUtil = new GlideUtil();
        if(data.getRecipe().getImg() != null && (!TextUtils.isEmpty(data.getRecipe().getImg())))
            glideUtil.attach(imgvBg).injectImageWithNull(data.getRecipe().getImg());

        getSupportActionBar().setTitle(data.getName());

        cookDetailAdapter = new CookDetailAdapter(this, data, isShowCollection);
        recyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerList.setAdapter(cookDetailAdapter);
    }

    @Override
    public void onBackPressed() {
        boolean success = getSupportFragmentManager().popBackStackImmediate();
        if (!success)
            super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private final static String Intnet_Data_Cook = "cook";
    private final static String Intnet_Data_Collection = "collection";
    public static void startActivity(Activity activity, View view, CookDetail data, boolean isShowCollection){
        Intent intent = new Intent(activity, CookDetailActivity.class);
        intent.putExtra(Intnet_Data_Cook, data);
        intent.putExtra(Intnet_Data_Collection, isShowCollection);

        ActivityOptionsCompat options =
                ActivityOptionsCompat .makeSceneTransitionAnimation(activity
                        , Pair.create(view, activity.getString(R.string.transition_cook_detail_imgv_bg))
                        , Pair.create(view, activity.getString(R.string.transition_cook_detail_content))
                        );

        activity.startActivityForResult(intent, 10029, options.toBundle());
    }
}
