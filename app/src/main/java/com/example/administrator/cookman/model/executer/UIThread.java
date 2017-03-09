package com.example.administrator.cookman.model.executer;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by PeOS on 2016/9/6 0006.
 */
public class UIThread implements PostExecutionThread{
    private UIThread() {

    }

    public static UIThread instance() {
        return Holder.INSTANCE;
    }

    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }

    private final static class Holder {
        private static final UIThread INSTANCE = new UIThread();
    }
}
