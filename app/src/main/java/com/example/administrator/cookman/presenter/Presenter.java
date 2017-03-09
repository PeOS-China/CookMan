package com.example.administrator.cookman.presenter;

import android.content.Context;

import com.example.administrator.cookman.model.executer.JobExecutor;
import com.example.administrator.cookman.model.executer.RxJavaExecuter;
import com.example.administrator.cookman.model.executer.UIThread;

/**
 * Created by Administrator on 2017/2/17.
 */

public abstract class Presenter {

    protected Context context;
    protected RxJavaExecuter rxJavaExecuter;

    public Presenter(Context context){
        this.context = context;
        this.rxJavaExecuter = new RxJavaExecuter(JobExecutor.instance(), UIThread.instance());
    }

    public abstract void destroy();
}
