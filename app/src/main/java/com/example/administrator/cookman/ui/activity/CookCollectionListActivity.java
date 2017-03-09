package com.example.administrator.cookman.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.administrator.cookman.R;
import com.example.administrator.cookman.model.entity.CookEntity.CookDetail;
import com.example.administrator.cookman.model.entity.tb_cook.TB_CookDetail;
import com.example.administrator.cookman.model.manager.CookCollectionManager;
import com.example.administrator.cookman.presenter.Presenter;
import com.example.administrator.cookman.ui.adapter.CookCollectionListAdapter;
import com.example.administrator.cookman.ui.component.dialog.CollectionSelectDialog;
import com.example.administrator.cookman.ui.component.twinklingrefreshlayout.TwinklingRefreshLayout;
import com.example.administrator.cookman.ui.component.twinklingrefreshlayout.header.bezierlayout.BezierLayout;

import butterknife.Bind;

public class CookCollectionListActivity extends BaseSwipeBackActivity implements CookCollectionListAdapter.OnCookCollectionListListener {

    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    @Bind(R.id.refresh_layout)
    public TwinklingRefreshLayout twinklingRefreshLayout;
    @Bind(R.id.recyclerview_list)
    public RecyclerView recyclerList;

    private CookCollectionListAdapter cookCollectionListAdapter;

    @Override
    protected Presenter getPresenter(){
        return null;
    }

    @Override
    protected int getLayoutId(){
        return R.layout.activity_cook_collection_list;
    }

    @Override
    protected void init(Bundle savedInstanceState){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.collection);

        cookCollectionListAdapter = new CookCollectionListAdapter(this);
        cookCollectionListAdapter.setDataList(CookCollectionManager.getInstance().get());
        recyclerList.setLayoutManager(new LinearLayoutManager(recyclerList.getContext()));
        recyclerList.setAdapter(cookCollectionListAdapter);

        BezierLayout headerView = new BezierLayout(this);
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setPureScrollModeOn(true);

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

    @Override
    public void onCookCollectionListClick(View view, CookDetail cook){
        CookDetailActivity.startActivity(this, view, cook, false);
    }

    @Override
    public void onCookCollectionListMore(final TB_CookDetail cook){
        CollectionSelectDialog dlg = new CollectionSelectDialog(this);
        dlg.setOnCollectionSelectListener(new CollectionSelectDialog.OnCollectionSelectListener() {
            @Override
            public void onCollectionSelectDelete() {
                CookCollectionManager.getInstance().delete(CookCollectionManager.getInstance().tb2CookDetail(cook));
                cookCollectionListAdapter.deletItem(cook);
            }
        });
        dlg.show();
    }

    public static void startActivity(Activity activity){
        Intent intent = new Intent(activity, CookCollectionListActivity.class);
        activity.startActivity(intent);
    }

}
