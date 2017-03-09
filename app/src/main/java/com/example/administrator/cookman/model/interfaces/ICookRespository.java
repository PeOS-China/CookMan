package com.example.administrator.cookman.model.interfaces;

import com.example.administrator.cookman.model.entity.CookEntity.subscriberEntity.CategorySubscriberResultInfo;
import com.example.administrator.cookman.model.entity.CookEntity.subscriberEntity.SearchCookMenuSubscriberResultInfo;

import rx.Observable;

/**
 * Created by Administrator on 2017/2/20.
 */

public interface ICookRespository {

    public Observable<CategorySubscriberResultInfo> getCategoryQuery();
    public Observable<SearchCookMenuSubscriberResultInfo> searchCookMenuByID(final String cid, final int page, final int size);
    public Observable<SearchCookMenuSubscriberResultInfo> searchCookMenuByName(final String name, final int page, final int size);
}
