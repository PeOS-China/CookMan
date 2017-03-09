package com.example.administrator.cookman.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.administrator.cookman.R;
import com.example.administrator.cookman.model.manager.CookCategoryManager;
import com.example.administrator.cookman.presenter.Presenter;
import com.example.administrator.cookman.ui.adapter.CookCategoryFirAdapter;
import com.example.administrator.cookman.ui.adapter.CookCategorySndAdapter;

import butterknife.Bind;

public class CookCategoryActivity extends BaseSwipeBackActivity implements
        CookCategoryFirAdapter.OnCookCategoryFirListener
    , CookCategorySndAdapter.OnCookCategorySndListener
{

    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    @Bind(R.id.recyclerview_list_category)
    public RecyclerView recyclerListCategory;
    @Bind(R.id.recyclerview_list_content)
    public RecyclerView recyclerListContent;

    private CookCategoryFirAdapter cookCategoryFirAdapter;
    private CookCategorySndAdapter cookCategorySndAdapter;

    @Override
    protected Presenter getPresenter(){
        return null;
    }

    @Override
    protected int getLayoutId(){
        return R.layout.activity_cook_category;
    }

    @Override
    protected void init(Bundle savedInstanceState){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        cookCategoryFirAdapter = new CookCategoryFirAdapter(this);
        cookCategoryFirAdapter.setDataList(CookCategoryFirAdapter.createDatas(CookCategoryManager.getInstance().getCategoryFirDatas()));

        recyclerListCategory.setLayoutManager(new LinearLayoutManager(recyclerListCategory.getContext()));
        recyclerListCategory.setAdapter(cookCategoryFirAdapter);

        cookCategorySndAdapter = new CookCategorySndAdapter(this);
        cookCategorySndAdapter.setDataList(
                CookCategorySndAdapter.createDatas(
                        CookCategoryManager.getInstance().getCategorySndDatas(CookCategoryManager.getInstance().getCategoryFirDatas().get(0).getCtgId())
                )
        );
        recyclerListContent.setLayoutManager(new LinearLayoutManager(recyclerListCategory.getContext()));
        recyclerListContent.setAdapter(cookCategorySndAdapter);
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
    public void onCookCategoryFirClick(String ctgId){
        cookCategorySndAdapter.setDataList(
                CookCategorySndAdapter.createDatas(
                        CookCategoryManager.getInstance().getCategorySndDatas(ctgId)
                )
        );
    }

    @Override
    public void onCookCategorySndClick(String ctgId, String name){
        CookListActivity.startActivity(this, ctgId, name);
        //finish();
    }

    public static void startActivity(Activity activity){
        Intent intent = new Intent(activity, CookCategoryActivity.class);
        activity.startActivity(intent);
    }
}
