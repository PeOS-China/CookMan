package com.example.administrator.cookman.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import com.example.administrator.cookman.IView.ISplashView;
import com.example.administrator.cookman.presenter.SplashPresenter;

public class SplashActivity extends Activity implements ISplashView {

    private SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        splashPresenter = new SplashPresenter(this, this);
        splashPresenter.initData();
    }

    @Override
    public void onSplashInitData(){
        startMainActivity();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("deprecation")
    private void startMainActivity(){
        MainActivity.startActivity(this);
        overridePendingTransition(0, 0);
        finish();
    }

}
