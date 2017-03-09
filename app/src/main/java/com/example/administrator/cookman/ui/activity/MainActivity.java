package com.example.administrator.cookman.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.administrator.cookman.R;
import com.example.administrator.cookman.presenter.Presenter;
import com.example.administrator.cookman.ui.fragment.MainPageFragment;
import com.example.administrator.cookman.utils.Logger.Logger;
import com.example.administrator.cookman.utils.ToastUtil;

public class MainActivity extends BaseActivity {

    private FragmentManager fragmentManager;
    private MainPageFragment mainPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**********************************************************/
    @Override
    protected int getLayoutId(){
        return R.layout.activity_main;
    }

    @Override
    protected Presenter getPresenter(){
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        mainPageFragment = new MainPageFragment();

        transaction
                .add(R.id.fragment_main_content, mainPageFragment)
                .show(mainPageFragment)
                .commit();
    }
    /**********************************************************/

    /**********************************************************/
    private boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {

        if(mainPageFragment.onBackPressed()) {
            return;
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        ToastUtil.showToast(this, R.string.toast_msg_oncemore_exit);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CookChannelActivity.Request_Code_Channel && resultCode == CookChannelActivity.Result_Code_Channel_NoChanged){
            if(mainPageFragment != null)
                mainPageFragment.updateChannel();
        }
    }

    /**********************************************************/

    public static void startActivity(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
