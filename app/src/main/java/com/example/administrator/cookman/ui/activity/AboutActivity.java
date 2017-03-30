package com.example.administrator.cookman.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.administrator.cookman.R;
import com.example.administrator.cookman.presenter.Presenter;
import com.example.administrator.cookman.utils.StatusBarUtil;
import com.example.administrator.cookman.utils.VersionUtil;

import butterknife.Bind;
import butterknife.OnClick;

public class AboutActivity extends BaseSwipeBackActivity {

    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.toolbar_layout)
    public CollapsingToolbarLayout toolbarLayout;

    @Bind(R.id.text_version)
    public TextView textVersion;


    @Override
    protected Presenter getPresenter(){
        return null;
    }

    @Override
    protected int getLayoutId(){
        return R.layout.activity_about;
    }

    @Override
    protected void init(Bundle savedInstanceState){

        StatusBarUtil.setImmersiveStatusBar(this);
        StatusBarUtil.setImmersiveStatusBarToolbar(toolbar, this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        toolbarLayout.setTitleEnabled(false);
        toolbar.setTitle(getString(R.string.app_name_about));

        textVersion.setText(String.format("当前版本: %s (Build %s)", VersionUtil.getVersion(this), VersionUtil.getVersionCode(this)));

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

    @OnClick(R.id.btn_look_core)
    public void onClickLookCode(){
        startHtml(getString(R.string.about_html_look_core));
    }

    @OnClick(R.id.btn_share_core)
    public void onClickShareCode(){
        startShare(
                getString(R.string.share_core_title)
                , getString(R.string.share_core_content)
        );
    }

    @OnClick(R.id.btn_look_bo)
    public void onClickLookBo(){
        startHtml(getString(R.string.about_html_look_bo));
    }

    @OnClick(R.id.btn_share_app)
    public void onClickShare(){
        startShare(
                getString(R.string.share_app_title)
                , getString(R.string.share_app_content)
        );
    }

    private void startHtml(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
    }

    private void startShare(String shareTitle, String shareContent){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
        startActivity(Intent.createChooser(sharingIntent, shareTitle));
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }
}
