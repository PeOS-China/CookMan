package com.example.administrator.cookman.presenter;

import android.content.Context;

import com.example.administrator.cookman.IView.ISplashView;
import com.example.administrator.cookman.model.entity.CookEntity.CategoryChildInfo1;
import com.example.administrator.cookman.model.entity.CookEntity.CategoryResultInfo;
import com.example.administrator.cookman.model.entity.CookEntity.subscriberEntity.CategorySubscriberResultInfo;
import com.example.administrator.cookman.model.interfaces.ICookRespository;
import com.example.administrator.cookman.model.manager.CookCategoryManager;
import com.example.administrator.cookman.model.manager.CustomCategoryManager;
import com.example.administrator.cookman.model.respository.CookRespository;
import com.example.administrator.cookman.utils.Logger.Logger;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/2/19.
 */

public class SplashPresenter extends Presenter{

    private ICookRespository iCookRespository;
    private ISplashView iSplashView;

    public SplashPresenter(Context context, ISplashView iSplashView){
        super(context);

        this.iCookRespository = CookRespository.getInstance();
        this.iSplashView = iSplashView;
    }

    public void destroy(){
        if(getCategoryQuerySubscriber != null){
            getCategoryQuerySubscriber.unsubscribe();
        }
    }

    public void initData(){

        rxJavaExecuter.execute(
                iCookRespository.getCategoryQuery()
                , getCategoryQuerySubscriber = new GetCategoryQuerySubscriber()
        );

    }

    private GetCategoryQuerySubscriber getCategoryQuerySubscriber;
    private class GetCategoryQuerySubscriber extends Subscriber<CategorySubscriberResultInfo> {
        @Override
        public void onCompleted(){

        }

        @Override
        public void onError(Throwable e){
            if(getCategoryQuerySubscriber != null){
                getCategoryQuerySubscriber.unsubscribe();
            }

            CustomCategoryManager.getInstance().initData(null);

            if(iSplashView != null)
                iSplashView.onSplashInitData();

        }

        @Override
        public void onNext(CategorySubscriberResultInfo data){

            ArrayList<CategoryChildInfo1> datas = CookCategoryManager.removeBangZi(data.getResult().getChilds());

            CookCategoryManager.getInstance().initDatas(datas);
            CustomCategoryManager.getInstance().initData(datas);

            if(iSplashView != null)
                iSplashView.onSplashInitData();

            this.onCompleted();
        }
    }
}
