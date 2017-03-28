package com.example.administrator.cookman.ui.component.twinklingrefreshlayout.PeRefreshLayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.cookman.R;
import com.example.administrator.cookman.ui.component.twinklingrefreshlayout.TwinklingRefreshLayout;
import com.example.administrator.cookman.ui.component.twinklingrefreshlayout.dataView.DataKnifeView;

/**
 * Created by Administrator on 2017/3/17.
 */

public class PeRefreshLayout extends RelativeLayout {

    private Context context;

    private DataKnifeView dataKnifeView;
    private TwinklingRefreshLayout twinklingRefreshLayout;
    private RecyclerView recyclerView;

    private PeRefreshLayoutListener peRefreshLayoutListener;

    public PeRefreshLayout(Context context) {
        this(context, null);
    }

    public PeRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PeRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context){
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.common_view_pe_refresh_layout, this);

        dataKnifeView = (DataKnifeView)view.findViewById(R.id.view_data);
        twinklingRefreshLayout = (TwinklingRefreshLayout)view.findViewById(R.id.refresh_layout);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_list);

        dataKnifeView.setDataKnifeViewListener(new OnClickListener() {
            @Override
            public void onClick(View v){
                setModeLoading();
                if(peRefreshLayoutListener != null){
                    peRefreshLayoutListener.onPeRefreshLayoutClick();
                }
            }
        });

    }

    public TwinklingRefreshLayout getTwinklingRefreshLayout() {
        return twinklingRefreshLayout;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setModeLoading(){
        twinklingRefreshLayout.setVisibility(GONE);
        dataKnifeView.setVisibility(VISIBLE);

        dataKnifeView.setModeLoading();
    }

    public void setModeLoading(String msg){
        twinklingRefreshLayout.setVisibility(GONE);
        dataKnifeView.setVisibility(VISIBLE);

        dataKnifeView.setModeLoading(msg);
    }

    public void setModeNetworkErr(){
        twinklingRefreshLayout.setVisibility(GONE);
        dataKnifeView.setVisibility(VISIBLE);

        dataKnifeView.setModeNetworkErr();
    }

    public void setModeNetworkErr(String msg){
        twinklingRefreshLayout.setVisibility(GONE);
        dataKnifeView.setVisibility(VISIBLE);

        dataKnifeView.setModeNetworkErr(msg);
    }

    public void setModeException(){
        twinklingRefreshLayout.setVisibility(GONE);
        dataKnifeView.setVisibility(VISIBLE);

        dataKnifeView.setModeException();
    }

    public void setModeException(String msg){
        twinklingRefreshLayout.setVisibility(GONE);
        dataKnifeView.setVisibility(VISIBLE);

        dataKnifeView.setModeException(msg);
    }

    public void setModeDataEmpty(){
        twinklingRefreshLayout.setVisibility(GONE);
        dataKnifeView.setVisibility(VISIBLE);

        dataKnifeView.setModeDataEmpty();
    }

    public void setModeDataEmpty(String msg){
        twinklingRefreshLayout.setVisibility(GONE);
        dataKnifeView.setVisibility(VISIBLE);

        dataKnifeView.setModeDataEmpty(msg);
    }

    public void setModeList(){
        twinklingRefreshLayout.setVisibility(VISIBLE);
        dataKnifeView.setVisibility(GONE);
    }

    public boolean isShowDataView(){
        return dataKnifeView.isShown();
    }

    public void setPeRefreshLayoutListener(PeRefreshLayoutListener peRefreshLayoutListener){
        this.peRefreshLayoutListener = peRefreshLayoutListener;
    }

}
