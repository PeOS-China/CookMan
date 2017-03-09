package com.example.administrator.cookman.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.administrator.cookman.IView.ICookListView;
import com.example.administrator.cookman.R;
import com.example.administrator.cookman.model.entity.CookEntity.CookDetail;
import com.example.administrator.cookman.presenter.CookListPresenter;
import com.example.administrator.cookman.presenter.Presenter;
import com.example.administrator.cookman.ui.adapter.CookListAdapter;
import com.example.administrator.cookman.ui.component.twinklingrefreshlayout.Footer.BottomProgressView;
import com.example.administrator.cookman.ui.component.twinklingrefreshlayout.RefreshListenerAdapter;
import com.example.administrator.cookman.ui.component.twinklingrefreshlayout.TwinklingRefreshLayout;
import com.example.administrator.cookman.ui.component.twinklingrefreshlayout.header.bezierlayout.BezierLayout;
import com.example.administrator.cookman.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.Bind;

public class CookListActivity extends BaseSwipeBackActivity implements ICookListView {

    @Bind(R.id.text_title)
    public TextView textTitle;
    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    @Bind(R.id.refresh_layout)
    public TwinklingRefreshLayout twinklingRefreshLayout;
    @Bind(R.id.recyclerview_list)
    public RecyclerView recyclerList;

    private String cid;
    private String name;

    private CookListAdapter cookListAdapter;
    private CookListPresenter cookListPresenter;

    @Override
    protected Presenter getPresenter(){
        return cookListPresenter;
    }

    @Override
    protected int getLayoutId(){
        return R.layout.activity_cook_list;
    }

    @Override
    protected void init(Bundle savedInstanceState){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        cid = intent.getStringExtra(Intent_Type_Cid);
        name = intent.getStringExtra(Intent_Type_Name);

        textTitle.setText(name);

        recyclerList.setLayoutManager(new LinearLayoutManager(recyclerList.getContext()));
        cookListAdapter = new CookListAdapter(this);
        recyclerList.setAdapter(cookListAdapter);

        BezierLayout headerView = new BezierLayout(this);
        headerView.setRoundProgressColor(getResources().getColor(R.color.white));
        headerView.setWaveColor(getResources().getColor(R.color.colorPrimary));
        headerView.setRippleColor(getResources().getColor(R.color.white));
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setWaveHeight(140);

        BottomProgressView bottomProgressView = new BottomProgressView(twinklingRefreshLayout.getContext());
        bottomProgressView.setAnimatingColor(getResources().getColor(R.color.colorPrimary));
        twinklingRefreshLayout.setBottomView(bottomProgressView);
        twinklingRefreshLayout.setOverScrollBottomShow(true);

        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                cookListPresenter.updateRefreshCookMenuByID(cid);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                cookListPresenter.loadMoreCookMenuByID(cid);
            }
        });

        cookListPresenter = new CookListPresenter(this, this);
        cookListPresenter.updateRefreshCookMenuByID(cid);

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

    /********************************************************************************************/
    @Override
    public void onCookListUpdateRefreshSuccess(ArrayList<CookDetail> list){
        twinklingRefreshLayout.finishRefreshing();

        cookListAdapter.setDataList(list);
        cookListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCookListUpdateRefreshFaile(String msg){
        twinklingRefreshLayout.finishRefreshing();
        ToastUtil.showToast(this, msg);
    }

    @Override
    public void onCookListLoadMoreSuccess(ArrayList<CookDetail> list){
        twinklingRefreshLayout.finishLoadmore();
        cookListAdapter.addItems(list);
    }

    @Override
    public void onCookListLoadMoreFaile(String msg){
        twinklingRefreshLayout.finishLoadmore();
        ToastUtil.showToast(this, msg);
    }
    /********************************************************************************************/

    private static final String Intent_Type_Cid = "cid";
    private static final String Intent_Type_Name = "name";
    public static void startActivity(Activity activity, String cid, String name){
        Intent intent = new Intent(activity, CookListActivity.class);

        intent.putExtra(Intent_Type_Cid, cid);
        intent.putExtra(Intent_Type_Name, name);

        activity.startActivity(intent);
    }

}
