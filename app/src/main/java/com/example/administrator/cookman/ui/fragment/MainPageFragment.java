package com.example.administrator.cookman.ui.fragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.cookman.R;
import com.example.administrator.cookman.constants.Constants;
import com.example.administrator.cookman.model.entity.tb_cook.TB_CustomCategory;
import com.example.administrator.cookman.model.manager.CustomCategoryManager;
import com.example.administrator.cookman.presenter.Presenter;
import com.example.administrator.cookman.ui.activity.CookCategoryActivity;
import com.example.administrator.cookman.ui.activity.CookChannelActivity;
import com.example.administrator.cookman.ui.adapter.MainPageViewPageAdapter;
import com.example.administrator.cookman.ui.component.magicindicator.MagicIndicator;
import com.example.administrator.cookman.ui.component.magicindicator.buildins.commonnavigator.CommonNavigator;
import com.example.administrator.cookman.ui.component.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.example.administrator.cookman.ui.component.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.example.administrator.cookman.ui.component.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.example.administrator.cookman.ui.component.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import com.example.administrator.cookman.ui.component.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import com.example.administrator.cookman.utils.Logger.Logger;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/2/17.
 */

public class MainPageFragment extends BaseFragment implements
        ViewPager.OnPageChangeListener
{
    private List<TB_CustomCategory> customCategoryDatas;

    @Bind(R.id.magic_indicator)
    public MagicIndicator magicIndicator;
    @Bind(R.id.view_pager)
    public ViewPager viewPager;

    private CommonNavigator commonNavigator;
    private MainPageViewPageAdapter mainPageViewPageAdapter;

    /********************************************************************************************/
    @Override
    protected Presenter getPresenter(){
        return null;
    }

    @Override
    protected int getLayoutId(){
        return R.layout.fragment_main_page;
    }

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        initIndicatorView();
    }

    /********************************************************************************************/

    /********************************************************************************************/
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        magicIndicator.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        magicIndicator.onPageScrollStateChanged(state);
    }
    /********************************************************************************************/

    /********************************************************************************************/
    @OnClick(R.id.imgv_search)
    public void onClickImgvSearch(){
        MobclickAgent.onEvent(getActivity(), Constants.Umeng_Event_Id_Search);

        getFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, new SearchFragment(), "fragment_search")
                .addToBackStack("fragment:reveal")
                .commit();
    }

    @OnClick(R.id.imgv_add)
    public void onClickChannelAManager(){
        MobclickAgent.onEvent(getActivity(), Constants.Umeng_Event_Id_Channel);

        CookChannelActivity.startActivity(getActivity());
    }
    /********************************************************************************************/

    private void initIndicatorView(){
        customCategoryDatas = CustomCategoryManager.getInstance().getDatas();

        commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setScrollPivotX(0.35f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return customCategoryDatas == null ? 0 : customCategoryDatas.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(customCategoryDatas.get(index).getName());
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                WrapPagerIndicator indicator = new WrapPagerIndicator(context);
                indicator.setFillColor(Color.parseColor("#de9816"));//ebe4e3
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);

        mainPageViewPageAdapter = new MainPageViewPageAdapter(getFragmentManager(), customCategoryDatas);
        viewPager.addOnPageChangeListener(this);

        viewPager.setAdapter(mainPageViewPageAdapter);
    }

    public boolean onBackPressed() {
        SearchFragment fragment = (SearchFragment)getFragmentManager().findFragmentByTag("fragment_search");
        if(fragment != null) {
            return fragment.onBackPressed();
        }

        return false;

    }

    public void updateChannel(){

        initIndicatorView();

    }

}
