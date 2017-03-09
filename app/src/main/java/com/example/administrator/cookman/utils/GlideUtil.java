package com.example.administrator.cookman.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.administrator.cookman.CookManApplication;
import com.example.administrator.cookman.R;

import java.io.File;

/**
 * Created by PeOS on 2016/9/1 0001.
 */
public class GlideUtil {

    ImageView imageView;
    private DiskCacheStrategy diskCache = DiskCacheStrategy.ALL;//磁盘缓存
    private boolean isSkipMemoryCache = false;//禁止内存缓存

    public GlideUtil attach(ImageView imageView) {
        this.imageView = imageView;
        return this;
    }

    public GlideUtil injectImage(String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .diskCacheStrategy(diskCache)
                .skipMemoryCache(isSkipMemoryCache)
                .placeholder(R.mipmap.ic_icon_loading)
                .crossFade()
                .into(imageView);
        return this;
    }

    public GlideUtil injectImageWithNull(String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .diskCacheStrategy(diskCache)
                .skipMemoryCache(isSkipMemoryCache)
                .placeholder(null)
                .crossFade()
                .into(imageView);
        return this;
    }

    public GlideUtil injectImageWithoutCache(String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(isSkipMemoryCache)
                .placeholder(R.mipmap.ic_icon_loading)
                .crossFade()
                .into(imageView);
        return this;
    }

    public GlideUtil injectTarget(String url, Target target, Context context, @Nullable RequestListener
            requestListener) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .diskCacheStrategy(diskCache)
                .listener(requestListener)
                .into(target);
        return this;
    }

    public GlideUtil clearImage() {
        Glide.clear(imageView);
        imageView.setImageResource(R.mipmap.ic_icon_loading);
        return this;
    }

    public GlideUtil clearImage(int res) {
        Glide.clear(imageView);
        imageView.setImageResource(res);
        return this;
    }

    public void downloadImage(String url, Target target) {
        Glide.with(CookManApplication.getContext())
                .load(url)
                .asBitmap()
                .diskCacheStrategy(diskCache)
                .into(target);
    }

//    public static String getGlideCrashSize(){
//        try {
//            return FileUtil.getFormatSize(
//                    FileUtil.getFolderSize(
//                            new File(Glide.getPhotoCacheDir(YtSmartApplication.getContext()).getPath())
//                    )
//            );
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
}
