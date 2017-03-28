package com.example.administrator.cookman.ui.component.twinklingrefreshlayout.dataView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.cookman.R;
import com.example.administrator.cookman.ui.component.ProgressWheel;

/**
 * Created by Administrator on 2017/3/17.
 */

public class DataKnifeView extends RelativeLayout {

    public static final int Mode_Loading = 0;
    public static final int Mode_NetworkErr = 1;
    public static final int Mode_Exception = 2;
    public static final int Mode_DataEmpty = 3;
    public static final int Mode_Visi_Gone = 4;

    private Context context;

    private RelativeLayout viewData;
    private ProgressWheel progressWheel;
    private ImageView imgvIcon;
    private TextView textInfo;

    private int mode = Mode_Loading;
    private int imgIdNetworkErr;
    private int imgIdException;
    private int imgIdDataEmpty;

    public DataKnifeView(Context context) {
        this(context, null);
    }

    public DataKnifeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DataKnifeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context){
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.common_view_pe_data_kinfe, this);

        viewData = (RelativeLayout)view.findViewById(R.id.view_data);
        progressWheel = (ProgressWheel)view.findViewById(R.id.progress_wheel);
        imgvIcon = (ImageView)view.findViewById(R.id.imgv_icon);
        textInfo = (TextView)view.findViewById(R.id.text_info);

        imgIdNetworkErr = R.mipmap.icon_data_view_net_err;
        imgIdException = R.mipmap.icon_data_view_exception;
        imgIdDataEmpty = R.mipmap.icon_data_view_null;

        viewData.setClickable(true);

    }

    public int getMode(){
        return mode;
    }

    public void setModeLoading(){
        mode = Mode_Loading;

        imgvIcon.setVisibility(GONE);
        textInfo.setVisibility(GONE);

        progressWheel.setVisibility(VISIBLE);
    }

    public void setModeLoading(String msg){
        mode = Mode_Loading;

        imgvIcon.setVisibility(GONE);

        textInfo.setVisibility(VISIBLE);
        progressWheel.setVisibility(VISIBLE);

        textInfo.setText(msg);
    }

    public void setModeNetworkErr(){
        mode = Mode_NetworkErr;

        textInfo.setVisibility(GONE);
        progressWheel.setVisibility(GONE);

        imgvIcon.setVisibility(VISIBLE);
        imgvIcon.setImageResource(imgIdNetworkErr);
    }

    public void setModeNetworkErr(String msg){
        mode = Mode_NetworkErr;

        progressWheel.setVisibility(GONE);

        textInfo.setVisibility(VISIBLE);
        imgvIcon.setVisibility(VISIBLE);
        imgvIcon.setImageResource(imgIdNetworkErr);

        textInfo.setText(msg);
    }

    public void setModeException(){
        mode = Mode_Exception;

        textInfo.setVisibility(GONE);
        progressWheel.setVisibility(GONE);

        imgvIcon.setVisibility(VISIBLE);
        imgvIcon.setImageResource(imgIdException);

    }

    public void setModeException(String msg){
        mode = Mode_Exception;

        progressWheel.setVisibility(GONE);

        textInfo.setVisibility(VISIBLE);
        imgvIcon.setVisibility(VISIBLE);
        imgvIcon.setImageResource(imgIdException);

        textInfo.setText(msg);

    }

    public void setModeDataEmpty(){
        mode = Mode_DataEmpty;

        textInfo.setVisibility(GONE);
        progressWheel.setVisibility(GONE);

        imgvIcon.setVisibility(VISIBLE);
        imgvIcon.setImageResource(imgIdDataEmpty);

    }

    public void setModeDataEmpty(String msg){
        mode = Mode_DataEmpty;

        progressWheel.setVisibility(GONE);

        textInfo.setVisibility(VISIBLE);
        imgvIcon.setVisibility(VISIBLE);
        imgvIcon.setImageResource(imgIdDataEmpty);

        textInfo.setText(msg);
    }

    public void setDataKnifeViewListener(OnClickListener dataKnifeViewListener){
        viewData.setOnClickListener(dataKnifeViewListener);
    }
}
