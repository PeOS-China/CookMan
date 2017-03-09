package com.example.administrator.cookman.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.cookman.presenter.Presenter;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/17.
 */

public abstract class BaseFragment extends Fragment {

    protected Presenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);

        initView(inflater, container, savedInstanceState);

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();


        if(presenter == null && getPresenter() != null){
            presenter = getPresenter();
        }
    }

    @Override
    public void onPause(){
        super.onPause();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        ButterKnife.unbind(this);

        if(presenter != null){
            presenter.destroy();
        }

    }

    protected abstract Presenter getPresenter();
    protected abstract int getLayoutId();
    protected abstract void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}