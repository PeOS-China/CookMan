package com.example.administrator.cookman.ui.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.cookman.model.entity.tb_cook.TB_CustomCategory;
import com.example.administrator.cookman.ui.fragment.CookListFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/2/17.
 */

public class MainPageViewPageAdapter extends FragmentPagerAdapter {

    private List<TB_CustomCategory> customCategoryDatas;

    public MainPageViewPageAdapter(FragmentManager fm, List<TB_CustomCategory> customCategoryDatas){
        super(fm);
        this.customCategoryDatas = customCategoryDatas;
    }

    @Override
    public CookListFragment getItem(int position) {
        CookListFragment fragment = null;

        fragment = new CookListFragment();
        fragment.setCustomCategoryData(customCategoryDatas.get(position));

        return fragment;
    }

    @Override
    public int getCount() {
        if(null == customCategoryDatas)
            return 0;

        return customCategoryDatas.size();
    }

    @Override
    public long getItemId(int position) {
        // 获取当前数据的hashCode
        int hashCode = customCategoryDatas.get(position).hashCode();
        return hashCode;
    }

}
