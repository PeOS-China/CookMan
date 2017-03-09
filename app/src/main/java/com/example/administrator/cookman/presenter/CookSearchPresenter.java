package com.example.administrator.cookman.presenter;

import android.content.Context;

import com.example.administrator.cookman.IView.ICookSearchView;
import com.example.administrator.cookman.constants.Constants;
import com.example.administrator.cookman.model.entity.CookEntity.subscriberEntity.SearchCookMenuSubscriberResultInfo;
import com.example.administrator.cookman.model.interfaces.ICookRespository;
import com.example.administrator.cookman.model.manager.CookSearchHistoryManager;
import com.example.administrator.cookman.model.respository.CookRespository;
import com.example.administrator.cookman.utils.ErrorExceptionUtil;
import com.example.administrator.cookman.utils.Logger.Logger;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/2/22.
 */

public class CookSearchPresenter extends Presenter{

    private ICookSearchView iCookSearchView;
    private ICookRespository iCookRespository;

    public CookSearchPresenter(Context context, ICookSearchView iCookSearchView){
        super(context);

        this.iCookSearchView = iCookSearchView;
        this.iCookRespository = CookRespository.getInstance();
    }

    @Override
    public void destroy(){
        if(searchCookMenuByNameSubscriber != null){
            searchCookMenuByNameSubscriber.unsubscribe();
        }
    }

    public void search(String name){
        rxJavaExecuter.execute(
                iCookRespository.searchCookMenuByName(name, 1, Constants.Per_Page_Size)
                , searchCookMenuByNameSubscriber = new SearchCookMenuByNameSubscriber()
        );
    }

    public void saveHistory(){

        rxJavaExecuter.execute(
                Observable.create(
                        new Observable.OnSubscribe<Void>() {
                            @Override
                            public void call(Subscriber<? super Void> subscriber) {
                                CookSearchHistoryManager.getInstance().save();
                            }
                        }
                )
                , new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }
                }
        );
    }

    private SearchCookMenuByNameSubscriber searchCookMenuByNameSubscriber;
    private class SearchCookMenuByNameSubscriber extends Subscriber<SearchCookMenuSubscriberResultInfo> {
        @Override
        public void onCompleted(){

        }

        @Override
        public void onError(Throwable e){
            if(searchCookMenuByNameSubscriber != null){
                searchCookMenuByNameSubscriber.unsubscribe();
            }


            if(iCookSearchView != null)
                iCookSearchView.onCookSearchFaile(ErrorExceptionUtil.getErrorMsg(e));

        }

        @Override
        public void onNext(SearchCookMenuSubscriberResultInfo data){

            if(data == null || data.getResult() == null){
                if(iCookSearchView != null)
                    iCookSearchView.onCookSearchFaile("找不到相关菜谱");

                this.onCompleted();
                return ;
            }

            int totalPages = data.getResult().getTotal();

            if(iCookSearchView != null) {
                if(data.getResult().getList().size() < 1){
                    iCookSearchView.onCookSearchEmpty();
                }
                else {
                    iCookSearchView.onCookSearchSuccess(data.getResult().getList(), totalPages);
                }
            }

            this.onCompleted();
        }
    }
}
